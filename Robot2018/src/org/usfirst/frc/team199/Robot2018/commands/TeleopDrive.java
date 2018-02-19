/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An example command. You can replace me with your own command.
 */
public class TeleopDrive extends Command {

	private Robot rob;

	/**
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public TeleopDrive(Robot robot) {
		// Use requires() here to declare subsystem dependencies
		requires(robot.dt);
		rob = robot;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Robot.dt.enableVelocityPIDs();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		rob.dt.resetVPIDInputRanges();
		rob.dt.updatePidConstants();
		rob.dt.teleopDrive();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		rob.dt.disableVelocityPIDs();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
