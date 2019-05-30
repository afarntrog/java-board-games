import java.util.Scanner;

public class OthelloGame {

    static int comp_total_wins = 0;  // Store total running total wins
    static int human_total_wins = 0;
    public static void main(String[] args) {
        instructions();

        // Play, and then ask user if they want to play again.
        do {
            Board game_board = new Board(); // Initialize board
            Human human_player = new Human( "X" );  // Initialize human player
            Computer computer_player = new Computer( "O" ); // Initialize computer player
            game_board.board_printer(); // Display board to begin.

            // Play until game is over.
            do {
                play_human(human_player, game_board);
                play_computer(computer_player, game_board);
            } while ( !total_game_over(computer_player, human_player, game_board) );

            // Get winner for game.
            tally_winner(computer_player, human_player, game_board);
            // Output game over message.
            game_over_message( computer_player, human_player, game_board );

           } while( play_again() );

           // Output farewell message and all game scores.
           System.out.println("Thank you for playing.");
           game_stats();
    } // End Main
    


    /**
     * This method gets the move row or column.
     */
    private static int get_move_value(String label) {
        Scanner input = new Scanner(System.in);
        int move_int;
        int UPPER_BOUND = 9;
        int LOWER_BOUND = 0;
        do {
            System.out.print("Enter the " + label + " number: ");
            move_int = input.nextInt();
        } while( !Validate.int_validator(UPPER_BOUND, LOWER_BOUND, move_int) );
        return move_int;
    }
    // END get move

    /**
     * If game is over for both players then return false, else return true;
     */
    public static boolean total_game_over(Computer comp_player, Human human_player, Board game_board) {
        return comp_player.game_is_over(game_board.get_board()) 
               && human_player.game_is_over(game_board.get_board());
    }

    /**
     * This will display the winner and scores to the console
     */
    public static void display_winner(Computer comp_player, Human human_player, Board game_board) {
        System.out.println( get_winner(comp_player, human_player, game_board) + " won this game!");
        System.out.println("Score tally:");
        System.out.println("Computer total = " + comp_player.score_tally(game_board.get_board()));
        System.out.println("Human total = " + human_player.score_tally(game_board.get_board()));
    }



    // Get and return the winner name as a String
    public static String get_winner(Computer comp_player, Human human_player, Board game_board) {
        if (comp_player.score_tally(game_board.get_board()) > human_player.score_tally(game_board.get_board())) {
            return "Computer";
        }
        return "Human";
    }

    // Get the winner and add 1 point to their tally.
    public static void tally_winner(Computer comp_player, Human human_player, Board game_board) {
        if ( get_winner(comp_player, human_player, game_board).equals("Computer") )
            comp_total_wins += 1;
        else
            human_total_wins += 1;     
    }

    // Display the total amount of wins per player throughout all games.
    public static void game_stats() {
        System.out.println("The computer won " + comp_total_wins + " game(s) in total.");
        System.out.println("Human won " + human_total_wins + " game(s) in total.");
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
    public static void play_human( Human human_player, Board game_board ) {
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
    public static void play_computer(Computer computer_player, Board game_board) {
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
    public static void game_over_message(Computer computer_player, Human human_player, Board game_board) {
        System.out.println("Game is over!!!");
        display_winner(computer_player, human_player, game_board);
        game_board.board_printer();
    }

    public static void instructions() {
        System.out.println("\n\nWelcome to Othello!\n"
                           + "If you cannot move your turn is skipped.\n"
                           + "If both players cannot move or the board is full, it is game over.\n");
    }
}