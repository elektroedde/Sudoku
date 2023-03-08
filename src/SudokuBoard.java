public class SudokuBoard implements SudokuSolver
{
    private int[][] board;

    SudokuBoard()
    {
        board = new int[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = 0;
            }
        }
    }

    @Override
    public boolean solve()
    {
        return solve(0, 0);
    }

    private boolean solve(int row, int col)
    {
        if (row == 9)
        {
            return true;
        }

        if (col == 9)
        {
            return solve(row + 1, 0);
        }

        for (int i = 1; i < 10; i++)
        {
            {
                if (get(row, col) == 0)
                {
                    add(row, col, i);
                } else
                {
                    return solve(row, col + 1);
                }

                if (isValid())
                {
                    if (solve(row, col + 1))
                    {
                        return true;
                    } else
                    {
                        remove(row, col);
                    }
                } else
                {
                    remove(row, col);
                }
            }
        }
        return false;
    }

    @Override
    public void add(int row, int col, int digit)
    {
        if (row > 8 || row < 0 || col > 8 || col < 0 || digit < 0 || digit > 9)
        {
            throw new IllegalArgumentException();
        }
        board[row][col] = digit;
    }

    @Override
    public void remove(int row, int col)
    {
        if (row > 8 || row < 0 || col > 8 || col < 0)
        {
            throw new IllegalArgumentException();
        }
        board[row][col] = 0;
    }

    @Override
    public int get(int row, int col)
    {
        if (row > 8 || row < 0 || col > 8 || col < 0)
        {
            throw new IllegalArgumentException();
        }
        return board[row][col];
    }

    @Override
    public boolean isValid()
    {
        // Row checker
        for (int row = 0; row < 9; row++)
        {
            for (int i = 0; i < 9; i++)
            {
                for (int j = i + 1; j < 9; j++)
                {
                    if (board[row][i] != 0 && board[row][i] == board[row][j])
                        return false;
                }
            }
        }

        // Column checker
        for (int col = 0; col < 9; col++)
        {
            for (int i = 0; i < 9; i++)
            {
                for (int j = i + 1; j < 9; j++)
                {
                    if (board[i][col] != 0 && board[i][col] == board[j][col])
                        return false;
                }
            }
        }

        // Region checker
        for (int region = 1; region <= 9; region++)
        {
            if (!regionChecker(region))
                return false;
        }

        return true;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = 0;
            }
        }
    }

    @Override
    public void setMatrix(int[][] m)
    {
        if (m.length != 9 || m[0].length != 9)
        {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = m[i][j];
            }
        }
    }

    @Override
    public int[][] getMatrix()
    {
        return board;
    }

    private boolean regionChecker(int region)
    {
        if (region < 1 || region > 9)
        {
            throw new IllegalArgumentException();
        }
        int toRow = 0;
        int toCol = 0;

        switch (region)
        {
            case 1:
                toRow = 3;
                toCol = 3;
                break;
            case 2:
                toRow = 3;
                toCol = 6;
                break;
            case 3:
                toRow = 3;
                toCol = 9;
                break;
            case 4:
                toRow = 6;
                toCol = 3;
                break;
            case 5:
                toRow = 6;
                toCol = 6;
                break;
            case 6:
                toRow = 6;
                toCol = 9;
                break;
            case 7:
                toRow = 9;
                toCol = 3;
                break;
            case 8:
                toRow = 9;
                toCol = 6;
                break;
            case 9:
                toRow = 9;
                toCol = 9;
                break;
            default:
                break;
        }

        int[] regionChecker = new int[9];
        int k = 0;

        // Add digits from region to vector
        for (int i = toRow - 3; i < toRow; i++)
        {
            for (int j = toCol - 3; j < toCol; j++)
            {
                regionChecker[k] = board[i][j];
                k++;
            }
        }

        // Check vector for duplicates
        for (int i = 0; i < 9; i++)
        {
            for (int j = i + 1; j < 9; j++)
            {
                if (regionChecker[i] != 0 && regionChecker[i] == regionChecker[j])
                {
                    return false;
                }
            }
        }
        return true;
    }
}
