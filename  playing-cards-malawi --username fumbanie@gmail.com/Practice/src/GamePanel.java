import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

class GamePanel extends JPanel {

	int numCards = 1;

	int p1 = 1;
	int p2 = 2;
	int p3 = 3;
	int p4 = 4;

	int width = 40;
	int height = 60;

	ArrayList<Integer> players = new ArrayList<Integer>();
	private int screen_width = 600;
	private int screen_height = 400;

	Point bottomZone = new Point(0, screen_height - height/2);
	Point leftZone = new Point( 0, 150 );
	
	Point topZone = new Point(0, height);

	Point rightZone = new Point( screen_width - width, height/2);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void initPlayers() {
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
	}

	public Dimension getPreferredSize() {
		return new Dimension(screen_width, screen_height);
	}

	protected void paintComponent(Graphics g) {
		initPlayers();
		g.setColor(Color.red);

		// Place middle card
		// TODO Correct positions...
		g.fillRect( (screen_width / 2 )+ width/2,  (screen_height / 2) - height/2, width, height);
		drawCards(g);
	}

	protected void drawCards(Graphics g) {

		for (Integer player : players) {
			setCardColors(g, player);

			for (int i = 0; i < numCards; i++) {

				Point position = this.determineCardPosition(player, i);
				int xCoord = position.x  + width/2;
				int yCoord = position.y - height/2;
				// Calculate Appropriate x and y values here......
				
				System.err.println( "> ( "+ xCoord + " , " + yCoord + " ) " );

				g.fillRect(xCoord, yCoord, width, height);
				g.drawString(" " + player, xCoord, yCoord);
			}
		}
	}

	/**
	 * This method determines the new position for a card
	 * 
	 * @return Point x and y coordinates representing the new position
	 */
	private Point determineCardPosition(Integer player, int cardNum) {
		Point result = new Point();
		// calculate values based on the player who will be holding them
		if (player == 1) {
			// bottom zone
			result.x = bottomZone.x;
			result.y = bottomZone.y;
		}

		else if (player == 2) {
			// left zone
			result.x = leftZone.x;
			result.y = leftZone.y;
		}

		else if (player == 3) {
			// top zone
			result.x = topZone.x;
			result.y = topZone.y;
		}

		else {
			// right zone
			result.x = rightZone.x;
			result.y = rightZone.y;
		}

		return result;
	}

	private void setCardColors(Graphics g, Integer player) {
		if (player == 1)
			g.setColor(Color.green);
		else if (player == 2)
			g.setColor(Color.blue);
		else if (player == 3)
			g.setColor(Color.yellow);
		else if (player == 4)
			g.setColor(Color.magenta);
	}

}