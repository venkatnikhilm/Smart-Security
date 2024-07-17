package sd.asu.suprad.smartsecure;

import java.io.Serializable;

public class Room implements Serializable {
    private String roomName;
    private boolean isArmed, motionSensor, windowSensor, doorSensor;
    private int temperature;

    public Room(String roomName, boolean isSecure, boolean motionSensor, boolean windowSensor, boolean doorSensor, int temperature) {
        this.roomName = roomName;
        this.isArmed = isSecure;
        this.motionSensor = motionSensor;
        this.doorSensor = doorSensor;
        this.windowSensor = windowSensor;
        this.temperature = temperature;
    }


    public boolean isArmed() {
        return isArmed;
    }

    public void setArmed(boolean armed) {
        isArmed = armed;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isMotionSensor() {
        return motionSensor;
    }

    public void setMotionSensor(boolean motionSensor) {
        this.motionSensor = motionSensor;
    }

    public boolean isWindowSensor() {
        return windowSensor;
    }

    public void setWindowSensor(boolean windowSensor) {
        this.windowSensor = windowSensor;
    }

    public boolean isDoorSensor() {
        return doorSensor;
    }

    public void setDoorSensor(boolean doorSensor) {
        this.doorSensor = doorSensor;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
