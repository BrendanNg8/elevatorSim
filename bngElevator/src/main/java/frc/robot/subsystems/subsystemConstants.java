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
public static enum WristPosition {
    Stowed(Degrees.of(0)),
    AlgaeGroundIntake(Degrees.of(-150)),
    L1Score(Degrees.of(-120)),
    L2Score(Degrees.of(-140)),
    L3Score(Degrees.of(-140)),
    L4Score(Degrees.of(-130)),
    ReefGrabAlgae(Degrees.of(135)),
    ReefSCAlgae(Degrees.of(-130)),
    GrabAlgaeIntermediate(Degrees.of(90)),
    AlgaeProcessor(Degrees.of(-175)),
    AlgaeTransit(Degrees.of(-270)),
    AlgaeNet(Degrees.of(-220)),
    Test(Degrees.of(-180));

    private final Angle angle;

    WristPosition(Angle angle) {
      this.angle = angle;
    }

    public Angle angle() {
      return angle;
    }
  } 
}




