package org.epicp.agent;

import org.epicp.client.Client;
import org.epicp.gamestate.PlayerState;

public class SingleAgent implements Agent {

	private final PlayerState playerState;
	private final Client client;

	public SingleAgent(PlayerState playerState, Client client) {

		this.playerState = playerState;
		this.client = client;
	}
}
