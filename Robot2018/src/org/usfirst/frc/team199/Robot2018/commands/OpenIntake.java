package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class OpenIntake extends InstantCommand {

	private Robot rob;

	public OpenIntake(Robot robot) {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		rob = robot;
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		rob.intakeEject.openIntake();
		SmartDashboard.putBoolean("Intake Open", true);
	}

}
