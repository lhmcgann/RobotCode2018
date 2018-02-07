package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Toggles between arcade and tank drive.
 */
public class ShiftDriveType extends InstantCommand {

	private Robot robot;

	/**
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public ShiftDriveType(Robot robot) {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.robot = robot;
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("Bool/Arcade Drive", !robot.getBool("Arcade Drive", true));
	}

}
