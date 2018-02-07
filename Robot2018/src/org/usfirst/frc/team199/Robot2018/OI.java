/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team199.Robot2018;

import org.usfirst.frc.team199.Robot2018.commands.PIDMove;
import org.usfirst.frc.team199.Robot2018.commands.PIDTurn;
import org.usfirst.frc.team199.Robot2018.commands.SetDistancePerPulse;
import org.usfirst.frc.team199.Robot2018.commands.ShiftDriveType;
import org.usfirst.frc.team199.Robot2018.commands.ShiftHighGear;
import org.usfirst.frc.team199.Robot2018.commands.ShiftLowGear;
import org.usfirst.frc.team199.Robot2018.commands.UpdatePIDConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public Joystick leftJoy;
	private JoystickButton shiftLowGearButton;
	private JoystickButton shiftHighGearButton;
	private JoystickButton shiftDriveTypeButton;
	private JoystickButton PIDMoveButton;
	private JoystickButton PIDTurnButton;
	public Joystick rightJoy;
	private JoystickButton updatePIDConstantsButton;
	private JoystickButton updateEncoderDPPButton;
	public Joystick manipulator;

	public int getButton(String key, int def) {
		if (!SmartDashboard.containsKey("Button/" + key)) {
			SmartDashboard.putNumber("Button/" + key, def);
		}
		return (int) SmartDashboard.getNumber("Button/" + key, def);
	}

	/**
	 * Instantiates all of the joysticks and joystick buttons. Ties buttons to their
	 * respective commands.
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public OI(Robot robot) {
		leftJoy = new Joystick(0);
		shiftLowGearButton = new JoystickButton(leftJoy, getButton("Shift Low Gear", 3));
		shiftLowGearButton.whenPressed(new ShiftLowGear(robot));
		shiftHighGearButton = new JoystickButton(leftJoy, getButton("Shift High Gear", 5));
		shiftHighGearButton.whenPressed(new ShiftHighGear(robot));
		shiftDriveTypeButton = new JoystickButton(leftJoy, getButton("Shift Drive Type", 2));
		shiftDriveTypeButton.whenPressed(new ShiftDriveType(robot));
		PIDMoveButton = new JoystickButton(leftJoy, getButton("PID Move", 7));
		PIDMoveButton.whenPressed(new PIDMove(40, robot.dt, robot.dt.distEncAvg, robot));
		PIDTurnButton = new JoystickButton(leftJoy, getButton("PID Turn", 8));
		PIDTurnButton.whenPressed(new PIDTurn(30, robot.dt, robot.dt.fancyGyro, robot));

		rightJoy = new Joystick(1);
		updatePIDConstantsButton = new JoystickButton(rightJoy, getButton("Get PID Constants", 8));
		updatePIDConstantsButton.whenPressed(new UpdatePIDConstants(robot));
		updateEncoderDPPButton = new JoystickButton(rightJoy, getButton("Get Encoder Dist Per Pulse", 9));
		updateEncoderDPPButton.whenPressed(new SetDistancePerPulse(robot));

		manipulator = new Joystick(2);
	}
}
