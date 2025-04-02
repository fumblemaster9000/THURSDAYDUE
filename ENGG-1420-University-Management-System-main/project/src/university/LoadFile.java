package university;

import java.io.*;
import java.util.Scanner;

public class LoadFile {

    public String userscan() {
        Scanner scanf = new Scanner(System.in); // Input from GUI
        String userinput = scanf.nextLine();
        return userinput;
    }

    public void writeToFile(String fileName, String subject, String StudentID, String replacement) { //based on student ID, will write over student attributes
        String[][] arr = readFromFile(fileName);
        File Baller = new File("C:\\Users\\dalyc\\Desktop\\Student.txt\\");
        Baller.delete();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Baller))) {

            String line = "";
            for (int i = 0; i < arr.length - 1; i++) {//goes row by row until i < arr.length

                //if(StudentID.equals(arr[i][0])){//Is the StudentID the correct person?

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

            //if(StudentID.equals(arr[i][0])){//Is the StudentID the correct person?

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
}
