package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
public class ScoreCommands {
    public static Command scoreReefLevel (
        ExampleSubsystem elevator, WristSubsystem wrist, int level) {

            var elevatorTarget = 
                switch (level) {
                    case 2 -> subsystemConstants.ElevatorPosition.L2.distance();
                    case 3 -> subsystemConstants.ElevatorPosition.L3.distance();
                    case 4 -> subsystemConstants.ElevatorPosition.L4.distance();
                    default -> subsystemConstants.ElevatorPosition.Down.distance();
                };
            
            var wristTarget =
                switch (level) {
                    case 2 -> subsystemConstants.WristPosition.L2Score.angle();
                    case 3 -> subsystemConstants.WristPosition.L3Score.angle();
                    case 4 -> subsystemConstants.WristPosition.L4Score.angle();
                    default -> subsystemConstants.WristPosition.Stowed.angle();
                };
            
                var stowElevator = subsystemConstants.ElevatorPosition.Down.distance();
                var stowWrist = subsystemConstants.WristPosition.Stowed.angle();

                Command reachTargets = 
                    Commands.parallel(
                        Commands.deadline(elevator.WaitHeight(elevatorTarget), elevator.setHeight(elevatorTarget)), //Go to the level height
                        Commands.deadline(wrist.WaitUntilAtAngle(wristTarget), wrist.setAngle(wristTarget)) //Set the level angle
                    );
                    

                Command postScore = 
                    Commands.sequence(
                        Commands.waitSeconds(0.5),
                        Commands.parallel(elevator.setHeight(stowElevator),
                        wrist.setAngle(stowWrist))
                    );

                return Commands.sequence(reachTargets, postScore).withName("Score L "+level);
                    
        }
}
