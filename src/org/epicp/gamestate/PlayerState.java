package org.epicp.gamestate;

public class PlayerState {

	private final int playerId;
	private final ObjectColor objectColor;
	private int nodeId;

	public PlayerState(int playerId, ObjectColor objectColor) {
		this.playerId = playerId;
		this.objectColor = objectColor;
	}

	public int getPlayerId() {
		return playerId;
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
