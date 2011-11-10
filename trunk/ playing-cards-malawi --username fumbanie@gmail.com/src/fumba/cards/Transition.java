package fumba.cards;

import java.util.Timer;
import java.util.TimerTask;


class Transition extends TimerTask {

	Controller controller;
	Timer timer;

	Transition(Controller controller, Timer timer){
		this.controller = controller;
		this.timer = timer;
	}


	public void run() {
		controller.nextPlayer();
		timer.cancel();
	}
}