import java.util.Scanner;

/**
 * TicTacToe game with backtracking (minimax) AI.
 * Difficulty and search depth can be set.
 */
public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private int maxDepth;
    private int difficulty; // 1 = easy, 2 = medium, 3 = hard

    public TicTacToe(int difficulty, int maxDepth) {
        this.board = new char[3][3];
        this.currentPlayer = 'X';
        this.maxDepth = maxDepth;
        this.difficulty = difficulty;
        initializeBoard();
    }

    // Initialize the board with empty cells
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Print the current board
    public void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    // Check if the board is full
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    // Check for a win
    private char checkWinner() {
        // Rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0];
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[0][i];
        }
        // Diagonals
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0];
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2];
        return ' ';
    }

    // Make a move
    public boolean makeMove(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ') {
            return false;
        }
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return true;
    }

    // AI move using minimax with depth and difficulty
    public void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int moveRow = -1, moveCol = -1;
        char aiPlayer = currentPlayer;
        char humanPlayer = (aiPlayer == 'X') ? 'O' : 'X';

        // For easy/medium, sometimes make a random move
        if (difficulty == 1 || (difficulty == 2 && Math.random() < 0.4)) {
            // Easy: random move
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = aiPlayer;
                        currentPlayer = humanPlayer;
                        return;
                    }
                }
            }
        }

        // Hard/Medium: minimax
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = aiPlayer;
                    int score = minimax(0, false, aiPlayer, humanPlayer);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        moveRow = i;
                        moveCol = j;
                    }
                }
            }
        }
        if (moveRow != -1 && moveCol != -1) {
            board[moveRow][moveCol] = aiPlayer;
            currentPlayer = humanPlayer;
        }
    }

    // Minimax algorithm with depth limit
    private int minimax(int depth, boolean isMaximizing, char aiPlayer, char humanPlayer) {
        char winner = checkWinner();
        if (winner == aiPlayer) return 10 - depth;
        if (winner == humanPlayer) return depth - 10;
        if (isBoardFull() || depth >= maxDepth) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = aiPlayer;
                        int score = minimax(depth + 1, false, aiPlayer, humanPlayer);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = humanPlayer;
                        int score = minimax(depth + 1, true, aiPlayer, humanPlayer);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    // Main game loop for human vs AI
    public void playGame(Scanner scanner) {
        while (true) {
            printBoard();
            char winner = checkWinner();
            if (winner != ' ') {
                System.out.println("Winner: " + winner);
                break;
            }
            if (isBoardFull()) {
                System.out.println("Draw!");
                break;
            }
            if (currentPlayer == 'X') {
                System.out.print("Enter your move (row and column 0-2): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                if (!makeMove(row, col)) {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                System.out.println("AI is making a move...");
                aiMove();
            }
        }
    }

    // Example main method
    public static void main(String[] args) {
        // Difficulty: 1 = easy, 2 = medium, 3 = hard
        // Max depth: e.g., 3 for medium, 9 for hard
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe(3, 9);
        game.playGame(scanner);
        scanner.close();
    }
}
