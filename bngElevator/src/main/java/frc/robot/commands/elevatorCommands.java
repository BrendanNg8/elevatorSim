package frc.robot.commands;
import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.subsystemConstants;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.WristSubsystem;
//import frc.robot.subsystems.drive.Drive;
public class elevatorCommands {
    private void ElevatorCommands() {}
    /* 
    public Distance getHeight() {
        return elevator.getHeight();
    }
    public Command setHeight(Distance height) {return elevator.setHeight(height);}
    public boolean atHeight(Distance target) {return Math.abs(getHeight().in(Meters) - target.in(Meters)) <= subsystemConstants.ELEVATOR_TOLERANCE.in(Meters);}
    public Command waitUntilAtHeight(Distance target) {return edu.wpi.first.wpilibj2.command.Commands.waitUntil(() -> atHeight(target));}
    //Setting height to 4 levels below
    public static Command L4(ExampleSubsystem elevator) {
        return Commands.deadline(elevator.waitUntilAtHeight(subsystemConstants.ElevatorPosition.L4.distance()), 
        elevator.setHeight(subsystemConstants.ElevatorPosition.L4.distance())); 
    }
    public static Command L3(ExampleSubsystem elevator) {
        return Commands.deadline(elevator.waitUntilAtHeight(subsystemConstants.ElevatorPosition.L3.distance()), 
        elevator.setHeight(subsystemConstants.ElevatorPosition.L3.distance()));
    }
    public static Command L2(ExampleSubsystem elevator) {
        return Commands.deadline(elevator.waitUntilAtHeight(subsystemConstants.ElevatorPosition.L2.distance()),
        elevator.setHeight(subsystemConstants.ElevatorPosition.L2.distance()));
    }
    public static Command L1(ExampleSubsystem elevator) {
        return Commands.deadline(elevator.waitUntilAtHeight(subsystemConstants.ElevatorPosition.L1.distance()),
        elevator.setHeight(subsystemConstants.ElevatorPosition.L1.distance()));
    }
    public static Command Down(ExampleSubsystem elevator) {
        return elevator.setHeight(subsystemConstants.ElevatorPosition.Down.distance()); //get from constants
    }
    */
    ExampleSubsystem elevator;
    WristSubsystem wrist;
    public Command setHeight(Distance height) {return elevator.setHeight(height);}
    public static Command scoreReefLevel(
            ExampleSubsystem elevator, WristSubsystem wrist, int level){
            var elevatorTarget = 
            switch (level) {
                case 2 -> subsystemConstants.ElevatorPosition.L2.distance();
                case 3 -> subsystemConstants.ElevatorPosition.L3.distance();
                case 4 -> subsystemConstants.ElevatorPosition.L4.distance();
                default -> subsystemConstants.ElevatorPosition.Down.distance();
            };

            var wristTarget = 
                switch (level) {
                    case 2 -> subsystemConstants.ElevatorPosition.L2.distance();
                    case 3 -> subsystemConstants.ElevatorPosition.L3.distance();
                    case 4 -> subsystemConstants.ElevatorPosition.L4.distance();
                    default -> subsystemConstants.ElevatorPosition.Down.distance();
                };
            
            var stowElevator = subsystemConstants.ElevatorPosition.Down.distance();
            var stowWrist = subsystemConstants.WristPosition.Stowed.angle();
            
            Commands.sequence(Commands.deadline(elevator.waitUntilAtHeight(subsystemConstants.ElevatorPosition.L4.distance()), 
            elevator.setHeight(subsystemConstants.ElevatorPosition.L4.distance()),Commands.deadline(null, null)) //finish
            );

            
        } 

    
    
}
