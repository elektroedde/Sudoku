import java.awt.Container;
import java.awt.GridLayout;
import java.util.concurrent.TimeUnit;
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
    { "Select sudoku", "Empty", "Sudoku 1", "Sudoku 2", "Sudoku 3" };

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
            // Takes the current board values and adds them to the sudoku matrix
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
            String currentSelection = (String) presets.getSelectedItem();
            if (currentSelection.compareTo("Empty") == 0)
            {
                sudokuMatrix.clear();
            } 
            else if (currentSelection.compareTo("Sudoku 1") == 0)
            {
                setPreset1(sudokuMatrix);
            } 
            else if (currentSelection.compareTo("Sudoku 2") == 0)
            {
                setPreset2(sudokuMatrix);
            } 
            else if (currentSelection.compareTo("Sudoku 3") == 0)
            {
                setPreset3(sudokuMatrix);
            }

            updateBoard(sudokuMatrix);
        });

        exitButton.addActionListener(e ->
        {
            for(int i = 0; i < 9; i++)
            {
                for(int j = 0; j < 9; j++)
                {
                    sudokuMatrix.add(i, j, 5);
                    try
                    {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e1)
                    {
                        System.out.println("TEEE");
                    }
                    updateBoard(sudokuMatrix);
                }
            }
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

    private void setPreset1(SudokuSolver sudokuMatrix)
    {
        sudokuMatrix.clear();
        sudokuMatrix.add(2, 0, 6);
        sudokuMatrix.add(1, 1, 8);
        sudokuMatrix.add(2, 2, 2);
        sudokuMatrix.add(1, 5, 7);
        sudokuMatrix.add(0, 6, 2);
        sudokuMatrix.add(1, 7, 9);
        sudokuMatrix.add(2, 6, 5);
        sudokuMatrix.add(3, 1, 7);
        sudokuMatrix.add(3, 4, 6);
        sudokuMatrix.add(4, 3, 9);
        sudokuMatrix.add(4, 5, 1);
        sudokuMatrix.add(5, 4, 2);
        sudokuMatrix.add(5, 7, 4);
        sudokuMatrix.add(6, 2, 5);
        sudokuMatrix.add(6, 6, 6);
        sudokuMatrix.add(6, 8, 3);
        sudokuMatrix.add(7, 1, 9);
        sudokuMatrix.add(7, 3, 4);
        sudokuMatrix.add(7, 7, 7);
        sudokuMatrix.add(8, 2, 6);
    }

    private void setPreset2(SudokuSolver sudokuMatrix)
    {
        sudokuMatrix.clear();
        sudokuMatrix.add(0, 3, 8);
        sudokuMatrix.add(0, 5, 1);
        sudokuMatrix.add(1, 7, 4);
        sudokuMatrix.add(1, 8, 3);
        sudokuMatrix.add(2, 0, 5);
        sudokuMatrix.add(3, 4, 7);
        sudokuMatrix.add(3, 6, 8);
        sudokuMatrix.add(4, 6, 1);
        sudokuMatrix.add(5, 1, 2);
        sudokuMatrix.add(5, 4, 3);
        sudokuMatrix.add(6, 0, 6);
        sudokuMatrix.add(6, 7, 7);
        sudokuMatrix.add(6, 8, 5);
        sudokuMatrix.add(7, 2, 3);
        sudokuMatrix.add(7, 3, 4);
        sudokuMatrix.add(8, 3, 2);
        sudokuMatrix.add(8, 6, 6);
    }

    private void setPreset3(SudokuSolver sudokuMatrix)
    {
        int[][] test = {
            {1, 2, 3, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

        //sudokuMatrix.add(0, 2, 8);
        //sudokuMatrix.add(0, 5, 9);
        //sudokuMatrix.add(0, 7, 6);
        //sudokuMatrix.add(0, 8, 2);
        //sudokuMatrix.add(1, 8, 5);
        //sudokuMatrix.add(2, 0, 1);
        //sudokuMatrix.add(2, 2, 2);
        //sudokuMatrix.add(2, 3, 5);
        //sudokuMatrix.add(3, 3, 2);
        //sudokuMatrix.add(3, 4, 1);
        //sudokuMatrix.add(3, 7, 9);
        //sudokuMatrix.add(4, 1, 5);
        //sudokuMatrix.add(4, 6, 6);
        //sudokuMatrix.add(5, 0, 6);
        //sudokuMatrix.add(5, 7, 2);
        //sudokuMatrix.add(5, 8, 8);
        //sudokuMatrix.add(6, 0, 4);
        //sudokuMatrix.add(6, 1, 1);
        //sudokuMatrix.add(6, 3, 6);
        //sudokuMatrix.add(6, 5, 8);
        //sudokuMatrix.add(7, 0, 8);
        //sudokuMatrix.add(7, 1, 6);
        //sudokuMatrix.add(7, 4, 3);
        //sudokuMatrix.add(7, 6, 1);
        //sudokuMatrix.add(8, 6, 4);
        sudokuMatrix.clear();
        sudokuMatrix.setMatrix(test);
    }
}