package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DefaultIntake extends Command {

	private Robot rob;

	public DefaultIntake(Robot robot) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(robot.intakeEject);
		rob = robot;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// 1 and 5 represent the axes' index in driver station
		rob.intakeEject.runLeftIntake(rob.oi.manipulator.getRawAxis(1));
		rob.intakeEject.runRightIntake(rob.oi.manipulator.getRawAxis(5));
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		rob.intakeEject.runIntake(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
