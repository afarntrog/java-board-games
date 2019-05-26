/**
 * Functions of this class:
 * 1) creates a board and initializes it.
 * 2) allows you to retrieve an exact copy of the board.
 * 3) allows you to print out the board.
 * 4) Accepts a move and sets that move on the board.
 * 
 * HUMAN = X, COMPUTER = O
 */

public class Board {
    String player ;
    String HUMAN_PIECE = "X";
    int turn_total_flips;
    String COMPUTER_PIECE = "O";
    int move_row;
    int move_col;

    // Initialize othello board.
    private String[][] othello_board = new String[8][8];

    // Constructor sets the board.
    public Board() {
        set_initial_board();
    }

    // Copy constructor.
    public Board(String[][] from_board) {
        for (int row = 0; row < from_board.length; row++) {
            for (int col = 0; col < from_board.length; col++) {
                othello_board[row][col] = from_board[row][col];
            }
        }
    }

    // Set Board to start
    private void set_initial_board() {
        // Set orginal board to change null to " "
        String placeholder = " ";
        for (int row = 0; row < othello_board.length; row++) {
            for (int col = 0; col < othello_board[row].length; col++) {
            othello_board[row][col] = placeholder;
            }
        }
        // This sets the middle values to start the game.
        othello_board[3][3] = "X";
        othello_board[3][4] = "O";    
        othello_board[4][3] = "O";
        othello_board[4][4] = "X";
    } // End boardSetter()



    // This prints out the board
    public void board_printer() {
        // Set col and row labels
        String row_label = "12345678";
        String col_label = "12345678";

        // Print board
        linePrinter(col_label);
        for (int row = 0; row < othello_board.length; row++) {
            System.out.print(row_label.charAt(row) + " ");
            for (int col = 0; col < othello_board[row].length; col++) {
            System.out.print("|");
            System.out.print(othello_board[row][col]);
            }
            System.out.print("|");
            System.out.println();
        }
        // Extra space
        System.out.println();
    }// End method
    
    // This prints our a nice line to start and the col labels
    private void linePrinter(String col_label) {
        System.out.print("  ");

        for (int i = 0; i < col_label.length(); i++) {
            System.out.print(" ");
            System.out.print(col_label.charAt(i));
        }
        System.out.println("\n");
    }
    // :: End Board Printer::

    // Make a deep copy of the board this will be used in the copy constructor.
    public String[][] get_board() {
        String[][] copy_board = new String[8][8];
        for (int row = 0; row < othello_board.length; row++) {
            for (int col = 0; col < othello_board.length; col++) {
                copy_board[row][col] = othello_board[row][col];
            }
        }
        return copy_board;
    }
    // Make a deep copy of the incoming board and set it to the main global board.
    public void set_board(String[][] from_board) {
        for (int row = 0; row < from_board.length; row++) {
            for (int col = 0; col < from_board.length; col++) {
                othello_board[row][col] = from_board[row][col];
            }
        }
    }


    // This accepts a move and then sets it on the board.
    public void set_move(int move_row, int move_col, String player) {
        this.player = player;
        this.move_row = move_row;
        this.move_col = move_col;
        othello_board[move_row][move_col] = player.equals("HUMAN") ? "X" : "O";           
    }

}// End class

  // Undo move???
// Board winner??