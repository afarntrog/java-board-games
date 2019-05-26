import java.util.Scanner;

public class OthelloGame {
    public static void main(String[] args) {
        Board game_board = new Board();
        Human human_player = new Human( "X" );
        Computer computer_player = new Computer( "O" );

        game_board.board_printer(); //Test

        // Make sure whole game is not over.
        do {
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


            if ( !computer_player.game_is_over(game_board.get_board() )) {
                computer_player.play_computer_move( game_board.get_board() );
                game_board.set_board(computer_player.my_board());
                game_board.board_printer();
            }
        } while ( !total_game_over(computer_player, human_player, game_board) );

        System.out.println("Game is over!!!");
        display_winner(computer_player, human_player, game_board);
        game_board.board_printer();
    }

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
     * This will display the winner to the console
     */
    public static void display_winner(Computer comp_player, Human human_player, Board game_board) {
        if (comp_player.score_tally(game_board.get_board()) > human_player.score_tally(game_board.get_board())) {
            System.out.println("Computer won!");
        }
        else {
            System.out.println("You win!");
        }
        System.out.println("Score tally:");
        System.out.println("Computer total = " + comp_player.score_tally(game_board.get_board()));
        System.out.println("Human total = " + human_player.score_tally(game_board.get_board()));
    }

    public static void tally_winner() {

    }
}