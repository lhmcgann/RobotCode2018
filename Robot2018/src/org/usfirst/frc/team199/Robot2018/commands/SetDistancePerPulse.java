package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetDistancePerPulse extends InstantCommand {

	private Robot robot;

	/**
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public SetDistancePerPulse(Robot robot) {
		super();
		this.robot = robot;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		robot.dt.setDistancePerPulseLeft(robot.getConst("Distance Per Pulse Left", 0.184));
		robot.dt.setDistancePerPulseRight(robot.getConst("Distance Per Pulse Right", 0.184));
	}
}
