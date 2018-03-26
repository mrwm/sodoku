package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;


	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	//
	// DON'T CHANGE THIS.
	// Copy ctor. Duplicates its source. You’ll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	//
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Don’t change
	// “this” grid. Build 9 new grids.
	//
	public ArrayList<Grid> next9Grids()
	{
		int xOfNextEmptyCell = -5; //-5 because of random negative
		int yOfNextEmptyCell = -5;

		// Finds the x,y of an empty cell.
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if (values[i][j]==0) {
					xOfNextEmptyCell = i;
					yOfNextEmptyCell = j;
					//break;
				}
			}
			//if (i==xOfNextEmptyCell) {
			//	break;
			//}
		}

		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		int added = 1;
		for (int i=0; i<9; i++) {
			Grid add = new Grid(this);
			add.values[xOfNextEmptyCell][yOfNextEmptyCell]=added;
			grids.add(add);
			added++;
		}

		return grids;
	}
	
	//
	// Helper method for isLegal()
	//
	private boolean repeats(int[] in) {
		Arrays.sort(in);
		for (int i=0; i<in.length; i++) {
			for (int j = i + 1; j < in.length-1; j++) {
				if (in[i] ==0) {
					break;
				}
				if (in[i] == in[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
		// Check every row. If you find an illegal row, return false.
		for (int i=0; i<9; i++) {
			int[] tmp = new int[9];
			for (int j=0; j<9; j++) {
				tmp[j] = values[i][j];
			}
			if (repeats(tmp) == false) {
				//System.out.println("Row"); //debug
				return false;
			}
		}

		// Check every column. If you find an illegal column, return false.
		for (int i=0; i<9; i++) {
			int[] temp = new int[9];
			for (int j=0; j<9; j++) {
				temp[j] = values[j][i];
			}
			if (repeats(temp) == false) {
				//System.out.println("Col"); //debug
				return false;
			}
		}

		// Check every block. If you find an illegal block, return false.
		/*
		for (int i=0; i<9; i+=3) {
			int[] tmp = new int[9];
			for (int j=0; j<9; j+=3) {
				//Cells
				int increment = 0;
				for (int k=i; k<(i+3); k++) {
					for (int l=j; l<(j+3); l++) {
						tmp[increment] = values[i][j];
						increment++;
					}
				}
				if (repeats(tmp) == false) {
					//System.out.println("Block"); //debug
					return false;
				}
			}
		}
		*/
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int[] tmp = new int[9];
				int counter = 0;
				for (int k = j * 3; k < j * 3 + 3; k++) {
					for (int l = i * 3; l < i * 3 + 3; l++) {
						tmp[counter] = values[k][l];
						counter++;
					}
				}
				if (repeats(tmp) == true)
					return false;
			}
		}

		// All rows/cols/blocks are legal.
		return true;
	}

	
	//
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				int val = values[i][j];
				if (val < 1 || val > 9) {
					return false;
				}
			}
		}
		return true;
	}
	

	//
	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
		Grid tmp = (Grid)x;
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if (tmp.values[i][j] != this.values[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
}
