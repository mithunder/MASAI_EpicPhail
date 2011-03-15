package org.epicp.gamestate;

public enum ActionType {
	MOVE("Move"), PUSH("Push"), PULL("Pull"), NOOP("NoOp");

	private final String text;

	private ActionType(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
