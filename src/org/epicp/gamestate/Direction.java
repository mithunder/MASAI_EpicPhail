package org.epicp.gamestate;

public enum Direction {

	//Do not change order.
	NORTH, WEST, EAST, SOUTH;

	public boolean isOpposite(Direction otherDirection) {
		return ordinal() + otherDirection.ordinal() == 3;
	}

	public Direction getOpposite() {
		return values()[3-ordinal()];
	}
}
