/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team199.Robot2018.subsystems;

import org.usfirst.frc.team199.Robot2018.Robot;
import org.usfirst.frc.team199.Robot2018.RobotMap;
import org.usfirst.frc.team199.Robot2018.autonomous.PIDSourceAverage;
import org.usfirst.frc.team199.Robot2018.autonomous.VelocityPIDController;
import org.usfirst.frc.team199.Robot2018.commands.TeleopDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends Subsystem implements DrivetrainInterface {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private Encoder leftEncDist;
	private Encoder rightEncDist;
	public PIDSourceAverage distEncAvg;
	private SpeedControllerGroup dtLeft;
	private SpeedControllerGroup dtRight;
	private DifferentialDrive robotDrive;
	private VelocityPIDController leftVelocityController;
	private VelocityPIDController rightVelocityController;

	public AHRS fancyGyro;
	private DoubleSolenoid dtGear;

	private Robot robot;

	/**
	 * Constructs this subsystem and instantiates all of its components.
	 * 
	 * @param robotMap
	 *            the actual RobotMap object, created in Robot
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public Drivetrain(RobotMap robotMap, Robot robot) {
		leftEncDist = robotMap.leftEncDist;
		rightEncDist = robotMap.rightEncDist;
		distEncAvg = robotMap.distEncAvg;
		dtLeft = robotMap.dtLeft;
		dtRight = robotMap.dtRight;
		robotDrive = robotMap.robotDrive;
		leftVelocityController = robotMap.leftVelocityController;
		rightVelocityController = robotMap.rightVelocityController;

		fancyGyro = robotMap.fancyGyro;
		dtGear = robotMap.dtGear;

		this.robot = robot;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopDrive(robot));
	}

	/**
	 * Drives based on joystick input and SmartDashboard values
	 */
	@Override
	public void teleopDrive() {
		if (robot.getBool("Arcade Drive", true)) {
			if (robot.getBool("Arcade Drive Default Setup", true)) {
				arcadeDrive(robot.oi.leftJoy.getY(), robot.oi.rightJoy.getX());
			} else {
				arcadeDrive(robot.oi.rightJoy.getY(), robot.oi.leftJoy.getX());
			}
		} else {
			tankDrive(robot.oi.leftJoy.getY(), robot.oi.rightJoy.getY());
		}
	}

	/**
	 * Drives the robot based on parameters and SmartDashboard values
	 * 
	 * @param speed
	 *            The amount to move forward
	 * @param turn
	 *            The amount to turn
	 */
	@Override
	public void arcadeDrive(double speed, double turn) {
		robotDrive.arcadeDrive(speed, turn, robot.getBool("Square Drive Values", false));
	}

	/**
	 * Drive the robot based on parameters and SmartDashboard values
	 * 
	 * @param leftSpeed
	 *            The value to run the left of the drivetrain at
	 * @param rightSpeed
	 *            The value to run the right of the drivetrain at
	 */
	@Override
	public void tankDrive(double leftSpeed, double rightSpeed) {
		robotDrive.tankDrive(leftSpeed, rightSpeed, robot.getBool("Square Drive Values", false));
	}

	/**
	 * Used for getting the speed at which the left side of the drivetrain is
	 * currently set to. Gets data straight from SpeedControllerGroup.
	 * 
	 * @return The speed that the left side of the drivetrain is set to [-1, 1]
	 */
	@Override
	public double getDtLeftSpeed() {
		return dtLeft.get();
	}

	/**
	 * Used for getting the speed at which the right side of the drivetrain is
	 * currently set to. Gets data straight from SpeedControllerGroup.
	 * 
	 * @return The speed that the right side of the drivetrain is set to [-1, 1]
	 */
	@Override
	public double getDtRightSpeed() {
		return dtRight.get();
	}

	/**
	 * Updates the PIDControllers' PIDConstants based on SmartDashboard values
	 */
	@Override
	public void updatePidConstants() {
		leftVelocityController.setPID(robot.getConst("VelocityLeftkP", 1), robot.getConst("VelocityLeftkI", 0),
				robot.getConst("VelocityLeftkD", 0));
		rightVelocityController.setPID(robot.getConst("VelocityRightkP", 1), robot.getConst("VelocityRightkI", 0),
				robot.getConst("VelocityRightkD", 0));
		resetVelocityPIDkFConsts();
	}

	/**
	 * Enable the VelocityPIDControllers used for velocity control on each side of
	 * the DT
	 */
	@Override
	public void enableVelocityPIDs() {
		leftVelocityController.enable();
		rightVelocityController.enable();
	}

	/**
	 * Disables the VelocityPIDControllers used for velocity control on each side of
	 * the DT
	 */
	@Override
	public void disableVelocityPIDs() {
		leftVelocityController.disable();
		rightVelocityController.disable();
	}

	/**
	 * Resets the AHRS value
	 */
	@Override
	public void resetAHRS() {
		fancyGyro.reset();
	}

	/**
	 * Used to get the yaw angle (Z-axis in degrees) that the ahrs currently reads
	 * 
	 * @return The angle that the ahrs reads (in degrees)
	 */
	@Override
	public double getAHRSAngle() {
		return fancyGyro.getAngle();
	}

	/**
	 * Resets the encoders' distances to zero
	 */
	@Override
	public void resetDistEncs() {
		leftEncDist.reset();
		rightEncDist.reset();
	}

	/**
	 * Sets the distancePerPulse property on the left encoder
	 * 
	 * @param ratio
	 *            The ratio to set the distancePerPulse to (real dist units/encoder
	 *            pulses)
	 */
	@Override
	public void setDistancePerPulseLeft(double ratio) {
		leftEncDist.setDistancePerPulse(ratio);
	}

	/**
	 * Sets the distancePerPulse property on the right encoder
	 * 
	 * @param ratio
	 *            The ratio to set the distancePerPulse to (real dist units/encoder
	 *            pulses)
	 */
	@Override
	public void setDistancePerPulseRight(double ratio) {
		rightEncDist.setDistancePerPulse(ratio);
	}

	/**
	 * Returns the distance (in real units) that the left encoder reads
	 * 
	 * @return How far the left encoder has traveled in real units since last reset
	 */
	@Override
	public double getLeftEncDist() {
		return leftEncDist.getDistance();
	}

	/**
	 * Returns the distance (in real units) that the right encoder reads
	 * 
	 * @return How far the right encoder has traveled in real units since last reset
	 */
	@Override
	public double getRightEncDist() {
		return rightEncDist.getDistance();
	}

	/**
	 * Activates the solenoid to push the drivetrain into high or low gear.
	 * 
	 * @param highGear
	 *            If the solenoid is to be pushed into high gear (true, kForward) or
	 */
	@Override
	public void shiftGears(boolean highGear) {
		if (highGear ^ robot.getBool("Drivetrain Gear Shift Low", false)) {
			dtGear.set(DoubleSolenoid.Value.kForward);
		} else {
			dtGear.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * Stops the solenoid that pushes the drivetrain into low or high gear
	 */
	@Override
	public void shiftGearSolenoidOff() {
		dtGear.set(DoubleSolenoid.Value.kOff);
	}

	/**
	 * Reset the kf constants for both VelocityPIDControllers based on current DT
	 * gearing (high or low gear).
	 * 
	 * @param newKF
	 *            the new kF constant based on high and low gear max speeds; should
	 *            be 1 / max speed
	 * @return the new kF value as 1 / correct max speed
	 */
	@Override
	public double resetVelocityPIDkFConsts() {
		double newKF = 1 / getCurrentMaxSpeed();
		leftVelocityController.setF(newKF);
		rightVelocityController.setF(newKF);
		return newKF;
	}

	/**
	 * Gets the current max speed of the DT based on gearing (high or low gear)
	 * 
	 * @return the current max speed of the DT in inches/second
	 */
	@Override
	public double getCurrentMaxSpeed() {
		if (robot.getBool("High Gear", true)) {
			return robot.getConst("Max High Speed", 204);
		} else {
			return robot.getConst("Max Low Speed", 84);
		}
	}
}
