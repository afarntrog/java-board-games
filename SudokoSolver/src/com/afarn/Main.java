package com.afarn;

public class Main {
    public static void main(String[] args) {

        // get the board. Either default or input from user with file
        int[][] PLAY_BOARD = Board.getBoard();

        Sudoku sudoku = new Sudoku(PLAY_BOARD);
        System.out.println("Sudoku grid to solve");
        sudoku.display();

        // try to solve the board with backtracking
        if (sudoku.solveBoard()) {
            System.out.println("Sudoku Grid solved with Backtracking");
            sudoku.display();
        } else {
            System.out.println("This Sudoku board cannot be solved");
        }
    }
}
