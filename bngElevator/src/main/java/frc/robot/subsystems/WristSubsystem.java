// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.SmartMechanism;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXSWrapper;
import yams.motorcontrollers.remote.TalonFXWrapper;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.config.ArmConfig;
import yams.mechanisms.positional.Arm;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;
import edu.wpi.first.units.measure.Angle;
import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Pounds;

public class WristSubsystem extends SubsystemBase {
  private SmartMotorControllerConfig smcConfig = new SmartMotorControllerConfig(this)
  .withControlMode(ControlMode.CLOSED_LOOP)
  //Feedback Constants (PID)
  .withClosedLoopController(0, 0, 0, DegreesPerSecond.of(360), DegreesPerSecondPerSecond.of(1440)) //? robot value _=> change to 0?
  .withSimClosedLoopController(0, 0, 0, DegreesPerSecond.of(450), DegreesPerSecondPerSecond.of(720)) //? sim value_=> change to 0?
  //Feedforward Constants 
  .withFeedforward(new ArmFeedforward(0, 0, 0)) //! robot value
  .withSimFeedforward(new ArmFeedforward(0, 0, 0)) //! sim value

  .withTelemetry("ArmMotor", TelemetryVerbosity.HIGH)
  .withGearing(SmartMechanism.gearing(SmartMechanism.gearbox(67.407)))
  .withMotorInverted(false)
  .withIdleMode(MotorMode.COAST) //! Should technically be break but
  .withStatorCurrentLimit(Amps.of(40))
  .withSupplyCurrentLimit(Amps.of(40));
  //.withClosedLoopRampRate(Seconds.of(0.25))
  //.withOpenLoopRampRate(Seconds.of(0.25));

  //Motor controller object
  private final TalonFX talon = new TalonFX(subsystemConstants.Wrist_ID, subsystemConstants.CANBUS); 
  //SmartMotorConroller
  private SmartMotorController talonSMC = new TalonFXWrapper(talon, DCMotor.getKrakenX60Foc(1), smcConfig); 

  
  private ArmConfig armCfg = new ArmConfig(talonSMC)
  //! Soft is applied to the smc PID (SmartMotorController)
  .withSoftLimits(Degrees.of(-20), Degrees.of(10))
  //! Hard is applied to the simulation
  .withHardLimit(Degrees.of(-360), Degrees.of(360))
  //! Where the arm stops
  .withStartingPosition(Degrees.of(-5))
  //! Length and mass of your arm for sim
  .withLength(Meters.of(0.25))
  .withMass(Pounds.of(1))

  .withTelemetry("Arm", TelemetryVerbosity.HIGH);

  private Arm arm = new Arm(armCfg);

  //! Set the angle of the arm
  public Command setAngle(Angle angle) {return arm.setAngle(angle);}

  //! Move the arm up and down
  public Command set(double DutyCycle) {return arm.set(DutyCycle);}

  /** Creates a new WristSubsystem. */
  public WristSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    arm.updateTelemetry();
  }
  @Override
  public void simulationPeriodic() {
    arm.simIterate();
  }
}
