package com.afarn;

public class Sudoku {

    private int[][] board;
    public static final int EMPTY = 0; // blank cell
    public static final int SIZE = 9;// size of the grid


    // CONSTRUCTOR: take in board that player wants to solve. make copy of it
    public Sudoku(int[][] board) {
        this.board = new int[SIZE][SIZE];

        // for each row copy 9 columns
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                this.board[row][col] = board[row][col];
            }
        }
    }
    /* RULES: Check to make sure the number is not in either:
        1.Row
        2.Column
        3. its own 3x3 grid
    */

    // check row
    private boolean isInRow(int curr_row, int target) {
        for (int col = 0; col < SIZE; col++) {
            if (board[curr_row][col] == target)
                return true;
        }
        return false;
    }


    // check in is in column
    private boolean isInCol(int curr_col, int target) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][curr_col] == target)
                return true;
        }
        return false;
    }


    // check its entire 3*3 grid
    private boolean inInOwnGrid(int curr_row, int curr_col, int target) {
        // we must get this cell's grid  based off the current position
        // use modulo. Example: curr_row=8 , start_row = curr_row -( curr_row % 3) this will be start_row = 8 -( 8 % 3) which equals to 6. so start at 6 and go 2 more, 3 in total
        int beg_row = curr_row - (curr_row % 3);
        int beg_col = curr_col - (curr_col % 3);

        for (int row = beg_row; row < beg_row + 3; row++) {
            for (int col = beg_col; col < beg_col + 3; col++) {
                if (board[row][col] == target) {
                    return true;
                }
            }
        }
        return false;
    }


    // parent method to check that target number can be placed here
    // this returns true if the target is NOT in: own_grid && col && row
    private boolean cellIsGood(int curr_row, int curr_col, int target) {
        return !inInOwnGrid(curr_row, curr_col, target) && !isInCol(curr_col, target) && !isInRow(curr_row, target);
    }




    // Solve Sudoku board method. We will use a recursive BackTracking algorithm.
    public boolean solveBoard() {

        // loop through the whole board and get the first empty cell
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                // find first available empty cell
                if (board[row][col] == EMPTY) {

                    // try a number in order to finc one that is good
                    for (int number = 1; number <= SIZE; number++) {
                        // make sure the number can legally go in this cell
                        if (cellIsGood(row, col, number)) {
                            board[row][col] = number;

                            if (solveBoard()) { // we start backtracking recursively
                                return true;
                            } else { // if not a solution, we empty the cell and we continue
                                board[row][col] = EMPTY;
                            }

                        } // if cellIsGood returns false for given number then try next number
                    }
                    // if we are here than the board cannot be solved because no number can go in this cell
                    return false; // we return false
                }
            }
        }

        return true; // sudoku solved
    }


    // Display the board.
    public void display() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j]);
            }

            System.out.println();
        }

        System.out.println();
    }


}



