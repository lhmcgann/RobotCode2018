package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.subsystems.IntakeEjectInterface;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Eject the cube by simultaneously spinning the wheels out and extending a
 * piston in the center of the intake. Immediately retract center piston.
 */
public class EjectCubeWithPiston extends CommandGroup {

	public EjectCubeWithPiston(IntakeEjectInterface intEj) {
		addParallel(new EjectCube(intEj));
		addParallel(new ShiftEjectPiston(intEj, true));
		addSequential(new ShiftEjectPiston(intEj, false));
	}
}
