package org.usfirst.frc.team199.Robot2018.subsystems;

import org.usfirst.frc.team199.Robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem implements ClimberInterface {

	private WPI_TalonSRX climberMotor;

	public Climber(RobotMap rmap) {
		climberMotor = rmap.climberMotor;
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
	 * runs the motors
	 * 
	 */
	@Override
	public void runClimber(double speed) {

	}

	/**
	 * attaches the climber hook to the lift. Requires that Lift is on the ground
	 */
	@Override
	public void attachToLift() {

	}

	/**
	 * attaches hook to bar and releases it from the lift
	 */
	@Override
	public void attachToBar() {

	}

	/**
	 * stops the climber
	 */
	@Override
	public void stopClimber() {
		climberMotor.stopMotor();
	}

}
