import java.util.Scanner;

public class OthelloGame {

    static int comp_total_wins = 0;  // Store total running total wins
    static int human_total_wins = 0;
    static int tie_total_wins = 0;
    static Board game_board;
    static Human human_player = new Human( "X" );  // Initialize human player
    static Computer computer_player = new Computer( "O" ); // Initialize computer player

    public static void main(String[] args) {
        instructions(); // Display instructions.
        // Play, and then ask user if they want to play again.
        do {
            game_board = new Board(); // Initialize new board at begining of each game.
            game_board.board_printer(); // Display board to begin.

            // Play until game is over.
            do {
                play_human();
                play_computer();
            } while ( !total_game_over() );

            // Get winner for game.
            tally_winner();
            // Output game over message.
            game_over_message();

           } while( play_again() );

           // Output farewell message and all game scores.
           System.out.println("Thank you for playing.\n");
           game_stats();
    } // End Main
    


    /**
     * This method gets the move row or column.
     * It makes sure it is valid by using the validate utility class.
     */
    private static int get_move_value(String label) {
        Scanner input = new Scanner(System.in);
        int move_int;
        int UPPER_BOUND = 9;
        int LOWER_BOUND = 0;
        do {
            System.out.print("Enter the " + label + " number: ");
            move_int = input.nextInt();

            if ( !Validate.int_validator(UPPER_BOUND, LOWER_BOUND, move_int) ) {
                System.out.println("ERROR: Enter valid number between: " + UPPER_BOUND + " and " + LOWER_BOUND );
            }

        } while( !Validate.int_validator(UPPER_BOUND, LOWER_BOUND, move_int) );
        return move_int;
       
    }

    /**
     * If game is over for both players then return false, else return true;
     */
    public static boolean total_game_over() {
        return computer_player.game_is_over(game_board.get_board()) 
               && human_player.game_is_over(game_board.get_board());
    }

    /**
     * This will display the winner and scores to the console
     */
    public static void display_winner() {
        System.out.println( get_winner() + " won this game!");
        System.out.println("Score tally:");
        System.out.println("Computer total = " + computer_player.score_tally(game_board.get_board()));
        System.out.println("Human total = " + human_player.score_tally(game_board.get_board()));
    }



    // Get and return the winner name as a String
    public static String get_winner() {
        if (computer_player.score_tally(game_board.get_board()) > human_player.score_tally(game_board.get_board()))
            return "Computer";
        else if (computer_player.score_tally(game_board.get_board()) < human_player.score_tally(game_board.get_board()))
            return "Human";
        else
            return "Tie";
    }

    // Get the winner and add 1 point to their tally.
    public static void tally_winner() {
        if ( get_winner().equals("Computer") )
            comp_total_wins += 1;
        else if ( get_winner().equals("Human") ) 
            human_total_wins += 1;    
        else
            tie_total_wins += 1;
    }

    // Display the total amount of wins per player throughout all games.
    public static void game_stats() {
        System.out.println("The computer won " + comp_total_wins + " game(s) in total.");
        System.out.println("Human won " + human_total_wins + " game(s) in total.");
        System.out.println("The human and the computer tied " + tie_total_wins + " game(s) in total.");
        System.out.println("Human won %" + ((double) human_total_wins/(human_total_wins + comp_total_wins + tie_total_wins))*100 + " of the game(s).");
    }

    /**
     * Ask player if he wants to play again. If yes return true;
     * @return booleans
     */
    public static boolean play_again() {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to play again? [y/n] ");
        return input.nextLine().toLowerCase().charAt(0) == 'y';
    }


    // Execute human players move.
    public static void play_human() {
        // Make sure game is not over for this player.
        if (!human_player.game_is_over(game_board.get_board()) ) {
            // Get move
            int move_row = get_move_value("row") -1;
            int move_col = get_move_value("column") -1;
            // Check if human move is valid.
            while (!human_player.move_validator( game_board.get_board(), move_row, move_col)) {
                System.out.println("ERROR: Please enter valid move\n");
                move_row = get_move_value("row") -1;
                move_col = get_move_value("column") -1;
            }
            human_player.set_move(move_row, move_col);
            game_board.set_board(human_player.my_board());
            game_board.board_printer(); //Test
        }
        else {
            System.out.println("You cannot go this turn. Turn skipped!\n");
        }
    }

    // Execute computers turn
    public static void play_computer() {
        if ( !computer_player.game_is_over(game_board.get_board() )) {
            computer_player.play_computer_move( game_board.get_board() );
            game_board.set_board(computer_player.my_board());
            game_board.board_printer();
        }
        else {
            System.out.println("Computer cannot move. You go instead!\n");
        }
    }

    // Output game message to the players.
    public static void game_over_message() {
        System.out.println("Game is over!!!");
        display_winner();
        game_board.board_printer();
    }

    public static void instructions() {
        System.out.println("\n\nWelcome to Othello!\n"
                           + "If you cannot move your turn is skipped.\n"
                           + "If both players cannot move or the board is full, it is game over.");
        System.out.println("Your playing piece is: " + human_player.get_piece() + "\n");
    }
}