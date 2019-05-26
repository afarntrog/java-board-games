public class Computer extends PlayerLogic {

    String BLANK_PIECE = " ";
    
    // This will call the super constructor.
    public Computer(String piece_color) {
        super(piece_color);
        System.out.println(PLAYER_PIECE);
    }
    // // Make a deep copy of the board.
    // public String[][] my_board() {
    //     String[][] copy_board = new String[8][8];
    //     for (int row = 0; row < othello_board.length; row++) {
    //         for (int col = 0; col < othello_board.length; col++) {
    //             copy_board[row][col] = othello_board[row][col];
    //         }
    //     }
    //     return copy_board;
    // }

    // SET BEST MOVE //
    /**
     * This method finds the best possible move and then sets the global move_row and move_col
     * to be the best move. So then it can call a method that'll execute the best move 
     * 
     */
    private int[] get_best_move() {
        int[] row_col = new int[2];
        int best_move_row = 0;
        int best_move_col = 0;

        String[][] store_othello_board =  copy_me_now( othello_board );
        // Temporary store the original board. We will then manipulate the global othello.board
        // so that we can call those methods on the global othello_board
        // After each iteration we will restore the global othello board from this temp_store_board
        // And then perform the test again to se if we can get the best move
        // String[][] temp_store_board = this.my_board();

        // 1 get all the valid moves
        // loop through the entire board if space is valid then process if not next.
        // Loop through the whole board. Check if move is valid
        // If it is then count how many pieces it flips. The move with the most piece flips is the move we want.
        int most_flips = 0;
        for ( int row = 0; row < othello_board.length; row++) {
            for (int col = 0; col < othello_board[row].length; col++) {
                // After each iteration reset the othello_board to the original board using a DEEP COPY of temp board.
                othello_board =  copy_me_now( store_othello_board );
                // set global ro/col variables to current row/col so we can use functions
                // QUESTION: I thought this may be ugly and instead I should pass the varaibles to the method. However, when i did that last semester my professor told me that that way is to CLUNKY, better to use global
                boolean move_is_valid = move_validator(othello_board, row, col);

                if ( move_is_valid ) {
                    turn_total_flips = 0;
                    set_move(row, col);
                    flip_all_pieces();
                    System.out.println(turn_total_flips);
                    // Get the total flips for current turn.                    
                    if (turn_total_flips > most_flips) {
                        most_flips = turn_total_flips;
                        best_move_row = row;
                        best_move_col = col;
                        System.out.println("BEST MOVE REACHED ");
                        // System.out.println(best_move[1]);

                    }
                }
            }
        }
        // Restore global othello board
        othello_board = store_othello_board;
        // Set best move values to return them.
        row_col[0] = best_move_row;
        row_col[1] = best_move_col;
        System.out.println(best_move_row);
        System.out.println(best_move_col);
        return row_col;
    }// end method

    // Set computers piece color on move
    private void set_best_move() {
        int[] row_col;
        row_col = get_best_move();
        // Set global move row and column
        move_row = row_col[0];
        move_col = row_col[1];
        othello_board[move_row][move_col] = PLAYER_PIECE;
    }

    // Play computers move and flip it's pieces.
    public void play_computer_move( String[][] game_board ) {
        // Set global othello board.
        othello_board = game_board;
        set_best_move();
        flip_all_pieces();
    }

    public String[][] copy_me_now(String[][] from_board) {
        String[][] copy_board = new String[8][8];
        for (int row = 0; row < from_board.length; row++) {
            for (int col = 0; col < from_board.length; col++) {
                copy_board[row][col] = from_board[row][col];
            }
        }
        return copy_board;
    }

    public boolean game_is_over(String[][] param_othello_board) {
        othello_board = param_othello_board;
        for (int row = 0; row < othello_board.length; row++) {
            for (int col = 0; col < othello_board[row].length; col++) {
                if ( move_validator(param_othello_board, row, col) ) {
                    return false;
                }
            }
        }    
        // If we looped through whole board and never returned true the there are no valid moves
        System.out.println("Computer cannot move. You go instead!");
        return true;
     }
}
