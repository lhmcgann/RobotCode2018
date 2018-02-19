package org.usfirst.frc.team199.Robot2018.commands;

import java.util.ArrayList;

import org.usfirst.frc.team199.Robot2018.Robot;
import org.usfirst.frc.team199.Robot2018.autonomous.AutoUtils;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Gets the script name and interprets the script into individual commands.
 */
public class RunScript extends CommandGroup {

	private Robot rob;

	public RunScript(String scriptName, Robot robot) {
		rob = robot;
		ArrayList<String[]> script = rob.autoScripts.getOrDefault(scriptName, new ArrayList<String[]>());

		outerloop: for (String[] cmd : script) {
			String cmdName = cmd[0];
			String cmdArgs = cmd[1];

			switch (cmdName) {
			case "moveto":
				addSequential(new AutoMoveTo(cmdArgs.split(" "), rob));
				break;
			case "turn":
				if (AutoUtils.isPoint(cmdArgs)) {
					double[] point = AutoUtils.parsePoint(cmdArgs);
					addSequential(new PIDTurn(point, rob.dt, rob.sd, rob.dt.getGyro(), rob));
				} else {
					double rotation = Double.parseDouble(cmdArgs);
					addSequential(new PIDTurn(rotation, rob.dt, rob.sd, rob.dt.getGyro(), rob));
				}
				break;
			case "move":
				double distance = Double.parseDouble(cmdArgs);
				addSequential(new PIDMove(distance, rob.dt, rob.sd, rob.dt.getDistEncAvg(), rob));
				break;
			case "switch":
				addSequential(new EjectToSwitch());
				break;
			case "scale":
				addSequential(new EjectToScale());
				break;
			case "exchange":
				addSequential(new EjectToExchange());
				break;
			case "wait":
				addSequential(new WaitCommand(Double.parseDouble(cmdArgs)));
				break;
			case "intake":
				addSequential(new IntakeCube(rob));
				break;
			case "jump":
				addSequential(new RunScript(cmdArgs, rob));
				break;
			case "end":
				break outerloop;
			default:
				// this should never happen since AutoUtils already validates the script.
				System.err.println("[ERROR] `" + cmdName + "` is not a valid command name.");
			}
		}
	}
}
