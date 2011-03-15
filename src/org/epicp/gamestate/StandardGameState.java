package org.epicp.gamestate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardGameState implements GameState {

	private Node[][] nodes;
	private int nodesWidth, nodesHeight;
	private Node[] nodeIds;
	private List<Node> goalNodes;
	private List<BoxState> boxes;
	private List<PlayerState> agents;

	private static class Node {

		private final int nodeId;
		private final int x, y;
		private boolean isWall;
		private GoalState goal;
		private BoxState box;
		private PlayerState agent;

		//TODO: Handle goal, box etc. in constructor.
		public Node(int nodeId, int x, int y) {
			this.nodeId = nodeId;
			this.x = x;
			this.y =y;
		}

		public int getNodeId() {
			return nodeId;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setWall(boolean b) {
			isWall = b;
		}

		public void setGoal(GoalState goal) {
			this.goal = goal;
		}

		public void setBox(BoxState box) {
			this.box = box;
		}

		public void setAgent(PlayerState agent) {
			this.agent = agent;
		}

		public char getChar() {
			if (isWall) {
				return '+';
			}
			else if (box != null) {
				return box.getBoxId();
			}
			else if (goal != null) {
				return goal.getId();
			}
			else if (agent != null) {
				return (char) ('0' + agent.getPlayerId());
			}
			return ' ';
			//TODO: Finish this.
		}

		public BoxState getBox() {
			return box;
		}
	}

	@Override
	public List<BoxState> getBoxes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GoalState> getGoals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerState> getPlayers() {
		return agents;
	}

	@Override
	public int getX(int nodeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY(int nodeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBlocked(int nodeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWall(int nodeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readinMap(InputStream inputStream) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

		final Map <Character, ObjectColor> colors = new HashMap <Character, ObjectColor>();
		String line;
		{
			ObjectColor color;

			// Read lines specifying colors
			//TODO: Make the pattern object once.
			while ( ( line = in.readLine() ).matches( "^[a-z]+:\\s*[0-9A-Z](,\\s*[0-9A-Z])*\\s*$" ) ) {
				line = line.replaceAll( "\\s", "" );
				color = ObjectColor.getColor(line.split( ":" )[0]);

				for ( String id : line.split( ":" )[1].split( "," ) ) {
					colors.put( id.charAt( 0 ), color );
				}
			}
		}

		List<String> lines = new ArrayList<String>();
		nodesWidth = 0;
		nodesHeight = 0;
		{
			while (!line.equals("")) {
				lines.add(line);
				if (nodesWidth < line.length()) {
					nodesWidth = line.length();
				}
				line = in.readLine();
			}
			nodesHeight = lines.size();
		}

		//Create node array.
		nodes = new Node[nodesWidth][nodesHeight];
		nodeIds = new Node[nodesWidth*nodesHeight];
		goalNodes = new ArrayList<Node>();
		boxes = new ArrayList<BoxState>();
		agents = new ArrayList<PlayerState>();

		// Read lines specifying level layout
		int nodeId = 0;
		int y = 0;
		for (String levelLine : lines) {
			for ( int x = 0; x < levelLine.length(); x++ ) {
				char id = levelLine.charAt( x );

				final Node node = new Node(nodeId, x, y);

				nodes[x][y] = node;
				nodeIds[nodeId] = node;

				if ('0' <= id && id <= '9') {
					//TODO: Add agents.
					final PlayerState player = new PlayerState((id - '0'), colors.get(id));
//					agents.add( new Agent( id, colors.get( id ) ) );
					node.setAgent(player);
					agents.add(player);
					player.setNodeId(node.getNodeId());
				}
				//Handle goals.
				else if ('a' <= id && id <= 'z') {
					final GoalState goal = new GoalState(id);
					node.setGoal(goal);
					goalNodes.add(node);
				}
				//Handle boxes.
				else if ('A' <= id && id <= 'Z') {
					final BoxState box = new BoxState(id, colors.get(id));
					node.setBox(box);
					box.setNodeId(node.getNodeId());
					boxes.add(box);
				}
				else {
					switch (id) {
					case '+' : {
						node.setWall(true);
						break;
					}
					case ' ' : {
						break;
					}
					//TODO: Handle 'unknown' objects.
					}
				}

				nodeId++;
			}
			y++;
		}

		Collections.sort(agents, new Comparator<PlayerState>() {
			@Override
			public int compare(PlayerState o1, PlayerState o2) {
				if (o1.getPlayerId() < o2.getPlayerId()) {
					return -1;
				}
				else if (o1.getPlayerId() > o2.getPlayerId()) {
					return 1;
				}
				return 0;
			}
		});

	}

//
//	private List<Node> goalNodes;
//	private List<Node> boxNodes;
//	//TODO: Sort the agents.
//	private List<Node> agentNodes;
//	private List<PlayerState> agents;

	@Override
	public void update(List<Action> actions) {
		for (Action action : actions) {
			switch (action.getActionType()) {
			case MOVE : {
				moveAgent(action);

				break;
			}
			case PUSH : {
				moveAgent(action);
				moveBox(
						getNodeDirection(getPlayerNode(action.getPlayerId()), action.getDirection1()),
						action.getDirection2()
				);
				break;
			}
			case PULL : {
				moveAgent(action);
				moveBox(
						getNodeDirection(getPlayerNode(action.getPlayerId()), action.getDirection2()),
						action.getDirection2().getOpposite()
				);
				break;
			}
			case NOOP : {
				//Do nothing.
				break;
			}
			}
		}
	}

	private Node getPlayerNode(int playerId) {
		return nodeIds[agents.get(playerId).getNodeId()];
	}

	private void moveAgent(Action action) {
		PlayerState agent = agents.get(action.getPlayerId());
		Node oldNode = nodeIds[agent.getNodeId()];
		Node newNode = getNodeDirection(oldNode, action.getDirection1());

		oldNode.setAgent(null);
		newNode.setAgent(agent);
		agent.setNodeId(newNode.getNodeId());
	}

	private void moveBox(Node node, Direction direction) {
		Node oldNode = nodeIds[node.getNodeId()];
		Node newNode = getNodeDirection(oldNode, direction);

		final BoxState box = oldNode.getBox();
		newNode.setBox(box);
		oldNode.setBox(null);
		box.setNodeId(newNode.getNodeId());
	}

	private Node getNodeDirection(Node node, Direction direction) {
		switch (direction) {
		case EAST: {
			return nodes[node.getX()+1][node.getY()];
		}
		case WEST: {
			return nodes[node.getX()-1][node.getY()];
		}
		case NORTH: {
			return nodes[node.getX()][node.getY()-1];
		}
		case SOUTH: {
			return nodes[node.getX()][node.getY()+1];
		}
		}

		return null;
	}

	@Override
	public void writeoutMap(OutputStream outputStream) throws IOException {
		PrintStream out = new PrintStream(outputStream);

		out.println();
		for (int y = 0; y < nodesHeight; y++) {
			String line = "";
			for (int x = 0; x < nodesWidth; x++) {

				Node node = nodes[x][y];
				if (node != null) {
					line += node.getChar();
				}
			}
			out.println(line);
		}
		out.flush();

		//TODO: Ensure proper closing.
		out.close();
	}
}
