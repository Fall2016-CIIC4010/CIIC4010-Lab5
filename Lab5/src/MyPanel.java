import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;   
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	
	Random random = new Random();
	int mines[][] = new int [TOTAL_COLUMNS][TOTAL_ROWS];
	int[][] neighbores = new int [TOTAL_COLUMNS][TOTAL_ROWS];
	boolean [][] revealed = new boolean [TOTAL_COLUMNS][TOTAL_ROWS];
	boolean [][] flagged = new boolean [TOTAL_COLUMNS][TOTAL_ROWS];
	String[][] neighboresText = new  String [TOTAL_COLUMNS][TOTAL_ROWS];
	int MINE = 1, notClicked = 0, noNeighbores = 0;
	
	
	
	public int getTotalCol() 
	{
		return TOTAL_COLUMNS;
	}
	
	public int getTotalRows()
	{
		return TOTAL_ROWS;
	}
	
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		for (int x = 0; x < TOTAL_COLUMNS; x++) 
		{   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) 
			{
				if(random.nextInt(100)<20)//asigns mines to cell 20% chance
					{
						mines [x][y]=MINE;
						
					}else
						{
							mines[x][y]=notClicked;
						}
				flagged[x][y]=false;//sets all cell unflagged
				revealed[x][y]=false;//sets all the cells to not revealed
				colorArray[x][y] = Color.WHITE;
			}
		}
		for (int x=0;x<TOTAL_COLUMNS;x++)//Array of neighbore mines
		{
			for (int y=0;y<TOTAL_ROWS;y++)
			{
				if(mines[x][y]!=MINE)
				{
					int neighborCount=0;
					if(x>0 && y>0 && mines[x-1][y-1]==MINE)//top left 
						neighborCount++;
					if(x>0 && y<mines.length-1 && mines[x-1][y+1]==MINE)//down left 
						neighborCount++;
					if(x>0 && mines[x-1][y]==MINE)//left
						neighborCount++;
					if(y>0 && mines[x][y-1]==MINE)//top
						neighborCount++;
					if(y<mines.length-1 && mines[x][y+1]==MINE)//bottom
						neighborCount++;
					if(x<mines.length-1&& y< mines.length-1&&mines[x+1][y+1]==MINE)//down right
						neighborCount++;
					if(y>0&&x<mines.length-1&& y< mines.length&&mines[x+1][y-1]==MINE)//top right
						neighborCount++;
					if(x<mines.length-1 && mines[x+1][y]==MINE)//Right
						neighborCount++;
					
					neighbores[x][y]=neighborCount;
					if(neighborCount==0)
						neighboresText[x][y] = "";
					else
						neighboresText[x][y] = String.valueOf(neighbores[x][y]);
				}
				else {
					neighboresText[x][y] = "";
				}
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;
		
		
		
			
		
		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS ; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS )));
		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
					g.setColor(Color.WHITE);
					g.drawString(neighboresText[x][y], x *(INNER_CELL_SIZE+1)+38, (y*30)+44);
					if(flagged[x][y]==true)
					{
						g.setColor(Color.RED);
						g.drawString(neighboresText[x][y], x *(INNER_CELL_SIZE+1)+38, (y*30)+44);
					}
				
			}
		}
		
	}
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x >= 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x >= 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
	public void clearZeros(int xpos, int ypos)
	{
		if(neighbores[xpos][ypos]!=noNeighbores)
		{
			revealed[xpos][ypos]= true;
			colorArray[xpos][ypos] = Color.GRAY;
			return ;
		}else
			{
				revealed[xpos][ypos]= true;
				colorArray[xpos][ypos] = Color.GRAY;
				if(xpos>0 && ypos>0 && revealed[xpos-1][ypos-1]!=true )//top left 
				{
					revealed[xpos-1][ypos-1]= true;
					colorArray[xpos-1][ypos-1] = Color.GRAY;
					clearZeros(xpos-1,ypos-1);
				}
						
				if(xpos>0 && ypos<mines.length-1 && revealed[xpos-1][ypos+1]!=true  )//down left 
				{
					revealed[xpos-1][ypos+1]= true;
					colorArray[xpos-1][ypos+1] = Color.GRAY;
					clearZeros(xpos-1,ypos+1);
				}
						
				if(xpos>0 && revealed[xpos-1][ypos]!=true)//left
				{
					revealed[xpos-1][ypos]= true;
					colorArray[xpos-1][ypos] = Color.GRAY;
					clearZeros(xpos-1,ypos);
				}
						
				if(ypos>0 && revealed[xpos][ypos-1]!=true)//top
				{
					revealed[xpos][ypos-1]= true;
					colorArray[xpos][ypos-1] = Color.GRAY;
					clearZeros(xpos,ypos-1);
				}
						
				if(ypos<mines.length-1 &&  revealed[xpos][ypos+1]!=true)//bottom
				{
					revealed[xpos][ypos+1]= true;
					colorArray[xpos][ypos+1] = Color.GRAY;
					clearZeros(xpos,ypos+1);
				}
						
				if(xpos<mines.length-1&& ypos< mines.length-1 && revealed[xpos+1][ypos+1]!=true)//down right
				{
					revealed[xpos+1][ypos+1]= true;
					colorArray[xpos+1][ypos+1] = Color.GRAY;
					clearZeros(xpos+1,ypos+1);
				}
						
				if(ypos>0 && xpos<mines.length-1 && ypos< mines.length && revealed[xpos+1][ypos-1]!=true)//top right
				{
					revealed[xpos+1][ypos-1]= true;
					colorArray[xpos+1][ypos-1] = Color.GRAY;
					clearZeros(xpos+1,ypos-1);
				}
						
				if(xpos<mines.length-1 && revealed[xpos+1][ypos]!=true)//Right
				{
					revealed[xpos+1][ypos]= true;
					colorArray[xpos+1][ypos] = Color.GRAY;
					clearZeros(xpos+1,ypos);
				}
						
				
			}
		
	}
	
}