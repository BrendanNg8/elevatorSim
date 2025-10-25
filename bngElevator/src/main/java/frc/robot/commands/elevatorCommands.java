package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.subsystemConstants;
import frc.robot.subsystems.ExampleSubsystem;
public class elevatorCommands {
    private void ElevatorCommands() {}

    public static Command Down(ExampleSubsystem elevator) {
        return elevator.setHeight(subsystemConstants.ElevatorPosition.Down.distance()); //get from constants
    }

    
}
