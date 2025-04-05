package university;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class LoadFile {

    // Retrieves a single line of user input from the console
    public String userscan() {
        Scanner scanf = new Scanner(System.in); // Input from GUI
        String userinput = scanf.nextLine();
        return userinput;
    }

    public void writeToFile(String fileName, String subject, String StudentID, String replacement) { //based on student ID, will write over student attributes
        // Read the contents of the file into a 2D array
        String[][] arr = readFromFile(fileName);
        File Baller = new File(fileName);
        Baller.delete();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Baller))) {

            // Initialize an empty string to store each line before writing to the file
            String line = "";

            // Iterate through the rows and columns of the 2D array (arr)
            for (int i = 0; i < arr.length - 1; i++) {//goes row by row until i < arr.length
                for (int j = 0; j < arr[0].length; j++) { //j is iterated throughout the elements on a row

                    if (StudentID.equals(arr[i][0])) {
                        { //is this the correct student ID
                            if (subject.equals(arr[0][j])) { //is this the correct subject to change? "Headers comparasin"
                                arr[i][j] = replacement; //changes the element at the correct student, and subject number, to the replacement
                            }
                        }
                    }

                    if (j == arr[0].length - 1) {
                        line = line + arr[i][j];//appends the final character such that the line does not contain indent
                    } else {
                        line = line + arr[i][j] + "\t"; //adds element to the line
                    }
                }
                writer.write(line);
                line = "";
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[][] readFromFile(String fileName) { //organizes all data into a 2D String List, such that the data can be added to a class file
        int open = 0;
        int count = 0;
        int row = 0;
        String[][] arr = null;

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

                int[][] arrdim = arraydimension(fileName);

                arr = new String[arrdim[0][0]][arrdim[0][1]]; //area dimension rowxcolumns

                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.contains("\0")) {
                        arr[row][0] = "\0";
                        return arr;
                    }

                    for (int i = 0; i < line.length(); ++i) {
                        if (count == arr[0].length - 1) { //if the element count is equal to the number elements
                            arr[row][count] = line.substring(open); //at current element, in row x, append the remaining substring at the end
                            break;
                        }

                        if (line.charAt(i) == '\t') { //if the char at character (i) is a tab indent
                            arr[row][count] = line.substring(open, i); //at current element in rowx, is opening range to the character
                            ++count;
                            open = i + 1;
                        }
                    }

                    count = 0;
                    ++row;
                    open = 0;
                }

                return arr;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return arr;
        }
    }

    public int[][] arraydimension(String fileName) {
        {
            int row = 1;

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                int elementcount = 1;

                while ((line = reader.readLine()) != null) {//screen through every single line in the file

                    if (line.contains("\0")) { //checking for the final line
                        break;
                    } else {
                        if (row == 1) {
                            elementcount = 1 + line.length() - line.replace("\t", "").length(); //finds number of elements in the first row
                        }
                    }
                    row += 1;
                }

                return new int[][]{{row, elementcount}, {}}; //2d array of (Dimensions)(11 string interval arrays, each with integers inside)
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new int[][]{{0, 0}, {}};
    }

    public String ID_FetchThing(String fileName, String USERID, String Thing) {

        String[][] arr = readFromFile(fileName);

        for (int i = 0; i < arr.length - 1; i++) {//goes row by row until i < arr.length

            for (int j = 0; j < arr[0].length; j++) { //j is iterated throughout the elements on a row

                if (USERID.equals(arr[i][0])) {
                    { //is this the correct ID
                        if (Thing.equals(arr[0][j])) { //is this the correct subject to change? "Headers comparasin"
                            return arr[i][j]; //changes the element at the correct student, and subject number, to the replacement
                        }
                    }
                }
            }
        }
        return "ID NOT FOUND";
    }

    public String fetchAnything(String fileName, String check, String change) {
        // Step 1: Read the 2D array from the file
        String[][] arr = readFromFile(fileName);

        // Step 2: Find the column index for the header (Change)
        int headerIndex = -1;
        int headerIndex1 = -1;
        for (int col = 0; col < arr[0].length; col++) {
            if (arr[0][col].equals(change)) {
                headerIndex = col;
                break;
            }
        }
        // If the header is not found, return an error message
        if (headerIndex == -1) {
            return "Header not found: " + change;
        }

        // Step 3: Find the row index for the item (Check)
        int rowIndex = -1;
        for (int row = 1; row < arr.length - 1; row++) { // Start at row 1 to skip the header row
            if (arr[row][headerIndex].equals(check)) { // Assuming the first column contains row identifiers
                rowIndex = row;
                break;
            }
        }

        // If the item is not found, return an error message
        if (rowIndex == -1) {
            return "Item not found: " + check;
        }

        // Step 4: Return the value at the intersection
        return arr[rowIndex][headerIndex];
    }

    public String fetchAnything1(String fileName, String check, String change1, String change) {
        // Step 1: Read the 2D array from the file
        String[][] arr = readFromFile(fileName);

        // Step 2: Find the column index for the header (Change)
        int headerIndex = -1;
        int headerIndex1 = -1;
        for (int col = 0; col < arr[0].length; col++) {
            if (arr[0][col].equals(change)) {
                headerIndex = col;
                break;
            }
        }

        for (int col = 0; col < arr[0].length; col++) {
            if (arr[0][col].equals(change1)) {
                headerIndex1 = col;
                break;
            }
        }

        // If the header is not found, return an error message
        if (headerIndex == -1) {
            return "Header not found: " + change;
        }

        // Step 3: Find the row index for the item (Check)
        int rowIndex = -1;
        for (int row = 1; row < arr.length - 1; row++) { // Start at row 1 to skip the header row
            if (arr[row][headerIndex1].equals(check)) { // Assuming the first column contains row identifiers
                rowIndex = row;
                break;
            }
        }

        // If the item is not found, return an error message
        if (rowIndex == -1) {
            return "Item not found: " + check;
        }

        // Step 4: Return the value at the intersection
        return arr[rowIndex][headerIndex];
    }

    public ArrayList<String> fetchAll(String fileName, String check, String header) {
        // Step 1: Read the 2D array from the file
        String[][] arr = readFromFile(fileName);
        ArrayList<String> resultList = new ArrayList<>();

        // Step 2: Find the column index for the header (to fetch values)
        int headerIndex = -1;
        for (int col = 0; col < arr[0].length; col++) {
            if (arr[0][col].equals(header)) {
                headerIndex = col;
                break;
            }
        }

        // If the header is not found, return an empty list


        // Step 3: Iterate through the rows and check if `check` is present
        for (int row = 1; row < arr.length - 1; row++) { // Start at row 1 to skip the header
            for (int col = 0; col < arr[row].length; col++) {
                if (arr[row][col].equals(check)) { // If `check` exists in the row
                    resultList.add(arr[row][headerIndex]); // Add the value under the `header` column
                    break; // Stop checking further columns in this row
                }
            }
        }

        // Step 4: Remove duplicates (if necessary)
        ArrayList<String> uniqueList = new ArrayList<>();
        for (String value : resultList) {
            if (!uniqueList.contains(value)) {
                uniqueList.add(value);
            }
        }

        // Step 5: Return the list of unique values
        return uniqueList;
    }


    public String[] fetchRow(String fileName, String USERID) {
        String[][] arr = readFromFile(fileName);

        if (arr == null) {
            return new String[]{"File not loaded properly"}; // Error handling
        }

        for (int i = 0; i < arr.length; i++) { // Iterate through rows
            if (USERID.equals(arr[i][0])) { // Check if USERID matches the first column
                return arr[i]; // Return the entire row
            }
        }

        return new String[]{"USERID NOT FOUND"}; // If no match is found
    }

    public void newRow(String fileName, String newRow) {
        File Baller = new File(fileName); // Create the file object

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Baller))) {
            // Step 1: Overwrite the file with the new row content
            writer.write(newRow);

        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions gracefully
        }
    }

}