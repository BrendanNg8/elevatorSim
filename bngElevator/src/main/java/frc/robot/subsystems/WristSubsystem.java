package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;
import static yams.mechanisms.SmartMechanism.gearbox;
import static yams.mechanisms.SmartMechanism.gearing;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.subsystemConstants;
import org.littletonrobotics.junction.Logger;
import yams.mechanisms.config.ArmConfig;
import yams.mechanisms.config.MechanismPositionConfig;
import yams.mechanisms.positional.Arm;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;
import yams.telemetry.SmartMotorControllerTelemetryConfig;

public class WristSubsystem extends SubsystemBase {

  private final TalonFX wristMotor = new TalonFX(subsystemConstants.Wrist_ID, subsystemConstants.CANBUS);
  private final SmartMotorControllerTelemetryConfig motorTelemetryConfig =
      new SmartMotorControllerTelemetryConfig()
          .withMechanismPosition()
          .withRotorPosition()
          .withMechanismLowerLimit()
          .withMechanismUpperLimit();
  private final SmartMotorControllerConfig motorConfig =
      new SmartMotorControllerConfig(this)
          .withClosedLoopController(
              0, 0, 0, DegreesPerSecond.of(360), DegreesPerSecondPerSecond.of(1440))
          .withSimClosedLoopController(
              3.772, 0, 0, DegreesPerSecond.of(60), DegreesPerSecondPerSecond.of(60))
          //      .withSoftLimit(Degrees.of(-360), Degrees.of(360))
          .withGearing(gearing(gearbox(67.407)))
          //      .withExternalEncoder(armMotor.getAbsoluteEncoder())
          .withIdleMode(MotorMode.COAST)
          .withTelemetry("ArmMotor", TelemetryVerbosity.HIGH)
          //      .withSpecificTelemetry("ArmMotor", motorTelemetryConfig)
          .withStatorCurrentLimit(Amps.of(40))
          .withSupplyCurrentLimit(Amps.of(40))
          .withMotorInverted(false)
          .withFeedforward(new ArmFeedforward(0, 0, 0, 0))
          .withSimFeedforward(new ArmFeedforward(0, 0.101, 9.34, 0.16))
          .withControlMode(ControlMode.CLOSED_LOOP)
          .withStartingPosition(Degrees.of(0));
  private final SmartMotorController motor =
      new TalonFXWrapper(wristMotor, DCMotor.getKrakenX60(1), motorConfig);
  private final MechanismPositionConfig robotToMechanism =
      new MechanismPositionConfig()
          .withMaxRobotHeight(Meters.of(1.5))
          .withMaxRobotLength(Meters.of(0.75))
          .withRelativePosition(
              new Translation3d(Meters.of(0.0), Meters.of(-0.3246), Meters.of(0.3429)));

  private ArmConfig m_config =
      new ArmConfig(motor)
          .withLength(Meters.of(0.25))
          .withHardLimit(Degrees.of(-360), Degrees.of(360))
          .withTelemetry("ArmExample", TelemetryVerbosity.HIGH)
          .withMass(Pounds.of(6))
          .withStartingPosition(Degrees.of(0))
          .withHorizontalZero(Degrees.of(0))
          .withMechanismPositionConfig(robotToMechanism);
  private final Arm wrist = new Arm(m_config);


  public WristSubsystem() {}

  public void periodic() {
    wrist.updateTelemetry();
  }

  public void simulationPeriodic() {
    wrist.simIterate();
  }

  public Command armCmd(double dutycycle) {
    return wrist.set(dutycycle);
  }

  public void holdAlgaeIdle() {}

  public void idle() {}

  public Command sysId() {
    return wrist.sysId(Volts.of(3), Volts.of(3).per(Second), Second.of(30));
  }

  public Angle getAngle() {
    return wrist.getAngle();
  }

  public Command setAngle(Angle angle) {
    return wrist.setAngle(angle);
  }
  public boolean atAngle(Angle target) {return ((Math.abs(getAngle().abs(Degrees) - target.abs(Degrees)))<= (subsystemConstants.WRIST_TOLERANCE));}
  public Command WaitUntilAtAngle(Angle target) {return edu.wpi.first.wpilibj2.command.Commands.waitUntil(() -> atAngle(target))}
}
