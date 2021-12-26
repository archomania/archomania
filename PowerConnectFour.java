/**
 * Create a class that hold methods for Power Connect Four game.
 * @author Jackie Nguyen
 */
public class PowerConnectFour 
{
	/**
	 *  The grid to contain tokens. Cells can be empty.
	 */
	private Column<Token>[] grid;
	/**
	 * an integer to keep track of player's turn.
	 */
	private static int i = 0;

	/**
	 * Initiate a reference.
	 */
	PowerConnectFour game;

	/**
	 *  The fixed number of columns the game grid should have.
	 */
	private static final int NUM_COLS = 7;

	/**
	 *  The minimum number of rows of the grid _for display_.
	 */
	private static final int MIN_ROWS = 6;

	/**
	 * The two players of the game.
	 * playerOne is always the first to make a move when the game starts.
	 */
	private static final Token playerOne = Token.RED;
	/**
	 * playerTwo goes second.
	 */
	private static final Token playerTwo = Token.YELLOW;

	/**
	 * The character used to represent empty cells when the grid is displayed.
	 */
	private static final Character empty = Character.valueOf('-');

	/**
	 * When grid is displayed, the top row of the grid should always be empty.
	 */  
	private static final int MARGIN_ROWS = 1;

	/**
	 * PowerConnect Four Constructor with no arguments.
	 * A grid with NUM_COLS columns is initiated.
	 */
	@SuppressWarnings("unchecked")	
	public PowerConnectFour() 
	{
		grid = (Column<Token>[]) new Column[NUM_COLS];
		for(int i = 0; i < NUM_COLS; i++)
		{
			grid[i] = (Column<Token>) new Column<Token>(MIN_ROWS);
		}
	}

	/**
	 * Count number of columns.
	 * @return number of columns of the grid.
	 */
	public int sizeCol() 
	{ 
		return grid.length;
	}

	/**
	 * Count number of row.
     * @return number of rows _for DISPLAY_ of the grid
     */
	public int sizeRow() 
	{ 
		return grid[0].capacity();
	}
	/**
	 * Check current size after pop, drop, power pop, power drop.
	 * @param col take an integer as column index.
	 */
	private void checkSizeRow(int col) //check if expand needed
	{
		
		if(grid[col].capacity()  <= grid[col].size())		
			expand();
		if(grid[col].capacity() > MIN_ROWS && grid[col].capacity() - grid[col].size() >= 2)
		{ 
			if(grid[0].capacity() - grid[0].size() >= 2 && grid[1].capacity() - grid[1].size() >= 2 
					&& grid[2].capacity() - grid[2].size() >= 2 && grid[3].capacity() - grid[3].size() >= 2
					&& grid[4].capacity() - grid[4].size() >= 2 && grid[5].capacity() - grid[5].size() >= 2
					&& grid[6].capacity() - grid[6].size() >= 2)
				shrink();
		}
	}

	/**
	 * Expand row size if space needed.
	 */
	@SuppressWarnings("unchecked")
	private void expand()
	{
		int currentSizeRow = sizeRow();
		Column<Token>[] temp = (Column<Token>[]) new Column[NUM_COLS];
		temp = grid;

		grid = (Column<Token>[]) new Column[NUM_COLS];
		for(int i = 0; i < NUM_COLS; i++)
		{
			grid[i] = (Column<Token>) new Column<Token>(currentSizeRow + 1);
			for(int j = 0; j < currentSizeRow; j++)
			{
				if(temp[i].get(j) == null)
				{
					getEmptySymbol();
				}
				else
					grid[i].add(j, temp[i].get(j));
			}
		}
	}

	/**
	 * Shrink row size if size falls below threshold.
	 */
	@SuppressWarnings("unchecked")
	private void shrink()
	{
		int currentSizeRow = sizeRow();
		Column<Token>[] temp = (Column<Token>[]) new Column[NUM_COLS];
		temp = grid;

		grid = (Column<Token>[]) new Column[NUM_COLS];	//due to type erasure, cannot use object
		for(int i = 0; i < NUM_COLS; i++)
		{
			grid[i] = (Column<Token>) new Column<Token>(currentSizeRow - 1);
			for(int j = 0; j < currentSizeRow; j++)
			{
				if(temp[i].get(j) == null)
				{
					getEmptySymbol();
				}
				else
					grid[i].add(j, temp[i].get(j));
			}
		}
	}

