package org.epicp.gamestate;

import java.util.List;

public interface SensorActionHandler {

	public List<Boolean> interpretSensorinput(String sensorInput);

	public String createJoinActions(List<Action> actions);

}
