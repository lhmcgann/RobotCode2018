package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OutakeCube extends Command {

	private Timer tim;
	private Robot rob;

	public OutakeCube(Robot robot) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		rob = robot;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		tim = new Timer();
		tim.reset();
		tim.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		rob.intakeEject.runIntake(1);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return tim.get() > rob.getConst("Outake Time", 0.5);
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
