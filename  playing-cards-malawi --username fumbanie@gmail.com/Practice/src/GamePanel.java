import java.awt.*;
import javax.swing.*;

class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

	protected void paintComponent(Graphics g) {

		g.setColor(Color.red);
		g.fillRect(10, 10, 40, 60);

	}

}