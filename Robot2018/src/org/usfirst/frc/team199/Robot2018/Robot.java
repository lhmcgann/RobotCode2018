/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team199.Robot2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team199.Robot2018.autonomous.AutoUtils;
import org.usfirst.frc.team199.Robot2018.commands.Autonomous;
import org.usfirst.frc.team199.Robot2018.commands.Autonomous.Position;
import org.usfirst.frc.team199.Robot2018.commands.Autonomous.Strategy;
import org.usfirst.frc.team199.Robot2018.subsystems.Climber;
import org.usfirst.frc.team199.Robot2018.subsystems.ClimberAssist;
import org.usfirst.frc.team199.Robot2018.subsystems.Drivetrain;
import org.usfirst.frc.team199.Robot2018.subsystems.IntakeEject;
import org.usfirst.frc.team199.Robot2018.subsystems.Lift;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

	public RobotMap rmap;
	public Drivetrain dt;
	public Lift lift;
	public IntakeEject intakeEject;
	public Climber climber;
	public ClimberAssist climberAssist;
	public Listener listen;

	public OI oi;

	public Map<String, ArrayList<String[]>> autoScripts;

	Command autonomousCommand;
	SendableChooser<Position> posChooser = new SendableChooser<Position>();
	Map<String, SendableChooser<Strategy>> stratChoosers = new HashMap<String, SendableChooser<Strategy>>();
	String[] fmsPossibilities = { "LL", "LR", "RL", "RR" };

	public double getConst(String key, double def) {
		if (!SmartDashboard.containsKey("Const/" + key)) {
			SmartDashboard.putNumber("Const/" + key, def);
		}
		return SmartDashboard.getNumber("Const/" + key, def);
	}

	public boolean getBool(String key, boolean def) {
		if (!SmartDashboard.containsKey("Bool/" + key)) {
			SmartDashboard.putBoolean("Bool/" + key, def);
		}
		return SmartDashboard.getBoolean("Bool/" + key, def);
	}

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		rmap = new RobotMap(this);
		dt = new Drivetrain(rmap, this);
		lift = new Lift(rmap);
		intakeEject = new IntakeEject(rmap);
		climber = new Climber(rmap);
		climberAssist = new ClimberAssist();

		oi = new OI(this);

		// auto position chooser
		for (Position p : Position.values()) {
			posChooser.addObject(p.getSDName(), p);
		}
		SmartDashboard.putData("Starting Position", posChooser);

		// auto strategy choosers
		for (String input : fmsPossibilities) {
			SendableChooser<Strategy> chooser = new SendableChooser<Strategy>();
			for (Strategy s : Strategy.values()) {
				chooser.addObject(s.getSDName(), s);
			}
			SmartDashboard.putData(input, chooser);
			stratChoosers.put(input, chooser);
		}

		// auto delay chooser
		SmartDashboard.putNumber("Auto Delay", 0);

		// parse scripts from Preferences, which maintains values throughout
		// reboots
		autoScripts = AutoUtils.parseScriptFile(Preferences.getInstance().getString("autoscripts", ""));

		listen = new Listener(this);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called once during the start of autonomous in order to grab
	 * values from SmartDashboard and the FMS and call the Autonomous command with
	 * those values.
	 */
	@Override
	public void autonomousInit() {
		String fmsInput = DriverStation.getInstance().getGameSpecificMessage();
		Position startPos = posChooser.getSelected();
		double autoDelay = SmartDashboard.getNumber("Auto Delay", 0);

		Map<String, Strategy> strategies = new HashMap<String, Strategy>();
		for (Map.Entry<String, SendableChooser<Strategy>> entry : stratChoosers.entrySet()) {
			String key = entry.getKey();
			SendableChooser<Strategy> chooser = entry.getValue();
			strategies.put(key, chooser.getSelected());
		}

		Autonomous auto = new Autonomous(startPos, strategies, autoDelay, fmsInput, false, this);
		auto.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
