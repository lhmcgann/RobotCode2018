package org.usfirst.frc.team199.Robot2018.subsystems;

import org.usfirst.frc.team199.Robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeEject extends Subsystem implements IntakeEjectInterface {

	private WPI_TalonSRX intakeMotor;

	/**
	 * Constructs this subsystem and instantiates all of its components.
	 * 
	 * @param rMap
	 *            the actual RobotMap object, created in Robot
	 */
	public IntakeEject(RobotMap rmap) {
		intakeMotor = rmap.intakeMotor;
	}

	/**
	 * Set the default command for a subsystem here.
	 */
	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * returns current motor value
	 */
	@Override
	public double getIntakeSpeed() {
		return intakeMotor.get();
	}

	/**
	 * Uses (insert sensor here) to detect a cube in front of the robot.
	 */
	@Override
	public boolean seeCube() {
		return false;
	}

	/**
	 * Uses (insert sensor here) to detect if the cube is currently inside the robot
	 * 
	 */
	@Override
	public boolean hasCube() {
		return false;
	}

	/**
	 * stops the motors
	 * 
	 */
	@Override
	public void stopIntake() {
		intakeMotor.stopMotor();
	}

	/**
	 * Spins the rollers
	 * 
	 * @param speed
	 *            - positive -> rollers in, negative -> rollers out
	 */
	@Override
	public void runIntake(double speed) {

	}

}
