import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver extends JFrame {
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int EMPTY = 0;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                if ((row / SUBGRID_SIZE + col / SUBGRID_SIZE) % 2 == 0) {
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                } else {
                    cells[row][col].setBackground(Color.WHITE);
                }
                panel.add(cells[row][col]);
            }
        }

        JButton solveButton = new JButton("Solve");
        solveButton.setFont(new Font("Arial", Font.BOLD, 20));
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = new int[SIZE][SIZE];
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        String text = cells[row][col].getText();
                        if (!text.isEmpty() && text.matches("\\d")) {
                            board[row][col] = Integer.parseInt(text);
                        } else {
                            board[row][col] = EMPTY;
                        }
                    }
                }
                if (solveSudoku(board)) {
                    for (int row = 0; row < SIZE; row++) {
                        for (int col = 0; col < SIZE; col++) {
                            cells[row][col].setText(String.valueOf(board[row][col]));
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Sudoku solved successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Unsolvable Sudoku puzzle.");
                }
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 20));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        cells[row][col].setText("");
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            } else {
                                board[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % SUBGRID_SIZE;
        int startCol = col - col % SUBGRID_SIZE;
        for (int i = startRow; i < startRow + SUBGRID_SIZE; i++) {
            for (int j = startCol; j < startCol + SUBGRID_SIZE; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuSolver().setVisible(true);
            }
        });
    }
}
