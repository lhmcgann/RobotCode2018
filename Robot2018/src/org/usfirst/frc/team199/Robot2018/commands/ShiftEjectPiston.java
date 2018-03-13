package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.subsystems.IntakeEjectInterface;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ShiftEjectPiston extends InstantCommand {

	private IntakeEjectInterface intEj;
	private boolean out;

	public ShiftEjectPiston(IntakeEjectInterface intEj, boolean extend) {
		super();
		this.intEj = intEj;
		out = extend;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		intEj.shiftEjectPiston(out);
	}
}
