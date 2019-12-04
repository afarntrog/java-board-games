public class Computer extends PlayerLogic {

    // String BLANK_PIECE = " ";

    // This will call the super constructor.
    public Computer(String piece_color) {
        super(piece_color);
    }

    // BEST MOVE //
    /**
     * This method finds the best possible move and then sets the global move_row and move_col
     * to be the best move at the current iteration. 
     * So then it can call a method that'll execute the best move.
     * 1) Test every possible move. Loop through the whole board. Check if move is valid.
     * 2) If move is valid then count how many pieces it flips. 
     * 3) The move with the most piece flips is the move we want. So return that value.
     */
    private int[] get_best_move() {
        int[] row_col = new int[2];
        int best_move_row = 0;
        int best_move_col = 0;

        // If corner is available then Grab it!
        if (corner_available()) {
            return corner_move();
        }

        // Temporary store the original board. We will then manipulate the global othello.board
        // so that we can call those methods on the global othello_board
        // After each iteration we will restore the global othello board from this temp_store_board
        // And then perform the test again to se if we can get the best move
        String[][] store_othello_board =  copy_me_now( othello_board );

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
                    // Get the total flips for current turn.                    
                    if (turn_total_flips > most_flips) {
                        most_flips = turn_total_flips;
                        best_move_row = row;
                        best_move_col = col;
                    }
                }// End outer if
            }
        }// End outer forloop

        // Restore global othello board
        othello_board = store_othello_board;

        // Set best move values to return them.
        row_col[0] = best_move_row;
        row_col[1] = best_move_col;
        return row_col;
    }// end method

    // Checks to see if a corner is available, if it is return corner move.
    public int[] corner_move() {
        int[] row_col = {-1, -1};
        if ( move_validator(othello_board, 0, 0) ) {
            row_col[0] = 0;
            row_col[1] = 0;
        }
        else if ( move_validator(othello_board, 0, 7) ) {
            row_col[0] = 0;
            row_col[1] = 7;
        }
        else if ( move_validator(othello_board, 7, 0) ){
            row_col[0] = 7;
            row_col[1] = 0; 
        }
        else if ( move_validator(othello_board, 7, 7)  ){
            row_col[0] = 7;
            row_col[1] = 7;
        }
        return row_col;
    }

    // If corner_move[0] is a -1 then there is no available corner.
    // This method tells us if there is an available corner.
    public boolean corner_available() {
        return corner_move()[0] != -1;
    }
    

    // Set computers piece color on move
    private void set_best_move() {
        int[] row_col;
        row_col = get_best_move();
        // Set global move row and column
        move_row = row_col[0];
        move_col = row_col[1];
        othello_board[move_row][move_col] = PLAYER_PIECE;
    }
    
        // Tell user where the computer will move to.
    public void display_computer_move() {
        System.out.println("The computer will go in row " + (move_row + 1) + " and column " + (move_col + 1) + "\n" );
    }

    // Play computers move and flip it's pieces.
    public void play_computer_move( String[][] game_board ) {
        // Set global othello board.
        othello_board = game_board;
        set_best_move();
        display_computer_move();
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
        // If we looped through whole board and never returned true then there are no valid moves
        return true;
     }

}
