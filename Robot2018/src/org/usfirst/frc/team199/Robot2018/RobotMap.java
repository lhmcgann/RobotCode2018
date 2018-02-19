/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team199.Robot2018;

import org.usfirst.frc.team199.Robot2018.autonomous.PIDSourceAverage;
import org.usfirst.frc.team199.Robot2018.autonomous.TalonVelocityController;
import org.usfirst.frc.team199.Robot2018.autonomous.VelocityPIDController;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public WPI_TalonSRX intakeMotor;
	public WPI_TalonSRX liftMotor;
	public WPI_TalonSRX climberMotor;

	public DigitalSource leftEncPort1;
	public DigitalSource leftEncPort2;
	public Encoder leftEncDist;
	public Encoder leftEncRate;
	public WPI_TalonSRX dtLeftMaster;
	public WPI_VictorSPX dtLeftSlave;
	public SpeedControllerGroup dtLeft;
	public VelocityPIDController leftVelocityController;
	public TalonVelocityController leftTalVelController;

	public DigitalSource rightEncPort1;
	public DigitalSource rightEncPort2;
	public Encoder rightEncDist;
	public Encoder rightEncRate;
	public WPI_TalonSRX dtRightMaster;
	public WPI_VictorSPX dtRightSlave;
	public SpeedControllerGroup dtRight;
	public VelocityPIDController rightVelocityController;
	public TalonVelocityController rightTalVelController;

	public DifferentialDrive robotDrive;
	public PIDSourceAverage distEncAvg;

	public AHRS fancyGyro;
	public DoubleSolenoid dtGear;

	public Robot robot;

	/**
	 * This function takes in a TalonSRX motorController and sets nominal and peak
	 * outputs to the default
	 * 
	 * @param mc
	 *            the TalonSRX to configure
	 */
	private void configSRX(WPI_TalonSRX mc) {
		// stuff cole said to put
		int kTimeout = (int) robot.getConst("kTimeoutMs", 10);
		mc.configNominalOutputForward(0, kTimeout);
		mc.configNominalOutputReverse(0, kTimeout);
		mc.configPeakOutputForward(1, kTimeout);
		mc.configPeakOutputReverse(-1, kTimeout);

		// current limiting stuff cole said to put
		mc.configPeakCurrentLimit(0, 0);
		mc.configPeakCurrentDuration(0, 0);
		// 40 amps is the amp limit of a CIM. also, the PDP has 40 amp circuit breakers,
		// so if we went above 40, the motors would stop completely.
		mc.configContinuousCurrentLimit(40, 0);
		mc.enableCurrentLimit(true);
	}

	/**
	 * This function takes in a VictorSPX motorController and sets nominal and peak
	 * outputs to the default
	 * 
	 * @param mc
	 *            the VictorSPX to configure
	 */
	private void configSPX(WPI_VictorSPX mc) {
		// stuff cole said to put
		int kTimeout = (int) robot.getConst("kTimeoutMs", 10);
		mc.configNominalOutputForward(0, kTimeout);
		mc.configNominalOutputReverse(0, kTimeout);
		mc.configPeakOutputForward(1, kTimeout);
		mc.configPeakOutputReverse(-1, kTimeout);
	}

	/**
	 * Instantiates all components of the robot and sets the appropriate settings.
	 * 
	 * @param robot
	 *            the actual Robot object, for non-static purposes
	 */
	public RobotMap(Robot robot) {

		this.robot = robot;

		intakeMotor = new WPI_TalonSRX(getPort("IntakeTalonSRX", 4));
		configSRX(intakeMotor);
		liftMotor = new WPI_TalonSRX(getPort("LiftTalonSRX", 5));
		configSRX(liftMotor);
		climberMotor = new WPI_TalonSRX(getPort("ClimberTalonSRX", 6));
		configSRX(climberMotor);

		leftEncPort1 = new DigitalInput(getPort("1LeftEnc", 0));
		leftEncPort2 = new DigitalInput(getPort("2LeftEnc", 1));
		leftEncDist = new Encoder(leftEncPort1, leftEncPort2);
		leftEncDist.setPIDSourceType(PIDSourceType.kDisplacement);
		leftEncRate = new Encoder(leftEncPort1, leftEncPort2);
		leftEncRate.setPIDSourceType(PIDSourceType.kRate);
		dtLeftMaster = new WPI_TalonSRX(getPort("LeftTalonSRXMaster", 0));
		configSRX(dtLeftMaster);
		dtLeftSlave = new WPI_VictorSPX(getPort("LeftTalonSPXSlave", 1));
		configSPX(dtLeftSlave);
		dtLeft = new SpeedControllerGroup(dtLeftMaster, dtLeftSlave);

		rightEncPort1 = new DigitalInput(getPort("1RightEnc", 2));
		rightEncPort2 = new DigitalInput(getPort("2RightEnc", 3));
		rightEncDist = new Encoder(leftEncPort1, leftEncPort2);
		rightEncDist.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncRate = new Encoder(leftEncPort1, leftEncPort2);
		rightEncRate.setPIDSourceType(PIDSourceType.kRate);
		dtRightMaster = new WPI_TalonSRX(getPort("RightTalonSRXMaster", 2));
		configSRX(dtRightMaster);
		dtRightSlave = new WPI_VictorSPX(getPort("RightTalonSPXSlave", 3));
		configSPX(dtRightSlave);
		dtRight = new SpeedControllerGroup(dtRightMaster, dtRightSlave);

		if (!robot.getBool("Talon PID", false)) {
			leftVelocityController = new VelocityPIDController(robot.getConst("VelocityLeftkI", 0),
					/* robot.getConst("VelocityLeftkD", 0) */ 0, robot.getConst("VelocityLeftkP", 0),
					1 / robot.getConst("Max Low Speed", 84), leftEncRate, dtLeft);
			leftVelocityController.setInputRange(-robot.getConst("Max High Speed", 204),
					robot.getConst("Max High Speed", 204));
			leftVelocityController.setOutputRange(-1.0, 1.0);
			leftVelocityController.setContinuous(false);
			leftVelocityController.setAbsoluteTolerance(robot.getConst("VelocityToleranceLeft", 2));

			rightVelocityController = new VelocityPIDController(robot.getConst("VelocityRightkI", 0),
					/* robot.getConst("VelocityRightkD", 0) */ 0, robot.getConst("VelocityRightkP", 0),
					1 / robot.getConst("Max Low Speed", 84), rightEncRate, dtRight);
			rightVelocityController.setInputRange(-robot.getConst("Max High Speed", 204),
					robot.getConst("Max High Speed", 204));
			rightVelocityController.setOutputRange(-1.0, 1.0);
			rightVelocityController.setContinuous(false);
			rightVelocityController.setAbsoluteTolerance(robot.getConst("VelocityToleranceRight", 2));

			robotDrive = new DifferentialDrive(leftVelocityController, rightVelocityController);
		} else {
			leftTalVelController = new TalonVelocityController(robot.getConst("VelocityLeftkP", 0),
					robot.getConst("VelocityLeftkI", 0), robot.getConst("VelocityLeftkD", 0),
					1 / robot.getConst("Max Low Speed", 84), leftEncRate, dtLeftMaster, 0, 0);

			rightTalVelController = new TalonVelocityController(robot.getConst("VelocityRightkP", 0),
					robot.getConst("VelocityRightkI", 0), robot.getConst("VelocityRightkD", 0),
					1 / robot.getConst("Max Low Speed", 84), rightEncRate, dtRightMaster, 0, 0);

			robotDrive = new DifferentialDrive(leftTalVelController, rightTalVelController);
		}

		robotDrive.setMaxOutput(robot.getConst("Max High Speed", 204));

		distEncAvg = new PIDSourceAverage(leftEncDist, rightEncDist);
		fancyGyro = new AHRS(SerialPort.Port.kMXP);
		dtGear = new DoubleSolenoid(getPort("1dtGearSolenoid", 0), getPort("2dtGearSolenoid", 1));
	}

	/**
	 * Used in RobotMap to find ports for robot components, getPort also puts
	 * numbers if they don't exist yet.
	 * 
	 * @param key
	 *            The port key
	 * @param def
	 *            The default value
	 * @return returns the Port
	 */
	public int getPort(String key, int def) {
		if (!SmartDashboard.containsKey("Port/" + key)) {
			SmartDashboard.putNumber("Port/" + key, def);
		}
		return (int) SmartDashboard.getNumber("Port/" + key, def);
	}

}
