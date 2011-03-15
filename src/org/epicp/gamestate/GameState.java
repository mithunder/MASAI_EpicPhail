package org.epicp.gamestate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public interface GameState {

	//Change game state.

	//TODO: Game state.
	public void update(List<Action> action);

	//Map IO.

	public void readinMap(InputStream inputStream) throws IOException;

	//Remember to use standard error, not standard out.
	public void writeoutMap(OutputStream outputStream) throws IOException;

	//Node functions.

	public int getX(int nodeId);

	public int getY(int nodeId);

	public boolean isWall(int nodeId);

	public boolean isBlocked(int nodeId);

	//General functions.

	public List<PlayerState> getPlayers();

	public List<GoalState> getGoals();

	public List<BoxState> getBoxes();
}
