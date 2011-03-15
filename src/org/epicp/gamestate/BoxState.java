package org.epicp.gamestate;

public class BoxState {

	private final char boxId;
	private final ObjectColor objectColor;
	private int nodeId;

	public BoxState(char boxId, ObjectColor objectColor) {
		this.boxId = boxId;
		this.objectColor = objectColor;
	}

	public char getBoxId() {
		return boxId;
	}

	public ObjectColor getObjectColor() {
		return objectColor;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
}
