package org.epicp.planner;

import java.util.List;

import org.epicp.gamestate.GameState;

public interface Planner {

	//TODO:

	public Plan getPlan(List<Goal> goals, GameState gameState);
}
