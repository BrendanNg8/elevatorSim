// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Millimeters;
import static edu.wpi.first.units.Units.Seconds;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.SmartMechanism;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.mechanisms.config.ElevatorConfig;
import yams.mechanisms.config.MechanismPositionConfig;
import yams.mechanisms.positional.Elevator;

import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.Pounds;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;
import frc.robot.subsystems.subsystemConstants;
import yams.motorcontrollers.remote.TalonFXWrapper;
import com.ctre.phoenix6.hardware.TalonFX;


public class ExampleSubsystem extends SubsystemBase {
  private final TalonFX elevatorMotor = new TalonFX(subsystemConstants.ElevatorLEADER_ID, subsystemConstants.CANBUS);
  private final TalonFX elevatorFollower = new TalonFX(subsystemConstants.ElevatorFOLLOWER_ID, subsystemConstants.CANBUS);

  private SmartMotorControllerConfig smcfonfig = new SmartMotorControllerConfig(this)
  .withControlMode(ControlMode.CLOSED_LOOP)
  .withMechanismCircumference(Meters.of(Millimeters.of(5).in(Meters) * 36)) //Calculating how many times the poly with the track rotates (thing moves elevator)
  //Feedback Constants (PID)
  .withClosedLoopController(3.5, 0, 0, MetersPerSecond.of(3), MetersPerSecondPerSecond.of(3))
  .withSimClosedLoopController(3, 0, 0, MetersPerSecond.of(3), MetersPerSecondPerSecond.of(3)) //3.25 (kP)
  //Feedforward Constants
  .withFeedforward(new ElevatorFeedforward(0, 0.3, 0.85, 0.002)) 
  .withSimFeedforward(new ElevatorFeedforward(0, 0.199, 0.9787, 0.0031)) 
  .withTelemetry("ElevatorMotor", TelemetryVerbosity.HIGH)
  .withGearing(SmartMechanism.gearing(SmartMechanism.gearbox(6.8444))) 
  // Motor properties to prevent over currenting; i.e. prevents motor from using too much power in a sense
  .withMotorInverted(false)
  .withIdleMode(MotorMode.COAST)
  .withStatorCurrentLimit(Amps.of(60)) // Could adjust in the future, current from motor controller to motor (usually higher than supply)
  .withSupplyCurrentLimit(Amps.of(50)) //Current from battery to motor controller
  .withFollowers(Pair.of(elevatorFollower, true)) //Two motors, line 65 is just telling this to be inverted
  .withStartingPosition(Inches.of(0));
  //.withClosedLoopRampRate(Seconds.of(0.25))
  //.withOpenLoopRampRate(Seconds.of(0.25));

  private SmartMotorController SmartMotorController = new TalonFXWrapper(elevatorMotor, DCMotor.getKrakenX60Foc(2), smcfonfig);

  private final MechanismPositionConfig m_robotToMechanism = new MechanismPositionConfig()
  .withMaxRobotHeight(Meters.of(1.5))
  .withMaxRobotLength(Meters.of(0.75))
  .withRelativePosition(new Translation3d(Meters.of(-0.25), Meters.of(0), Meters.of(0.25)));

  private ElevatorConfig elevconfig = new ElevatorConfig(SmartMotorController)
  .withStartingHeight(Meters.of(0)) //set to 0 while tesitng
  .withHardLimits(Meters.of(0), Meters.of(1.5)) //height limit
  .withTelemetry("Elevator", TelemetryVerbosity.HIGH)
  .withMass(Pounds.of(16))
  .withMechanismPositionConfig(m_robotToMechanism);

  private Elevator elevator = new Elevator(elevconfig);
  //Method returning height--similar to setting speed on a motor
 
  
  //Move the elevator up and down
  public Command set(double DutyCycle) {return elevator.set(DutyCycle);}
  public Command sysId() {return elevator.sysId(Volts.of(7), Volts.of(2).per(Second), Seconds.of(4));}

  public Command setHeight(Distance height) {return elevator.setHeight(height);}
  
  
  /** Creates a new ExampleSubsystem. 
   * @return */
    public void ExampleSubsystem() {}

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    elevator.updateTelemetry();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    elevator.simIterate();
  }
  public Command waitUntilAtHeight(Distance distance) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'waitUntilAtHeight'");
  }
}
