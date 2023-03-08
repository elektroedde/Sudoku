import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.NumberFormatter;

public class SudokuBoardController
{
    private JFormattedTextField[][] graphicalBoard;
    private String[] selections =
    { "Empty sudoku", "Sudoku 1", "Sudoku 2", "Sudoku 3" };

    public SudokuBoardController(SudokuSolver sudokuMatrix)
    {
        SwingUtilities.invokeLater(() -> createWindow(sudokuMatrix, "Sudoku", 700, 800));
    }

    private void createWindow(SudokuSolver sudokuMatrix, String title, int width, int height)
    {
        NumberFormatter sleepFormatter = new NumberFormatter();
        sleepFormatter.setValueClass(Integer.class);
        sleepFormatter.setMinimum(1);
        sleepFormatter.setMaximum(9);
        sleepFormatter.setAllowsInvalid(false);

        LineBorder lineBorder = new LineBorder(Color.black, 1, false);

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();

        JPanel sudokuPanel = new JPanel(new GridLayout(9, 9));
        graphicalBoard = new JFormattedTextField[9][9];

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                graphicalBoard[i][j] = new JFormattedTextField(sleepFormatter);
                graphicalBoard[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
                graphicalBoard[i][j].setBorder(lineBorder);
                graphicalBoard[i][j].setFont(new Font(Font.DIALOG, Font.BOLD, 25));

                if (isColoredTextField(i, j))
                    graphicalBoard[i][j].setBackground(new Color(153, 204, 255));

                sudokuPanel.add(graphicalBoard[i][j]);
            }
        }

        sudokuPanel.setPreferredSize(new Dimension(700, 700));

        JPanel optionsPanel = new JPanel();

        JButton solveButton = new JButton("Solve");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit program");
        JComboBox<String> presets = new JComboBox<>(selections);

        optionsPanel.add(solveButton);
        optionsPanel.add(clearButton);
        optionsPanel.add(presets);
        optionsPanel.add(exitButton);

        solveButton.addActionListener(e ->
        {
            boardValuesToMatrix(sudokuMatrix);

            if (!sudokuMatrix.solve())
                JOptionPane.showMessageDialog(sudokuPanel, "Unsolvable sudoku");

            updateBoard(sudokuMatrix);
        });

        clearButton.addActionListener(e ->
        {
            sudokuMatrix.clear();
            updateBoard(sudokuMatrix);
        });

        presets.addActionListener(e ->
        {
            if (presets.getSelectedIndex() == 0)
            {
                sudokuMatrix.clear();
            } 
            else if (presets.getSelectedIndex() == 1)
            {
                setPreset(1, sudokuMatrix);
            }
            else if (presets.getSelectedIndex() == 2)
            {
                setPreset(2, sudokuMatrix);
            } 
            else if (presets.getSelectedIndex() == 3)
            {
                setPreset(3, sudokuMatrix);
            }

            updateBoard(sudokuMatrix);
        });

        exitButton.addActionListener(e ->
        {
            System.exit(0);
        });

        pane.add(sudokuPanel, BorderLayout.NORTH);
        pane.add(optionsPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    private boolean isColoredTextField(int i, int j)
    {
        if ((i < 3 && j < 3) || (i < 3 && j > 5) || (i > 5 && j > 5) || ((i > 2 && i < 6) && (j > 2 && j < 6))
                || (i > 5 && j < 3))
        {
            return true;
        }
        return false;
    }

    private void updateBoard(SudokuSolver sudokuMatrix)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (sudokuMatrix.get(i, j) != 0)
                    graphicalBoard[i][j].setValue(sudokuMatrix.get(i, j));
                else
                    graphicalBoard[i][j].setValue(null);
            }
        }
    }

    private void boardValuesToMatrix(SudokuSolver sudokuMatrix)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (!graphicalBoard[i][j].getText().isEmpty())
                    sudokuMatrix.add(i, j, Integer.parseInt(graphicalBoard[i][j].getText()));
                else
                    sudokuMatrix.add(i, j, 0);
            }
        }
    }

    private void setPreset(int preset, SudokuSolver sudokuMatrix)
    {
        if(preset == 1)
        {
            int[][] sudokuPreset = {
                {0, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 8, 0, 0, 0, 7, 0, 9, 0},
                {6, 0, 2, 0, 0, 0, 5, 0, 0},
                {0, 7, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 9, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 4, 0},
                {0, 0, 5, 0, 0, 0, 6, 0, 3},
                {0, 9, 0, 4, 0, 0, 0, 7, 0},
                {0, 0, 6, 0, 0, 0, 0, 0, 0},
            };
        
            sudokuMatrix.clear();
            sudokuMatrix.setMatrix(sudokuPreset);
        }
        else if(preset == 2)
        {
            int[][] sudokuPreset = {
                {0, 0, 0, 8, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 4, 3},
                {5, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 7, 0, 8, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 2, 0, 0, 3, 0, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 7, 5},
                {0, 0, 3, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 6, 0, 0},
            };

            sudokuMatrix.clear();
            sudokuMatrix.setMatrix(sudokuPreset);

        }
        else if(preset == 3)
        {
            //aleks är fin och jag älskar henne
            int[][] sudokuPreset = {
                {0, 0, 8, 0, 0, 9, 0, 6, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 5},
                {1, 0, 2, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 1, 0, 0, 9, 0},
                {0, 5, 0, 0, 0, 0, 6, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 2, 8},
                {4, 1, 0, 6, 0, 8, 0, 0, 0},
                {8, 6, 0, 0, 3, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0}
        };

        sudokuMatrix.clear(); //
        sudokuMatrix.setMatrix(sudokuPreset);

        }
        else
        {
            JOptionPane.showMessageDialog(null, "No such preset");
        }
    }
}