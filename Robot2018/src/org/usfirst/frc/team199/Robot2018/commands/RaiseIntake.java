package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RaiseIntake extends InstantCommand {

	private Robot rob;

	public RaiseIntake(Robot robot) {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		rob = robot;
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		rob.intakeEject.raiseIntake();
		SmartDashboard.putBoolean("Intake Up", true);
	}

}
