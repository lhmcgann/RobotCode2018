package org.usfirst.frc.team199.Robot2018.commands;

import org.usfirst.frc.team199.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Used for testing the autonomous interfaces. Runs the script specified in
 * SmartDashboard
 */
public class TestAuto extends CommandGroup implements TestAutoInterface {

	/**
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public TestAuto(Robot robot) {
		addSequential(new RunScript(getScriptToTest(), robot));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getScriptToTest() {
		return SmartDashboard.getString("Auto Script", "NOTHING");
	}
}