	/**
	 * O(1).
	 * @return the character defined for empty cells for display.
	 */
	public Character getEmptySymbol()
	{
		return empty; //default return, make sure to remove/change
	}

	/**
	 * Return token at the given column and row of the grid.
	 * For an invalid row/col index (out of the range of current display) 
	 * throw an IndexOutOfBoundsException.
	 * O(1)
	 * Return null if the cell at the given col and row is empty
	 * @param col take an integer as column index
	 * @param row take an integer as row index
	 * @return Object at given indexes
	 */
	public Token get(int col, int row)
	{
		try
		{
			if(col < 0 || row < 0 || col > NUM_COLS)
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			System.err.println("Col " + col + ", Row "+ row + " out of bounds!");
		}
		if(grid[col].get(row) != null)
		{
			return grid[col].get(row);
		}
		else
			return null;
	}
	/**
	 * For an invalid column index, throw an IndexOutOfBoundsException.
	 * O(1)
	 * @param col take an integer as column index
	 * @return column at the given index
	 */
	public Column<Token> getColumn(int col)
	{
		try
		{
			if(col < 0 || col > NUM_COLS)
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			System.err.println("Col " + col + " out of bounds!");
		}
		return grid[col]; 
	}
	/**
	 * O(1).
	 * @return the player that can make the next move.
	 */
	public Token currentPlayer()
	{
		Token red = playerOne;
		Token yellow = playerTwo;;
		
		if(i % 2 == 0)
		{
			return red;
		}
		else
		{
			return yellow;
		}
	}

