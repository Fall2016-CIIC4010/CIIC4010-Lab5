package mineSweeperObjects;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class MineSweeperBoard {
	
	public MineSweeperObject[][] mineSweeperGameBoard;
	public int xBoardSize;
	public int yBoardSize;
	public Random randomGenerator;
	public int mineQuantity;
	public static final int MINEBOUNDS = 6;

	
	//Constructor for MineSweeperBoard
	public MineSweeperBoard(int xSize, int ySize){
		this.xBoardSize = xSize;
		this.yBoardSize = ySize;
		this.mineSweeperGameBoard = new MineSweeperObject[xSize][ySize];
		this.populateBoard();
	}
	//Method: Add an MineSweeperObject to the board
	public void addMineSweeperObjectToBoard(MineSweeperObject msObj, int xPosition, int yPosition){
		this.mineSweeperGameBoard[xPosition][yPosition] = msObj;
	}
	//Method: Check if there is a mine and if there is, print Game Over and return true
	public boolean verifyMine(int xPosition, int yPosition){
		if(this.mineSweeperGameBoard[xPosition][yPosition].getClass().toString().equals("Mine")){
			System.out.println("Game Over");
			return true;
		}
		return false;
	}
	//Method: Return Color of the mine that is on the specified position
	public Color giveCurrentMineColor(int xPosition, int yPosition){
		return this.mineSweeperGameBoard[xPosition][yPosition].getObjectColor(); 
	}
	//Method: Makes a new MineSweeperGameBoard
	public void clearBoard(){
		mineSweeperGameBoard = new MineSweeperObject[this.xBoardSize][this.yBoardSize];
	}
	//Method: Add the mines in a random fashion to the board
	public void populateBoard(){
		System.out.println("Commencing to Populate the Board");
		randomGenerator = new Random(); 
		this.mineQuantity = Math.max(randomGenerator.nextInt(MINEBOUNDS+1), 1);
		int randomXPos;
		int randomYPos;
		for(int i = 0; i<this.mineQuantity;i++){
			randomXPos = Math.max(randomGenerator.nextInt(this.xBoardSize-1), 1); // -1 so it doesn't go offbounds
			randomYPos = Math.max(randomGenerator.nextInt(this.yBoardSize-1), 1);
			System.out.println("Mine" + i + ": " + randomXPos +", " + randomYPos);
			addMineSweeperObjectToBoard(new Mine(), randomXPos, randomYPos);
			System.out.println(mineSweeperGameBoard[randomXPos][randomYPos]);
		}
	}
	public void restartBoard(){
		this.clearBoard();
		this.populateBoard();
	}
	
	
	
}
