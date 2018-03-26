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
	// Helper method for next9Grids
	//
	private int[] yxCell()
	{
		int[] yx = new int[2];
		
		// Search every member of values[][], looking for  zero.
		for (yx[0]=0; yx[0]<9; yx[0]++)
			for (yx[1]=0; yx[1]<9; yx[1]++)
				if (values[yx[0]][yx[1]] == 0)
					return yx;
		
		return null;
	}


	//
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Don’t change
	// “this” grid. Build 9 new grids.
	//
	public ArrayList<Grid> next9Grids()
	{
		// Finds the x,y of an empty cell.
		int[] yxNext = yxCell();
		int xOfNextEmptyCell = yxNext[0];
		int yOfNextEmptyCell = yxNext[1];

		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		for (int i=1; i<9; i++) {
			Grid add = new Grid(this);
			add.values[xOfNextEmptyCell][yOfNextEmptyCell]=i;
			grids.add(add);
		}

		return grids;
	}
	
	//
	// Helper method for isLegal()
	//
	private boolean repeats(int[] in) {
		boolean[] observed = new boolean[10];
		
		for (int i: in)
		{
			if (i == 0) {
				continue; // repeated 0s are good, so ignore 0s
			}
			else if (observed[i]) {
				return true;			// i has been seen before
			}
			else {
				observed[i] = true;		// first observation of i
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
		// Check all rows.
		for (int j=0; j<9; j++)
		{
			int[] ints = new int[9];
			for (int i=0; i<9; i++)
				ints[i] = values[j][i];
			if (repeats(ints))
				return false;
		}
		
		// Check all cols.
		for (int j=0; j<9; j++)
		{
			int[] ints = new int[9];
			for (int i=0; i<9; i++)
				ints[i] = values[i][j];
			if (repeats(ints))
				return false;
		}
		
		// Check all blocks. The two outer loops generate j = { 0, 3, 6 } and i = { 0, 3, 6 }.
		// These i,j pairs are the upper-left corners of each zone. The two inner loops compute
		// all 9 index pairs for the cells in the zone defined by i,j.
		for (int j=0; j<9; j+=3)
		{
			for (int i=0; i<9; i+=3)
			{
				int[] ints = new int[9];
				int n = 0;
				for (int jj=j; jj<j+3; jj++)
				{
					for (int ii=i; ii<i+3; ii++)
					{
						ints[n++] = values[jj][ii];
						if (repeats(ints))
							return false;
					}
				}
			}
		}
		
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
	
	public static void main(String[] args) {
		Grid g = TestGridSupplier.getPuzzle1();
		System.out.println("ORIGINAL GRID:\n" + g);
		ArrayList<Grid> nexts = g.next9Grids();
		for (Grid next: nexts) {
			System.out.println("-------\n" + next);
		}
	}
}