	/**
	 * Current player drop a token at the given column.
	 * Switch to the other player only if the move can be made successfully.
	 * O(1)	 
	 * @param col take an integer as column index
	 * @return true if the move can be made; return false if the move cannot be made for any reason.
	 */
	public boolean drop(int col)
	{
		try
		{
			if(col < 0 || col > NUM_COLS)
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			return false;
		}
		if(col >= 0 || col < NUM_COLS)
		{
			if(grid[col].size() + 1 <= grid[col].capacity())
			{
				grid[col].add(currentPlayer()); 
				//currentPlayer();
				i++;
				checkSizeRow(col);
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}


	/**
	 * Current player drop/insert a token at the given column and row.
	 * O(N)	 	 
	 * @param col take an integer as column index
	 * @param row take an integer as row index
	 * @return true if the move can be made; return false  if the move
	 */
	public boolean powerDrop(int col, int row)
	{
		if(row>=1 && grid[col].get(row-1) == null )
		{
			return false;
		}
		else if(row != 0)
		{
			grid[col].add(row, currentPlayer());
			i++;
			checkSizeRow(col);
			return true;
		}
		else 
		{
			grid[col].add(row, currentPlayer());
			i++;
			checkSizeRow(col);
			return true;
		}
	}

	/**
	 * Current player pop a token from the given column.
	 * O(N
	 * @param col take an integer as column index
	 * @return true if the move can be made; return false
	 */
	public boolean pop(int col)
	{
		if (col < 0 || col > sizeCol())
			return false;
		if(currentPlayer() == grid[col].get(0))
		{
			for(int k = 1; k < grid[col].size(); k++)
			{
				grid[col].set(k -1, grid[col].get(k));
			}
			grid[col].set(grid[col].size() -1, null);
					
			i++;

			checkSizeRow(col);
			return true;
		}
		else
			return false; 
	}

	/**
	 * Current player pop/remove a token from the given column and row.
	 * tokens above the removed one need to be shifted to make sure there are no floating tokens in grid.
	 * O(N)
	 * @param col take an integer as column index
	 * @param row take an integer as row index
	 * @return true if the move can be made; return false if the move cannot be made for any reason.
	 */
	public boolean powerPop(int col, int row)
	{
		if(col < 0 || col > NUM_COLS || row < 0 || row > sizeRow())
			return false;

		if(currentPlayer() == grid[col].get(row))
		{
			grid[col].delete(row);
			i++;
			checkSizeRow(col);
			return true;
		}
		else
			return false;
	}

	/**
	 * Count and return the number of consecutive tokens for the given player in a row.
	 * O(1)	 
	 * @param col take an integer as column index
	 * @param row take an integer as row index
	 * @param player take the current player token
	 * @return 0 if out of bounds
	 */
	public int countRow(int col, int row, Token player)
	{
		int count = 0;
		if(player == grid[col].get(row))
		{
			for(int i = col; i < 7 ; i++)
			{
				if(grid[i].get(row) == player)
					count++;
				else
					break;
			}
			for(int i = col - 1; i >= 0; i--)
			{
				if(grid[i].get(row) == player)

					count++;
				else
					break;
			}
			return count;
		}
		else
			return 0; 
	}

	/**
	 * Count and return the number of consecutive tokens for the given player in a column.
	 * O(1)	 
	 * @param col take an integer as column index
	 * @param row take an integer as row index
	 * @param player take the current player token
	 * @return numbers of chosen tokens
	 */
	public int countCol(int col, int row, Token player)
	{
		int count = 0;
		if(player == grid[col].get(row))
		{
			for(int i = row; i < grid[0].capacity() ; i++)
			{
				if(grid[col].get(i) == player)
					count++;
				else
					break;
			}
			for(int i = row - 1; i >= 0; i--)
			{
				if(grid[col].get(i) == player)

					count++;
				else
					break;
			}
			return count;
		}
		else
			return 0;
	}

	/**
	 * Count and return the number of consecutive tokens for the given player diagonally.
	 * A major diagonal extends diagonally down and to the right as well as up and to the left.
	 * O(1)	 
	 * @param col take an integer as column index
	 * @param row take an integer as row index
	 * @param player take the current player token
	 * @return numbers of chosen tokens
	 */
	public int countMajorDiagonal(int col, int row, Token player)
	{
		int horizontal = col; //going side way
		int vertical = row; // going up and down
		int count = 0;
		if(player == grid[col].get(row))
		{
			while(horizontal < NUM_COLS && vertical >= 0)
			{
				if(grid[horizontal].get(vertical) == player)
				{
					count++;
				}
				else
					break;
				horizontal++;
				vertical--;
				
				if(horizontal == NUM_COLS || vertical == -1)
				{
					break;
				}
			}

			horizontal = col - 1;
			vertical = row + 1;

			while(horizontal >= -1 && vertical <= grid[0].capacity())
			{
				if(grid[horizontal].get(vertical) == player)
				{
					count++;
				}
				else
					break;
				horizontal--;
				vertical++;
				
				if(horizontal == - 1 || vertical == grid[0].capacity())
				{
					break;
				}
			}

			return count;
		}
		else
			return 0; 
	}

	/**
	 * Count and return the number of consecutive tokens for the given player diagonally.
	 * A minor diagonal extends diagonally down and to the left as well as up and to the right.
	 * O(1)	 
	 * @param col take an integer as column index
	 * @param row take an integer as row index
	 * @param player take the current player token
	 * @return numbers of chosen tokens
	 */
	public int countMinorDiagonal(int col, int row, Token player)
	{
		int horizontal = col; //going side way
		int vertical = row; // going up and down
		int count = 0;
		if(player == grid[col].get(row))
		{
			while(horizontal < NUM_COLS && vertical <= grid[0].capacity())
			{
				if(grid[horizontal].get(vertical) == player)
				{
					count++;
				}
				else
					break;

				if(horizontal == NUM_COLS - 1 || vertical == 0)
				{
					break;
				}
				horizontal++;
				vertical++;
			}

			horizontal = col - 1;
			vertical = row - 1;

			while(horizontal >= -1 && vertical >= 0)
			{
				if(grid[horizontal].get(vertical) == player)
				{
					count++;
				}
				else
					break;

				if(horizontal == - 1 || vertical == grid[0].capacity())
				{
					break;
				}
				horizontal--;
				vertical--;
			}

			return count;
		}
		else
			return 0;
	}


	//******************************************************
	//*******  DO NOT EDIT ANYTHING IN THIS SECTION  *******
	//*******        But do read this section!       *******
	//******************************************************

	/**
	 * The method that checks whether the specified player has four connected tokens
	 * horizontally, vertically, or diagonally.  It relies on the methods of countRow(),
	 * countCol(), countMajorDiagonal(), and countMinorDiagonal() to work correctly.
	 *
	 * @param player the token to be checked
	 * @return whether the given player has four tokens connected
	 */
	public boolean hasFourConnected(Token player){
		// Check whether the specified player has four tokens either in a row,
		// in a column, or in a diagonal line (major or minor). Return true if 
		// so; return false otherwise.	

		for (int j = 0; j<sizeCol(); j++){
			for (int i = 0; i<sizeRow(); i++){
				if (countRow(j, i, player)>=4 || countCol(j, i, player)>=4
						|| countMajorDiagonal(j, i, player)>=4 
						|| countMinorDiagonal(j, i, player)>=4 )
					return true;
			}
		}
		return false;

	}

	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//*******		Remember to add JavaDoc			 *******
	//******************************************************
	/**
	 * Main method to run the functions.
	 * @param args does not accept arguments.
	 */
	public static void main(String[] args) {
		
		// init with an empty grid
		PowerConnectFour myGame = new PowerConnectFour();
		if (myGame.sizeCol() == NUM_COLS && myGame.sizeRow() == MIN_ROWS
			&& myGame.getColumn(2).size() == 0 && myGame.currentPlayer() == Token.RED
			&& myGame.get(0,0) == null){
			System.out.println("Yay 1!");		
		}
		
		// drop
		if (!myGame.drop(10) && myGame.drop(2) && myGame.getColumn(2).size() == 1 && 
			myGame.get(2,0) == Token.RED && myGame.currentPlayer() == Token.YELLOW ){
			System.out.println("Yay 2!");					
		}
		
		// drop, pop, column growing/shrinking, board display changed
		boolean ok = true;
		for (int i=0; i<5; i++){
			ok = ok && myGame.drop(2); 	//take turns to drop to column 2 for five times
		}
		//System.out.println("===Current Grid===");		
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		if (ok && myGame.getColumn(2).size() == 6 && myGame.sizeRow() == 7
			&& myGame.pop(2) && myGame.sizeRow() == 6 && myGame.get(2,1) == Token.RED){
			System.out.println("Yay 3!");							
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		
		// power drop
		if (!myGame.powerDrop(3,1) && myGame.powerDrop(3,0) && myGame.powerDrop(2,2)
			&& myGame.getColumn(2).size() == 6 && myGame.get(2,2) == Token.RED
			&& myGame.get(2,3) == Token.YELLOW && myGame.getColumn(3).size() == 1){
			System.out.println("Yay 4!");							
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		
		//power pop
		if (!myGame.powerPop(2,1) && myGame.powerPop(2,3) 
			&& myGame.getColumn(2).size() == 5 && myGame.get(2,3).getSymbol()=='R'){
			System.out.println("Yay 5!");									
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		//PowerConnectFourGUI.reportcurrentPlayer(myGame);
		// expected display:
		//|   || 0 || 1 || 2 || 3 || 4 || 5 || 6 |
		//| 5 || - || - || - || - || - || - || - |
		//| 4 || - || - || Y || - || - || - || - |
		//| 3 || - || - || R || - || - || - || - |
		//| 2 || - || - || R || - || - || - || - |
		//| 1 || - || - || R || - || - || - || - |
		//| 0 || - || - || Y || Y || - || - || - |
		//Player R's turn

		//counting
		if (myGame.countRow(3,0,Token.YELLOW) == 2 && myGame.countRow(3,0,Token.RED) == 0
			&& myGame.countCol(2,3,Token.RED) == 3 && myGame.drop(3) /*one more R*/
			&& myGame.countMajorDiagonal(3,1,Token.RED) == 2 /* (3,1) and (2,2) */
			&& myGame.countMinorDiagonal(2,0,Token.YELLOW) == 1){
			System.out.println("Yay 6!");												
		}
	}
}