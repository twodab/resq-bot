package com.qualcomm.ftcrobotcontroller.opmodes.StatesAuto;


/**
 * Created by Aman on 1/28/2016.
 *
 * Game Plan:
 *      move forward a bit,
 *      turn towards beacon,
 *      move straight until left/right ultrasonic (only 1 of them is activated) detects wall
 *      turn towards beacon a certain amount of degrees
 *      move forward, drop climbers
 *      move back a bit
 */
public class ResQ_Solo extends Autonomous_Library {
    protected static final int RED_ANGLE_FIRST = 220;

    protected CurrentState currentState = CurrentState.STARTING;

    public enum CurrentState{
        STARTING, FIRSTTURN, APPROACHBEACON, GETPARKEDCORRECTLY, DOA360FAM, FINALAPPROACH, PARKEDFORDROP, SECONDTURN, FINALPARK, DONE
    }

    @Override
    public void init() {
        initializeMapping();
        startIMU();
    }

    public void loop() { //Autonomous Logic
        double yaw = getYaw();
        telemetry.addData("yaw", yaw);
        telemetry.addData("distance", getDistance());
        switch(currentState) {
            //
            case STARTING:
                starting();
                break;
            case FIRSTTURN:
                telemetry.addData("Debug","Turning w/ driveTurnDegrees");
                firstTurn();
                break;
            case APPROACHBEACON:
                telemetry.addData("Debug", "Approaching beacon...should be moving");
                approachBeacon();
                break;
            default:
                telemetry.addData("Debug", "Done! :(");
                stopDrive();
        }
    }

    protected void starting () {
        srvoLeftDeflector.setPosition(0.2);
        srvoRightDeflector.setPosition(0.8);
        currentState = CurrentState.FIRSTTURN;
    }

    protected void firstTurn() {
        boolean result = driveTurnDegrees(RED_ANGLE_FIRST, 7);
        if(result) {
            currentState = CurrentState.APPROACHBEACON;
        }
    }

    protected void approachBeacon(){ // approaches the beacon until the ultrasonic detects the wall
        drive(1.0f, 1.0f);
        if(getDistance() == 37.5) {
            stopDrive();
            currentState = CurrentState.DONE;
        }
    }

}
