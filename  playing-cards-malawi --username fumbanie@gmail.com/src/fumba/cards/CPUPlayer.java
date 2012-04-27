package fumba.cards;

public class CPUPlayer extends Player {

	public CPUPlayer(String playerName, GamePanel gamePanel) {
		this.setName(playerName);
		this.gamePanel = gamePanel;
		this.currentMove = new Move(); // defaults to false, false for validity
		// and continuity
	}

	public Boolean isHuman() {
		return false;
	}

	/**
	 * Artificial Intelligence - calculate best move
	 */
	public Move calculateMove() {
		Move move = new Move();
		for (Card card : this.cards) {
			Move checkedMove = Rules.checkMove(card);
			// TODO CPU Plays first valid move available
			if (checkedMove.isValid()) {
				return checkedMove;
			}
		}
		return move;
	}

	public void makeMove(Move move) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	@Override
	public String getLocation() {
		return this.location;
	}
	
}
