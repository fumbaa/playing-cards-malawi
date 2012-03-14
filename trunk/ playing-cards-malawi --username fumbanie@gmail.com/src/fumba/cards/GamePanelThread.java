package fumba.cards;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * The <code>GamePanelThread</code> ...
 * 
 * <p>
 * <i>Copyright (c) 1998, 2011 Oracle. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 and Eclipse Distribution License v. 1.0 which accompanies
 * this distribution.</i>
 * </p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class GamePanelThread extends Thread {

	/**
	 * 
	 */
	private SurfaceHolder surfaceHolder;

	/**
	 * 
	 */
	private GamePanel gamePanel;

	/**
	 * 
	 */
	private boolean run = false;

	/**
	 * Constructor
	 * 
	 * @param surfaceHolder
	 * @param panel
	 */
	public GamePanelThread(GamePanel panel) {
		this.surfaceHolder = panel.getHolder();
		this.gamePanel = panel;
	}

	/**
	 * Set the run-status for the thread
	 * 
	 * @param run
	 */
	public void setRunning(boolean run) {
		this.run = run;
	}

	@Override
	/**
	 * 
	 */
	public void run() {
		Canvas canvas;
		while (this.run) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas(null);
				synchronized (this.surfaceHolder) {
					// _panel.updatePhysics();
					this.gamePanel.onDraw(canvas);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (canvas != null)
					this.surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	/**
	 * Gets the surfaceholder on which the game elements are drawn
	 * 
	 * @return SurfaceHolder
	 */
	public SurfaceHolder getSurfaceHolder() {
		return this.surfaceHolder;
	}

}
