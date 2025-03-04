import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BlockStacker extends JPanel implements ActionListener {
    private static final int GRID_ROWS = 20, GRID_COLS = 10, CELL_SIZE = 30;
    private int[][] grid = new int[GRID_ROWS][GRID_COLS];
    private Timer gameTimer;
    private Queue<int[][]> shapeQueue = new LinkedList<>();
    private int[][] activeShape;
    private int shapeRow = 0, shapeCol = GRID_COLS / 2;
    private Random rand = new Random();
    private boolean isGameOver = false;
    private int playerScore = 0;
    private int fallSpeed = 500; // Initial speed in milliseconds

    public BlockStacker() {
        setPreferredSize(new Dimension(GRID_COLS * CELL_SIZE, GRID_ROWS * CELL_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                processInput(e.getKeyCode());
            }
        });
        spawnShape();
        gameTimer = new Timer(fallSpeed, this);
        gameTimer.start();
    }

    private void spawnShape() {
        int[][] shape = {{1, 1, 1}, {0, 1, 0}}; // Default "T" shape
        shapeQueue.add(shape);
        activeShape = shapeQueue.poll();
        shapeRow = 0;
        shapeCol = GRID_COLS / 2;
        if (!isValidPosition(shapeRow, shapeCol)) {
            isGameOver = true;
            gameTimer.stop();
            repaint();
        }
    }

    private boolean isValidPosition(int newRow, int newCol) {
        if (isGameOver) return false;
        for (int r = 0; r < activeShape.length; r++) {
            for (int c = 0; c < activeShape[0].length; c++) {
                if (activeShape[r][c] == 1) {
                    int gridRow = newRow + r, gridCol = newCol + c;
                    if (gridRow >= GRID_ROWS || gridCol < 0 || gridCol >= GRID_COLS ||
                        (gridRow >= 0 && grid[gridRow][gridCol] == 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void lockShape() {
        for (int r = 0; r < activeShape.length; r++) {
            for (int c = 0; c < activeShape[0].length; c++) {
                if (activeShape[r][c] == 1) {
                    int gridRow = shapeRow + r;
                    if (gridRow < 0) {
                        isGameOver = true;
                        gameTimer.stop();
                        repaint();
                        return;
                    }
                    grid[gridRow][shapeCol + c] = 1;
                }
            }
        }
        clearFullRows();
        spawnShape();
    }

    private void clearFullRows() {
        int clearedRows = 0;

        // Check and clear full rows
        for (int r = GRID_ROWS - 1; r >= 0; r--) {
            boolean isFull = true;
            for (int c = 0; c < GRID_COLS; c++) {
                if (grid[r][c] == 0) {
                    isFull = false;
                    break;
                }
            }

            // Clear the row and shift rows above it down
            if (isFull) {
                clearedRows++;
                for (int row = r; row > 0; row--) {
                    System.arraycopy(grid[row - 1], 0, grid[row], 0, GRID_COLS);
                }
                grid[0] = new int[GRID_COLS]; // Clear the top row
                r++; // Re-check the current row after shifting
            }
        }

        // Update score and adjust speed if rows were cleared
        if (clearedRows > 0) {
            playerScore += clearedRows * 100;
            updateSpeed();
        }
    }

    private void updateSpeed() {
        int newSpeed = Math.max(100, fallSpeed - 50 * (playerScore / 500));
        if (newSpeed != fallSpeed) {
            fallSpeed = newSpeed;
            gameTimer.setDelay(fallSpeed);
        }
    }

    private void processInput(int key) {
        if (isGameOver) {
            if (key == KeyEvent.VK_R) {
                restartGame();
                return;
            }
            return;
        }
        if (key == KeyEvent.VK_LEFT && isValidPosition(shapeRow, shapeCol - 1)) shapeCol--;
        if (key == KeyEvent.VK_RIGHT && isValidPosition(shapeRow, shapeCol + 1)) shapeCol++;
        if (key == KeyEvent.VK_DOWN && isValidPosition(shapeRow + 1, shapeCol)) shapeRow++;
        if (key == KeyEvent.VK_SPACE) {
            while (isValidPosition(shapeRow + 1, shapeCol)) {
                shapeRow++;
            }
            lockShape();
        }
        repaint();
    }

    private void restartGame() {
        grid = new int[GRID_ROWS][GRID_COLS];
        shapeQueue.clear();
        isGameOver = false;
        playerScore = 0;
        fallSpeed = 500;
        shapeRow = 0;
        shapeCol = GRID_COLS / 2;
        spawnShape();
        gameTimer.setDelay(fallSpeed);
        gameTimer.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver) return;
        if (isValidPosition(shapeRow + 1, shapeCol)) {
            shapeRow++;
        } else {
            lockShape();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grid
        g.setColor(Color.WHITE);
        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < GRID_COLS; c++) {
                if (grid[r][c] == 1) {
                    g.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE - 1, CELL_SIZE - 1);
                }
            }
        }

        // Draw the active shape
        if (!isGameOver) {
            g.setColor(Color.BLUE);
            for (int r = 0; r < activeShape.length; r++) {
                for (int c = 0; c < activeShape[0].length; c++) {
                    if (activeShape[r][c] == 1) {
                        g.fillRect((shapeCol + c) * CELL_SIZE, (shapeRow + r) * CELL_SIZE,
                                CELL_SIZE - 1, CELL_SIZE - 1);
                    }
                }
            }
        }

        // Display score and game over message
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + playerScore, 10, 25);
        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String gameOverMsg = "GAME OVER";
            String scoreMsg = "Final Score: " + playerScore;
            String restartMsg = "Press 'R' to Restart";

            int gameOverX = (GRID_COLS * CELL_SIZE - g.getFontMetrics().stringWidth(gameOverMsg)) / 2;
            int scoreX = (GRID_COLS * CELL_SIZE - g.getFontMetrics().stringWidth(scoreMsg)) / 2;
            int restartX = (GRID_COLS * CELL_SIZE - g.getFontMetrics().stringWidth(restartMsg)) / 2;

            int centerY = (GRID_ROWS * CELL_SIZE) / 2;
            g.drawString(gameOverMsg, gameOverX, centerY - 40);
            g.drawString(scoreMsg, scoreX, centerY);
            g.drawString(restartMsg, restartX, centerY + 40);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Block Stacker");
        BlockStacker game = new BlockStacker();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
