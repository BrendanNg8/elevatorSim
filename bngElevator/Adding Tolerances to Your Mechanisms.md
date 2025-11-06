### Why Do We Need Tolerances?

When we command setpoints with `Elevator.setHeight()`, `Arm.setAngle()`, or any other setpoint command, the command may will never finish because the mechanism almost never hits the setpoint perfectly. Luckily, there is a very simple way to add tolerances and consider when the setpoint is reached. This will walk you through elevators and arms, but you will realize that this can also be applied to things like shooter RPM

### Create the Tolerances
In your `SubsystemConstants` file, add `Distance` and `Angle` objects that define the allowable error
``` Java
public static final Distance ELEVATOR_TOLERANCE = Inches.of(0.5);
public static final Angle WRIST_TOLERANCE = Degrees.of(2.0);
```

To know if we are at tolerance or not we can return a boolean that reports `true` if the difference between the setpoint and the current height is $\leq$ the tolerance:
1) Get the current height
```Java
public Distance getHeight() {
	return m_elevator.getHeight();
 }
  ```
  2) Determine if we are within tolerance:
``` Java
public boolean atHeight(Distance target) {
	return Math.abs(getHeight().in(Meters) - target.in(Meters))
		<= SubsystemConstants.ELEVATOR_TOLERANCE.in(Meters);
}
```
- We use `abs` to take absolute value of the difference, so it is always a positive number for comparison with the allowance

Wait-until helper
``` Java
public Command waitUntilAtHeight(Distance target) {
	return Commands.waitUntil(() -> atHeight(target));
}
```

To use this, we need to run `setHeight()` until `waitUntilAtHeight` completes. For this, use a deadline group:
``` Java
public static Command L4(ElevatorSubsystem elevator) {
	return Commands.deadline(
		elevator.waitUntilAtHeight(
			SubsystemConstants.ElevatorPosition.L4.distance()),
		elevator.setHeight(
			SubsystemConstants.ElevatorPosition.L4.distance())
	);
}
```
What is happening here:
- `waitUntilAtHeight`(the deadline) and `setHeight` start together
- `setHeight` moves the elevator until `waitUntilAtHeight` finishes, which is when `atHeight` returns true . The group then ends and interrupts `setHeight`, allowing the next command in a sequence to run.