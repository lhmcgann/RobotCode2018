package org.usfirst.frc.team199.Robot2018.subsystems;

public interface IntakeEjectInterface {

	/**
	 * Shifts the eject piston in and out.
	 * 
	 * @param extend
	 *            - whether to shift extend the piston (true) or retract it (false)
	 */
	public void shiftEjectPiston(boolean extend);

	/**
	 * Set the default command for a subsystem here.
	 */
	public void initDefaultCommand();

	// /**
	// * returns current left motor value
	// */
	// public double getLeftIntakeSpeed();
	//
	// /**
	// * returns current right motor value
	// */
	// public double getRightIntakeSpeed();

	/**
	 * Uses current to check if the wheels are blocked aka the cube is inside the
	 * robot
	 *
	 */
	public boolean hasCube();

	/**
	 * stops the motors
	 *
	 */
	public void stopIntake();

	/**
	 * Sets the left roller to run at the specified speed
	 *
	 * @param speed
	 *            Speed the left motor should run at
	 */
	public void runLeftIntake(double speed);

	/**
	 * Sets the right roller to run at the specified speed
	 *
	 * @param speed
	 *            Speed the right motor should run at
	 */
	public void runRightIntake(double speed);

	/**
	 * Spins the rollers. If motors not inverted, negative -> intaking, positive ->
	 * ejecting
	 * 
	 * @param intaking
	 *            - true if intaking, false if ejecting
	 */
	public void runIntake(boolean intaking);

	/**
	 * Closes the intake (kForward)
	 */
	public void closeIntake();

	/**
	 * Opens the intake (kReverse)
	 */
	public void openIntake();
}
