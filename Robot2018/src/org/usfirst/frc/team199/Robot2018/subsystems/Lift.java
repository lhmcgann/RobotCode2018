package org.usfirst.frc.team199.Robot2018.subsystems;

import org.usfirst.frc.team199.Robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem implements LiftInterface {

	private WPI_TalonSRX liftMotor;

	private Position targetPosition = Position.GROUND;

	/**
	 * Constructs this subsystem and instantiates all of its components.
	 * 
	 * @param rMap
	 *            the actual RobotMap object, created in Robot
	 */
	public Lift(RobotMap rmap) {
		liftMotor = rmap.liftMotor;
	}

	/**
	 * Set the default command for a subsystem here.
	 */
	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setTargetPosition(Position newPosition) {
		targetPosition = newPosition;
	}

	/**
	 * Uses (insert sensor here) to detect the current lift position
	 */
	@Override
	public double getHeight() {
		return -1;
	}

	/**
	 * stops the lift
	 */
	@Override
	public void stopLift() {
		liftMotor.stopMotor();
	}

	/**
	 * gets current motor values
	 */
	@Override
	public double getLiftSpeed() {
		return liftMotor.get();
	}

	/**
	 * Goes to specified height
	 * 
	 * @param position
	 *            - ground, switch, scale, bar
	 * @param offset
	 *            - distance up or down from the position
	 */
	@Override
	public void goToPosition(Position position, double offset) {

	}

}
