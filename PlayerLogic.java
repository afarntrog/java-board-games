public class PlayerLogic {

    protected int move_row, move_col;
    protected final String PLAYER_PIECE;
    protected final String BLANK_PIECE = " ";
    protected final String OPPOSING_PLAYER_PIECE;
    protected String[][] othello_board;
    protected int turn_total_flips;

    // Constructor to set the players piece colors
    public PlayerLogic(String piece_color) {
        PLAYER_PIECE = piece_color;
        OPPOSING_PLAYER_PIECE = PLAYER_PIECE.equals("X") ? "O" : "X";
    }

    

    /** Move validator will call 3 methods and return true or false
     * box is empty
     * there is opposing pieces
     * ther is one of my own pieces at the end of the line
     * We only need for there to be at least 1 valid move. If there is then we know we can go there.
     */ 
    public boolean move_validator(String[][] param_othello_board, int param_move_row, int param_move_col) {
        // Set global values so all pieces can use them.
        move_row = param_move_row;
        move_col = param_move_col;
        othello_board = param_othello_board;

        // 1. make sure square is empty if not return false - invalid move
        if (!square_is_empty()) {
            return false;
        }

        // 2. Make at least 1 of the touching surrounding pieces is of opposing color.
        // 3. Even if piece is opposing make sure it current player has another one of it's own pieces in that row.
        if (!is_opposing_piece()) {
            return false;
        }
        return true;
    }

    // :: BEG MOVE VALIDATION ::
    // This makes sure that the box he wants to put his piece is empty.
    private boolean square_is_empty() {
        return othello_board[move_row][move_col].equals(BLANK_PIECE);
    } // End method
    
    /**
     * Will check to make sure there is an opposing piece on one of the 8 sides so that the move is legit.
     * Before you check, check with 'if' to make sure testing opposing piece will not throw an 'outOfBounds Error'.
     * Steps:
     * 1) Only test if you will not get an outOfBounds error
     * 2) Test to see if the immediate piece is an opposing piece
     * 3) if step is true then make sure that one of your pieces is on the end of that line so that it is a valid move
     */
    public boolean is_opposing_piece() {
        boolean is_valid = false;

        // Check immediate top
        if (move_row != 0) {
            if (othello_board[move_row - 1][move_col].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = vertical_up_check();
            }
            if (is_valid)
                return is_valid;
        }

        // Check immediate bottom
        if (move_row < othello_board.length -1) {
            if (othello_board[move_row + 1][move_col].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = vertical_down_check();
            }
            if (is_valid)
                return is_valid;
        }

        // Check immediate left
        if (move_col != 0) {
            if (othello_board[move_row][move_col - 1].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = horizontal_left_check();
            }
            if (is_valid)
                return is_valid;
        }

        // Check immediate right
        if (move_col < othello_board.length - 1) {
            if (othello_board[move_row][move_col + 1].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = horizontal_right_check();
            }
            if (is_valid)
                return is_valid;
        }

        // Check immediate top left
        if ( (move_row != 0) && (move_col != 0) ) {
            if (othello_board[move_row - 1][move_col - 1].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = left_up_check();
            }
            if (is_valid)
                return is_valid;
        }
        // Check immediate bottom left
        if ( (move_row < othello_board.length - 1) && (move_col != 0) ) {
            if (othello_board[move_row + 1][move_col - 1].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = left_down_check();
            }
            if (is_valid)
                return is_valid;
        }
        // Check immediate top right
        if ( (move_row > 0) && (move_col < othello_board.length - 1) ) {
            if (othello_board[move_row - 1][move_col + 1].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = right_up_check();
            }
            if (is_valid)
                return is_valid;
        }
        // Check immediate bottom right
        if ( (move_row < othello_board.length - 1) && (move_col < othello_board.length - 1) ) {
            if (othello_board[move_row + 1][move_col + 1].equals(OPPOSING_PLAYER_PIECE)) {
                is_valid = right_down_check();
            }
            if (is_valid)
                return is_valid;
        }
        return false;

    } // End method

    public boolean vertical_up_check() {
        // loop through starting from current move up board [meaning must DECREMENT,2 1 0 etc.] until the top of the board.
        // MUST subtract 1 from row to index. Bec we do NOT want the loop to check this row/current move rather move down 1 row.
        // We must include row 0 into the actual equation so therfore `index > -1`.
        for (int index = move_row - 1; index > -1; index--) {
            if ( othello_board[index][move_col].equals( BLANK_PIECE ) ) {
                return false;
            }
            if (othello_board[index][move_col].equals(PLAYER_PIECE)) {
                return true;
            }
        }
        return false;

    }// method
        
    public boolean vertical_down_check() {
        // loop through starting from current move down the board until you reach and check the last square.
        for (int index = move_row + 1; index < othello_board.length; index++) {
            if ( othello_board[index][move_col].equals( BLANK_PIECE ) ) {
                return false;
            }
            if (othello_board[index][move_col].equals(PLAYER_PIECE)) {
                return true;
            }
        }
        return false;

    }// method
    public boolean horizontal_right_check() {
        // loop through starting from current move across the board until you reach and check the last square.
        for (int index = move_col + 1; index < othello_board.length; index++) {
            if ( othello_board[move_row][index].equals( BLANK_PIECE ) ){
                return false;
            }
            if (othello_board[move_row][index].equals(PLAYER_PIECE)) {
                return true;
            }
        }
        return false;

    }// method

    public boolean horizontal_left_check() {
        for (int index = move_col - 1; index > -1; index--) {
            if ( othello_board[move_row][index].equals( BLANK_PIECE ) ){
                return false;
            }
            if (othello_board[move_row][index].equals(PLAYER_PIECE)) {
                return true;
            }
        }
        return false;

    }// method

    public boolean left_up_check(){
        // Setup starting position to begin search
        int row = move_row - 1;
        int col = move_col - 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row > -1 && col > -1){
            if (othello_board[row][col].equals( BLANK_PIECE )) {
                return false;
            }
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                return true;
            }
            // Increase both the row and column in unison so that you can move diagonally.
            row--;
            col--;
        }
        return false;

    }
    public boolean left_down_check(){
        // Setup starting position to begin search
        int row = move_row + 1;
        int col = move_col - 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row < othello_board.length && col > -1){
            if (othello_board[row][col].equals( BLANK_PIECE )) {
                return false;
            }
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                return true;
            }
            // Increase both the row and column in unison so that you can move diagonally.
            row++;
            col--;
        }
        return false;
    }
    public boolean right_up_check(){
        // Setup starting position to begin search
        int row = move_row - 1;
        int col = move_col + 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row > -1 && col < othello_board.length){
            if (othello_board[row][col].equals( BLANK_PIECE )) {
                return false;
            }
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                return true;
            }
            // Increase both the row and column in unison so that you can move diagonally.
            row--;
            col++;
        }
        return false;
    }

    public boolean right_down_check(){
        // Setup starting position to begin search
        int row = move_row + 1;
        int col = move_col + 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row < othello_board.length && col < othello_board.length) {
            if (othello_board[row][col].equals( BLANK_PIECE )) {
                return false;
            }
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                return true;
            }
            // Increase both the row and column in unison so that you can move diagonally.
            row++;
            col++;
        }
        return false;
    }
    // :: END MOVE VALIDATION ::





    // This accepts a move and then sets it on the board.
    public void set_move(int param_move_row, int param_move_col) {
        move_row = param_move_row;
        move_col = param_move_col;
        othello_board[move_row][move_col] = PLAYER_PIECE;

        flip_all_pieces();           
    }

    // PIECE FLIPPERS::

    /**
     * Flip all the pieces.
     * Start from the current piece and go down and up every row and angle
     * flipping pieces until every possible one is flipped.
     * Kepp a tally of how many pieces were flipped per turn and use it for computer game ai testing
     * 
     */
    public void flip_all_pieces(){

        // Make sure it is legal to flip in that direction, then flip if legal.
        if (vertical_down_check() ) {
            vertical_down_flipper();
        }
        if (vertical_up_check()) {
            vertical_up_flipper();  
        }
        if ( horizontal_right_check() ) {
            horizontal_right_flipper();
        }
        if ( horizontal_left_check() ){
            horizontal_left_flipper();
        }
        if ( left_up_check() ) {
            left_up_flipper();
        }
        if ( left_down_check() ) {
            left_down_flipper();
        }
        if ( right_up_check() ) {
            right_up_flipper();
        }
        if ( right_down_check() ) {
            right_down_flipper();
        }
    }

    /**
     * This will loop through all the pieces and flip them.
     * It begins from the current move row/col and travels down that row/angle flipping all
     * the pieces along the way until it encounters one of its own. Then it stops flipping.
     */
    public void vertical_down_flipper(){
        int total_flips = 0;

        // loop through starting from current move down the board until you reach and check the last square.
        for (int index = move_row + 1; index < othello_board.length; index++) {
            // If the current piece is equal to the piece of the player: opposite of target then break.
            if (othello_board[index][move_col].equals(PLAYER_PIECE)){
                break;
            }
            othello_board[index][move_col] = flip();
            total_flips += 1;
        }
        // Add amnt of flips in this direction.
        turn_total_flips += total_flips;
    }

    public void vertical_up_flipper() {
        int total_flips = 0;
        for (int index = move_row - 1; index > - 1; index--) {
            if (othello_board[index][move_col].equals(PLAYER_PIECE)) {
                break;
            }
            othello_board[index][move_col] = flip();
            total_flips += 1;
        }
            // Add amnt of flips in this direction.
            turn_total_flips += total_flips;
    }

    public void horizontal_right_flipper() {
        int total_flips = 0;
        for (int index = move_col + 1; index < othello_board.length; index++) {
            if (othello_board[move_row][index].equals(PLAYER_PIECE)) {
                break;
            }
            else{
                othello_board[move_row][index] = flip();
                total_flips += 1;
            }
        }
        // Add amnt of flips in this direction.
        turn_total_flips += total_flips; 
    }

    public void horizontal_left_flipper() {
        int total_flips = 0;
        for (int index = move_col - 1; index > -1; index--) {
            if (othello_board[move_row][index].equals(PLAYER_PIECE)) {
                break;
            }
            othello_board[move_row][index] = flip();
            total_flips += 1;
        }
            // Add amnt of flips in this direction.
            turn_total_flips += total_flips; 
    }

    public void left_up_flipper() {
        int total_flips = 0;
        int row = move_row - 1;
        int col = move_col - 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row > -1 && col > -1){
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                break;
            }
            // Flip pieces then decrement
            othello_board[row][col] = flip();
            total_flips += 1;
            // Increase both the row and column in unison so that you can move diagonally.
            row--;
            col--;
        }
        // Add amnt of flips in this direction.
        turn_total_flips += total_flips; 
    }

    public void left_down_flipper(){
        int total_flips = 0;
        // Setup starting position to begin search
        int row = move_row + 1;
        int col = move_col - 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row < othello_board.length && col > -1){
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                break;
            }
            // Flip pieces then increment
            othello_board[row][col] = flip();
            total_flips += 1;
            // Increase both the row and column in unison so that you can move diagonally.
            row++;
            col--;
        }
        // Add amnt of flips in this direction.
        turn_total_flips += total_flips; 
    }


    public void right_up_flipper(){
        int total_flips = 0;
        // Setup starting position to begin search
        int row = move_row - 1;
        int col = move_col + 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row > -1 && col < othello_board.length){
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                break;
            }
            // Flip pieces then increment
            othello_board[row][col] = flip();
            total_flips += 1;        
            // Increase both the row and column in unison so that you can move diagonally.
            row--;
            col++;
        }
        // Add amnt of flips in this direction.
        turn_total_flips += total_flips; 
    }


    public void right_down_flipper(){
        int total_flips = 0;
        // Setup starting position to begin search
        int row = move_row + 1;
        int col = move_col + 1;
        // Keep climbing up the board (by reducing the index) diagonally until you hit the last one on the top or right.
        while (row < othello_board.length && col < othello_board.length) {
            if (othello_board[row][col].equals(PLAYER_PIECE)) {
                break;
            }
            // Flip pieces then increment
            othello_board[row][col] = flip();
            total_flips += 1;         
            // Increase both the row and column in unison so that you can move diagonally.
            row++;
            col++;
        }
        // Add amnt of flips in this direction.
        turn_total_flips += total_flips; 
    }


    /**
     * This will get current player and returns the value you should flip the piece to.
     * @return flip value.
     */
    public String flip() {
        System.out.println(PLAYER_PIECE);
        return PLAYER_PIECE;
    }


    // Make a deep copy of the board.
    public String[][] my_board() {
        String[][] copy_board = new String[8][8];
        for (int row = 0; row < othello_board.length; row++) {
            for (int col = 0; col < othello_board.length; col++) {
                copy_board[row][col] = othello_board[row][col];
            }
        }
        return copy_board;
    }

     /**
      * This will add up the score
      * @param param_othello_board pass in othello board.
      * @return the score value
      */
     public int score_tally(String[][] param_othello_board) { 
        int score = 0;
        for (int row = 0; row < othello_board.length; row++) {
            for (int col = 0; col < othello_board[row].length; col++) {
                if ( othello_board[row][col].equals(PLAYER_PIECE) ) {
                    score += 1;
                }
            }
        }
        return score;
     }
}
