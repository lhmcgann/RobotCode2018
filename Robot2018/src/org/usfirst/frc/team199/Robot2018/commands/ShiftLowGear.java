package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shifts the DT to low gear.
 */
public class ShiftLowGear extends InstantCommand {

	private Robot robot;

	/**
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public ShiftLowGear(Robot robot) {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.robot = robot;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		robot.dt.shiftGears(false);
		SmartDashboard.putBoolean("High Gear", false);
		robot.dt.resetVelocityPIDkFConsts();
	}
}
