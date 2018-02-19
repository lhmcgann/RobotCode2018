package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;
import org.usfirst.frc.team199.Robot2018.subsystems.LiftInterface;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunLift extends Command {

	private LiftInterface lift;
	private final double SPEED = 0.05;
	private int dir;
	private Robot rob;

	public RunLift(LiftInterface lift, boolean up, Robot robot) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.lift = lift;
		rob = robot;
		requires(rob.lift);
		if (up)
			dir = 1;
		else
			dir = -1;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		lift.runMotor(SPEED * dir);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		lift.stopLift();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
