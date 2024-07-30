import java.io.*;
import java.util.*;

public class OthelloGame {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Welcome to Othello!\n" +
                "choose a number between 1 and 3");
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Menu:");
            System.out.println("1. Start a New Game");
            System.out.println("2. Load a Game");
            System.out.println("3. Quit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    loadGame();
                    break;
                case 3:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void startNewGame() {
        System.out.print("Enter Player 1 name: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter Player 2 name: ");
        String name2 = scanner.nextLine();

        player1 = new Player(name1, 'X');
        player2 = new Player(name2, 'O');
        currentPlayer = player1;

        //create new board
        board = new Board();
        play();
    }

    private void loadGame() {
        System.out.print("Enter filename to load an old game: ");
        String filename = scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String name1 = reader.readLine();
            String name2 = reader.readLine();
            String currentPlayerName = reader.readLine();
            player1 = new Player(name1, 'X');
            player2 = new Player(name2, 'O');
            currentPlayer = currentPlayerName.equals(name1) ? player1 : player2;
            board = new Board();
            board.loadBoardState(reader.readLine());
            play();
        } catch (IOException e) {
            System.out.println("Failed to load game: " + e.getMessage());
        }
    }

    private void play() {
        while (true) {
            board.draw();
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getDisk() + ")");
            if (board.hasValidMoves(currentPlayer.getDisk())) {
                System.out.println("Enter your move (row + space + column) or type 'save' to save the game:");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("save")) {
                    saveGame();
                    return;
                }
                String[] parts = input.split(" ");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                if (board.isValidMove(row, col, currentPlayer.getDisk())) {
                    board.makeMove(row, col, currentPlayer.getDisk());
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                System.out.println("No valid moves available. Skipping turn.");
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
            }

            if (board.isGameOver()) {
                board.draw();
                System.out.println("Game over!");
                System.out.println("Winner: " + board.getWinner(player1, player2));
                break;
            }
        }
    }

    private void saveGame() {
        System.out.print("Enter filename to save: ");
        String filename = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(player1.getName());
            writer.newLine();
            writer.write(player2.getName());
            writer.newLine();
            writer.write(currentPlayer.getName());
            writer.newLine();
            writer.write(board.getBoardState());
            writer.newLine();
            System.out.println("Your game is saved.");
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }
}