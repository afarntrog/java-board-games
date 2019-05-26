public class Human extends PlayerLogic {

    // String BLANK_PIECE = " ";


    // This will call the super constructor.
    public Human(String piece_color) {
        super(piece_color);
        System.out.println(PLAYER_PIECE);
    }


    /**
     * This will test if game is over for this player. If it is then it'll return true and print out message
     * telling player turn is over.
     * @param param_othello_board
     * @return
     */
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
        System.out.println("You cannot move. Turn skipped!");
        return true;
     }
}
