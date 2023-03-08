public class SudokuApplication
{
    public static void main(String[] args) throws Exception
    {
        SudokuSolver brd = new SudokuBoard();
        new SudokuBoardController(brd);
    }
}
