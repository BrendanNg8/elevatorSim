// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.WristSubsystem;
import yams.mechanisms.positional.Elevator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

import static edu.wpi.first.units.Units.Degrees;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final WristSubsystem m_WristSubsystem = new WristSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final GenericHID apacController = new GenericHID(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    m_exampleSubsystem.setDefaultCommand(m_exampleSubsystem.setHeight(Meters.of(0)));
    //Set defaykt cinnabd to force the arm to go to 0
    m_WristSubsystem.setDefaultCommand(m_WristSubsystem.setAngle(Degrees.of(0)));



  }

  

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));
    
    //Scheduling 'setHeight' when controlling A button is pressed -> Take method exampleSub that we made
    //Canceling on release
    m_driverController.a().whileTrue(m_exampleSubsystem.setHeight(Meters.of(0.5)));
    m_driverController.b().whileTrue(m_exampleSubsystem.setHeight(Meters.of(1)));
    //Scheduling 'set' when the Xbox controller's X button is pressed
    //Cancels on release
    m_driverController.x().whileTrue(m_exampleSubsystem.set(0.3));
    m_driverController.y().whileTrue(m_exampleSubsystem
    .set(-0.3));
    new JoystickButton(apacController, 2).onTrue(m_exampleSubsystem.setHeight(Meters.of(0.8)));
    new JoystickButton(apacController, 1).onTrue(m_exampleSubsystem.setHeight(Meters.of(0.1)));

    new JoystickButton(apacController, 4).onTrue(m_WristSubsystem.setAngle(Degrees.of(-5)));
    new JoystickButton(apacController, 3).onTrue(m_WristSubsystem.setAngle(Degrees.of(15)));
    new JoystickButton(apacController, 6).onTrue(m_WristSubsystem.set(0.3));
    new JoystickButton(apacController, 5).onTrue(m_WristSubsystem.set(-0.3));


    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
