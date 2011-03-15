package org.epicp.gamestate;

public class Action {

	private final ActionType actionType;
	private final Direction direction1, direction2;
	private final int playerId;

	public Action(ActionType actionType, Direction direction1,
			Direction direction2, int playerId) {
		this.actionType = actionType;
		this.direction1 = direction1;
		this.direction2 = direction2;
		this.playerId = playerId;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public Direction getDirection1() {
		return direction1;
	}

	public Direction getDirection2() {
		return direction2;
	}

	public int getPlayerId() {
		return playerId;
	}

	@Override
	public String toString() {
		switch (actionType) {
		case MOVE : {
			return actionType + "(" + direction1 + ")";
		}
		case PULL :
		case PUSH : {
			return actionType + "(" + direction1 + "," + direction2 + ")";
		}
		case NOOP : {
			return actionType.toString();
		}
		}
		return null;
	}
}
