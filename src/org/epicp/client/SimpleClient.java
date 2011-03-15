package org.epicp.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.epicp.agent.Agent;
import org.epicp.agent.SingleAgent;
import org.epicp.gamestate.GameState;
import org.epicp.gamestate.PlayerState;
import org.epicp.gamestate.StandardGameState;

public class SimpleClient implements Client {

	private final GameState gameState = new StandardGameState();
	private final List<Agent> agents = new ArrayList<Agent>();

	private SimpleClient() throws IOException {
		gameState.readinMap(System.in);
	}

	public static Client createSimpleClient() throws IOException{

		final SimpleClient client = new SimpleClient();

		for (PlayerState playerState : client.gameState.getPlayers()) {
			client.agents.add(new SingleAgent(playerState, client));
		}

		client.gameState.writeoutMap(System.err);

		return client;
	}

	@Override
	public boolean update() {
		throw new UnsupportedOperationException();
	}
}
