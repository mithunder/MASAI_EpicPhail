package org.epicp.gamestate;

public enum ObjectColor {

	BLUE, RED, GREEN, CYAN, MAGENTA, ORANGE, PINK, YELLOW;

	public static ObjectColor getColor(String name) {
		for (ObjectColor color : ObjectColor.values()) {
			if (color.name().toLowerCase().equals(name.toLowerCase())) {
				return color;
			}
		}
		return null;
	}

}
