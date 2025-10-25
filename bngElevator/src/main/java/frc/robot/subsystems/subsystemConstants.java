package frc.robot.subsystems;
import edu.wpi.first.units.measure.*;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;

import edu.wpi.first.units.Units.*;

public class subsystemConstants {
    public static final String CANBUS = "rio";
    public static final int ElevatorLEADER_ID = 15;
    public static final int ElevatorFOLLOWER_ID = 14;
    public static final int Wrist_ID = 18;

    public static final int CoralIntake_ID = 21;
    public static final int EndEffector_ID = 20;

    //Tolerances for elevator end wrist position
    public static final Distance ELEVATOR_TOLERANCE = Inches.of(0.5);
    public static final Angle WRIST_TOLERANCE = Degrees.of(2.0);


//Elevator set positions (levels)
public static enum ElevatorPosition {
    
    Down(Inches.of(8)),
    Intake(Inches.of(16)),
    L1(Inches.of(12)),
    L2(Inches.of(28)),
    L3(Inches.of(40)),
    L4(Inches.of(74)),
    L2GrabAlgae(Inches.of(32)),
    L2SCAlgae(Inches.of(20)),
    L3GrabAlgae(Inches.of(35));

    private final Distance distance;
    ElevatorPosition(Distance distance) {
        this.distance = distance;
    }
    public Distance distance() {
        return distance;
    }


}
}




