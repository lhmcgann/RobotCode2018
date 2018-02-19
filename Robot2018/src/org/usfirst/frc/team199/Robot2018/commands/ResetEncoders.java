package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetEncoders extends InstantCommand {

	private Robot rob;

	public ResetEncoders(Robot robot) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super();
		rob = robot;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		rob.dt.resetDistEncs();
	}
}
