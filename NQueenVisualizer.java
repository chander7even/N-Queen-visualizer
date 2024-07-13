package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NQueenVisualizer extends JFrame {
    private JTextField nField;
    private JPanel solutionPanel;
    private final int CELL_SIZE = 50; // Size of each cell in the chessboard

    public NQueenVisualizer() {
        setTitle("N Queen Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("N: "));
        nField = new JTextField("4", 5);
        controlPanel.add(nField);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());
        controlPanel.add(startButton);

        solutionPanel = new JPanel();
        solutionPanel.setLayout(new GridLayout(0, 2, 10, 10));

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(solutionPanel), BorderLayout.CENTER);
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int n = Integer.parseInt(nField.getText());
            solutionPanel.removeAll();
            List<int[]> solutions = solveNQueens(n);
            for (int[] solution : solutions) {
                solutionPanel.add(createChessBoard(solution));
            }
            solutionPanel.revalidate();
            solutionPanel.repaint();
        }
    }

    private List<int[]> solveNQueens(int n) {
        List<int[]> solutions = new ArrayList<>();
        int[] board = new int[n];
        solve(0, n, board, solutions);
        return solutions;
    }

    private void solve(int row, int n, int[] board, List<int[]> solutions) {
        if (row == n) {
            solutions.add(board.clone());
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isSafe(row, col, board)) {
                board[row] = col;
                solve(row + 1, n, board, solutions);
            }
        }
    }

    private boolean isSafe(int row, int col, int[] board) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    private JPanel createChessBoard(int[] solution) {
        int n = solution.length;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(n, n));
        panel.setPreferredSize(new Dimension(n * CELL_SIZE, n * CELL_SIZE));

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                JPanel cell = new JPanel();
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if ((row + col) % 2 == 0) {
                    cell.setBackground(Color.WHITE);
                } else {
                    cell.setBackground(Color.GRAY);
                }
                if (solution[row] == col) {
                    JLabel queenLabel = new JLabel(new ImageIcon("queen.png"));
                    cell.add(queenLabel);
                }
                panel.add(cell);
            }
        }
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NQueenVisualizer frame = new NQueenVisualizer();
            frame.setVisible(true);
        });
    }
}
