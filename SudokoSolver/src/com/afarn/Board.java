package com.afarn;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Board extends Component {


    // ask use which board to use and return appropriate one.
    public static int[][] getBoard() {
        System.out.print("Use default board (d) or input board with file (i) or (q) to quit  ");
        Scanner input = new Scanner(System.in);
        char result = input.nextLine().toLowerCase().charAt(0);
        if (result == 'q')
            System.exit(0);

        if (result == 'd')
            return getDefaultBoard();

        // if result is not Default or Quit then it must be Input - so getFile();
        String filePath = getFile();

        return getInputBoard( filePath );
    }

    // get the file from the user
    public static String getFile() {
        // new instance of fileChooser
        JFileChooser fileChooser = new JFileChooser();
        // set file chooser to home directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int choseFile = fileChooser.showOpenDialog(null);
//            while (choseFile != JFileChooser.APPROVE_OPTION || )
        File selectedFile = null; // initialize file object
        if (choseFile == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        else
            return "";
    }


    // get the board from user
    public static int[][] getInputBoard(String filepath) {

        // initialize board to fill with file from user
        int[][] sudokuBoard = new int[9][9];

        String fileRow;

        int row = 0;

        try {

            Scanner scanner = new Scanner(new File(filepath));

            while (scanner.hasNextLine()) {

                fileRow = scanner.nextLine();

                // place row into array
                String[] temp = fileRow.split(" ");

                for (int col = 0; col < 9; col++) {
                    // convert String to int and store in board
                    sudokuBoard[row][col] = Integer.parseInt(temp[col]);

                }
                row++; // increment i
            }

        } catch (Exception e) {
            System.out.println("That is an invalid file.");
            getBoard(); // call board again
//            System.out.println("That is an invalid file.\nExiting program.");
//            System.exit(0);
        }
        return sudokuBoard;

    }


    // initialize a default board
    public static int[][] getDefaultBoard() {
        //    2d sudoku board
        int[][] PLAY_BOARD = {
                                {9,0,0,1,0,0,0,0,5},
                                {0,0,5,0,9,0,2,0,1},
                                {8,0,0,0,4,0,0,0,0},
                                {0,0,0,0,8,0,0,0,0},
                                {0,0,0,7,0,0,0,0,0},
                                {0,0,0,0,2,6,0,0,9},
                                {2,0,0,3,0,0,0,0,6},
                                {0,0,0,2,0,0,9,0,0},
                                {0,0,1,9,0,4,5,7,0},
                            }; // end BOARD initializer

        return PLAY_BOARD;
    }
}