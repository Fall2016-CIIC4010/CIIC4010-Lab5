import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter 
{
	

	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}

			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:
		
			//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton())
		{
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						
								
								if(myPanel.flagged[myPanel.mouseDownGridX][myPanel.mouseDownGridY]==false)
								{
									if(myPanel.mines[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== 1)//if it hits a bomb reveal everything
									{
										for (int i = 0; i < myPanel.getTotalCol(); i++) 
										{   
											for (int j = 0; j < myPanel.getTotalRows(); j++) 
											{
												
												if(myPanel.mines[i][j]== 1)//paints bomb cells black
													myPanel.colorArray[i][j] = Color.BLACK;
												myPanel.revealed[i][j]=true;
															
											}
										}
										
										
										
										
									}else
										{
											if(myPanel.revealed[myPanel.mouseDownGridX][myPanel.mouseDownGridY]==true){
											}else
											{
												myPanel.clearZeros(gridX, gridY);
											}
										}
								}
							
						}
					}
					myPanel.repaint();
					break;
				}
			case 3:	//when right click outside grid change white cells color
				Component cr = e.getComponent();
				while (!(cr instanceof JFrame)) {
					cr = cr.getParent();
					if (cr == null) {
						return;
					}
				}

				JFrame myRightClickFrame = (JFrame) cr;
				MyPanel myRightClickPanel = (MyPanel) myRightClickFrame.getContentPane().getComponent(0);
				Insets myRightClickInsets = myRightClickFrame.getInsets();
				int xr1 = myRightClickInsets.left;
				int yr1 = myRightClickInsets.top;
				e.translatePoint(-xr1, -yr1);
				int xr2 = e.getX();
				int yr2 = e.getY();
				myRightClickPanel.mouseDownGridX = myRightClickPanel.getGridX(xr2, yr2);
				myRightClickPanel.mouseDownGridY = myRightClickPanel.getGridY(xr2, yr2);
				myRightClickPanel.x = xr2;
				myRightClickPanel.y = yr2;
			
				
				int rightClickGridX = myRightClickPanel.getGridX(xr2, yr2);
				int rightClickGridY = myRightClickPanel.getGridY(xr2, yr2);
				
				
				if ((myRightClickPanel.mouseDownGridX == -1) || (myRightClickPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((rightClickGridX == -1) || (rightClickGridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myRightClickPanel.mouseDownGridX != rightClickGridX) || (myRightClickPanel.mouseDownGridY != rightClickGridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							//Released the mouse button on the same cell where it was pressed
							
									//On the grid other than on the left column and on the top row: set random color
									//Color newColor = null;*/
								
								
									if(myRightClickPanel.revealed[myRightClickPanel.mouseDownGridX][myRightClickPanel.mouseDownGridY]!= true)//does not let to marked revealed spaces
									{
										
											if(myRightClickPanel.flagged[myRightClickPanel.mouseDownGridX][myRightClickPanel.mouseDownGridY]== false)//paints the cell if it doesn't have a bomb
											{
												myRightClickPanel.colorArray[myRightClickPanel.mouseDownGridX][myRightClickPanel.mouseDownGridY] = Color.RED;
												myRightClickPanel.flagged[myRightClickPanel.mouseDownGridX][myRightClickPanel.mouseDownGridY] = true;
												
												
											}else
												{
												myRightClickPanel.flagged[myRightClickPanel.mouseDownGridX][myRightClickPanel.mouseDownGridY] = false;
												myRightClickPanel.colorArray[myRightClickPanel.mouseDownGridX][myRightClickPanel.mouseDownGridY] = Color.WHITE;
											}
									}
								
							}
						}
						myRightClickPanel.repaint();
						break;
					}
				//Right mouse button
				//Do nothing
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
			}
		}
	}