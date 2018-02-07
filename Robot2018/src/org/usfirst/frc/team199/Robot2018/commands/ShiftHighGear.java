package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shifts the DT to high gear.
 */
public class ShiftHighGear extends InstantCommand {

	private Robot robot;

	/**
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public ShiftHighGear(Robot robot) {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.robot = robot;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		robot.dt.shiftGears(true);
		SmartDashboard.putBoolean("High Gear", true);
		robot.dt.resetVelocityPIDkFConsts();
	}
}
