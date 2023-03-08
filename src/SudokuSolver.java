public interface SudokuSolver {
	/**
	 * Solves sudoku
	 */
	boolean solve();

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	void add(int row, int col, int digit);

	/**
	 * Removes digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @throws IllegalArgumentException if row or col is outside the range
	 *                                  [0..9]
	 */
	void remove(int row, int col);

	/**
	 * Retrieves digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @throws IllegalArgumentException if row or col is outside the range
	 *                                  [0..9]
	 */
	int get(int row, int col);

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 */
	boolean isValid();

	/**
	 * Clears the sudoku matrix by filling it with 0. The digit 0 represents an empty box.
	 */
	void clear();

	/**
	 * Fills the sudoku matrix with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	void setMatrix(int[][] m);

	/**
	 * Retrieves the sudoku matrix
	 * 
	 * @return matrix test
	 */
	int[][] getMatrix();
}
