package org.usfirst.frc.team199.Robot2018;

import org.usfirst.frc.team199.Robot2018.commands.SetDistancePerPulse;
import org.usfirst.frc.team199.Robot2018.commands.UpdatePIDConstants;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Listener implements TableEntryListener {

	private Robot robot;

	/**
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public Listener(Robot robot) {
		this.robot = robot;
	}

	private void addCommand(Command com) {
		Scheduler.getInstance().add(com);
	}

	@Override
	public void valueChanged(NetworkTable arg0, String arg1, NetworkTableEntry arg2, NetworkTableValue arg3, int arg4) {
		if (arg4 == EntryListenerFlags.kNew)
			return;
		switch (arg1) {
		case "Const/Distance Per Pulse Left":
			addCommand(new SetDistancePerPulse(robot));
			break;
		case "Const/Distance Per Pulse Right":
			addCommand(new SetDistancePerPulse(robot));
			break;
		case "Const/TurnkP":
			addCommand(new UpdatePIDConstants(robot));
			break;
		case "Const/TurnkI":
			addCommand(new UpdatePIDConstants(robot));
			break;
		case "Const/TurnkD":
			addCommand(new UpdatePIDConstants(robot));
			break;
		case "Const/MovekP":
			addCommand(new UpdatePIDConstants(robot));
			break;
		case "Const/MovekI":
			addCommand(new UpdatePIDConstants(robot));
			break;
		case "Const/MovekD":
			addCommand(new UpdatePIDConstants(robot));
			break;

		}
	}

}
