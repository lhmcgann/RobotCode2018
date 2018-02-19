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
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public PowerDistributionPanel pdp;

	public WPI_TalonSRX liftMotor;
	public Encoder liftEnc;
	public DigitalSource liftEncPort1;
	public DigitalSource liftEncPort2;

	public WPI_TalonSRX climberMotor;

	public VictorSP leftIntakeMotor;
	public VictorSP rightIntakeMotor;
	public DoubleSolenoid leftIntakeVerticalSolenoid;
	public DoubleSolenoid rightIntakeVerticalSolenoid;
	public DoubleSolenoid leftIntakeHorizontalSolenoid;
	public DoubleSolenoid rightIntakeHorizontalSolenoid;

	public DigitalSource leftEncPort1;
	public DigitalSource leftEncPort2;
	public Encoder leftEncDist;
	public Encoder leftEncRate;
	public WPI_TalonSRX dtLeftMaster;
	public WPI_VictorSPX dtLeftSlave;
	public SpeedControllerGroup dtLeft;
	public VelocityPIDController leftVelocityController;

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

	private final double DIST_PER_PULSE_RATIO = (5.0 * Math.PI) * (17.0 / 25) / (3.0 * 256);

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
		// so if we went above 40, the motors would stop completely
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
		pdp = new PowerDistributionPanel();

		liftMotor = new WPI_TalonSRX(getPort("LiftTalonSRX", 7));
		configSRX(liftMotor);
		liftEncPort1 = new DigitalInput(getPort("1LiftEnc", 4));
		liftEncPort2 = new DigitalInput(getPort("2LiftEnc", 5));
		liftEnc = new Encoder(liftEncPort1, liftEncPort2);
		liftEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		climberMotor = new WPI_TalonSRX(getPort("ClimberTalonSRX", 6));
		configSRX(climberMotor);

		leftIntakeMotor = new VictorSP(getPort("IntakeLeftVictorSP", 9));
		rightIntakeMotor = new VictorSP(getPort("IntakeRightVictorSP", 8));
		leftIntakeHorizontalSolenoid = new DoubleSolenoid(getPort("IntakeLeftHorizontalSolenoidPort1", 2),
				getPort("IntakeLeftHorizontalSolenoidPort2", 3));
		rightIntakeHorizontalSolenoid = new DoubleSolenoid(getPort("IntakeRightHorizontalSolenoidPort1", 4),
				getPort("IntakeRightHorizontalSolenoidPort2", 5));
		// leftIntakeVerticalSolenoid = new
		// DoubleSolenoid(getPort("IntakeLeftVerticalSolenoidPort1", 6),
		// getPort("IntakeLeftVerticalSolenoidPort2", 7));
		// rightIntakeVerticalSolenoid = new
		// DoubleSolenoid(getPort("IntakeRightVerticalSolenoidPort1", 8),
		// getPort("IntakeRightVerticalSolenoidPort2", 9));

		leftEncPort1 = new DigitalInput(getPort("1LeftEnc", 2));
		leftEncPort2 = new DigitalInput(getPort("2LeftEnc", 3));
		leftEncDist = new Encoder(leftEncPort1, leftEncPort2);
		leftEncDist.setPIDSourceType(PIDSourceType.kDisplacement);
		leftEncRate = new Encoder(leftEncPort1, leftEncPort2);
		leftEncRate.setPIDSourceType(PIDSourceType.kRate);
		leftEncDist.setDistancePerPulse(robot.getConst("DPP", DIST_PER_PULSE_RATIO));
		leftEncRate.setDistancePerPulse(robot.getConst("DPP", DIST_PER_PULSE_RATIO));

		dtLeftMaster = new WPI_TalonSRX(getPort("LeftTalonSRXMaster", 1));
		configSRX(dtLeftMaster);
		dtLeftSlave = new WPI_VictorSPX(getPort("LeftVictorSPXSlave", 2));
		configSPX(dtLeftSlave);
		dtLeft = new SpeedControllerGroup(dtLeftMaster, dtLeftSlave);
		// inverted bc gear boxes invert from input to output
		dtLeft.setInverted(true);

		leftVelocityController = new VelocityPIDController(robot.getConst("VelocityLeftkI", 0), 0,
				robot.getConst("VelocityLeftkD", calcDefkD(robot.getConst("Max Low Speed", 84))),
				1 / robot.getConst("Max Low Speed", 84), leftEncRate, dtLeft);
		leftVelocityController.setInputRange(-robot.getConst("Max High Speed", 204),
				robot.getConst("Max High Speed", 204));
		leftVelocityController.setOutputRange(-1.0, 1.0);
		leftVelocityController.setContinuous(false);
		leftVelocityController.setAbsoluteTolerance(robot.getConst("VelocityToleranceLeft", 2));
		SmartDashboard.putData(leftVelocityController);

		rightEncPort1 = new DigitalInput(getPort("1RightEnc", 1));
		rightEncPort2 = new DigitalInput(getPort("2RightEnc", 0));
		rightEncDist = new Encoder(rightEncPort1, rightEncPort2);
		rightEncDist.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncRate = new Encoder(rightEncPort1, rightEncPort2);
		rightEncRate.setPIDSourceType(PIDSourceType.kRate);
		rightEncDist.setDistancePerPulse(robot.getConst("DPP", DIST_PER_PULSE_RATIO));
		rightEncRate.setDistancePerPulse(robot.getConst("DPP", DIST_PER_PULSE_RATIO));
		rightEncRate.setReverseDirection(true);

		dtRightMaster = new WPI_TalonSRX(getPort("RightTalonSRXMaster", 4));
		configSRX(dtRightMaster);
		dtRightSlave = new WPI_VictorSPX(getPort("RightVictorSPXSlave", 3));
		configSPX(dtRightSlave);
		dtRight = new SpeedControllerGroup(dtRightMaster, dtRightSlave);
		// inverted bc gear boxes invert from input to output
		dtRight.setInverted(true);

		// if (!robot.getBool("Talon PID", false)) {
		// leftVelocityController = new
		// VelocityPIDController(robot.getConst("VelocityLeftkI", 0),
		// /* robot.getConst("VelocityLeftkD", 0) */ 0, robot.getConst("VelocityLeftkP",
		// 0),
		// 1 / robot.getConst("Max Low Speed", 84), leftEncRate, dtLeft);
		// leftVelocityController.setInputRange(-robot.getConst("Max High Speed", 204),
		// robot.getConst("Max High Speed", 204));
		// leftVelocityController.setOutputRange(-1.0, 1.0);
		// leftVelocityController.setContinuous(false);
		// leftVelocityController.setAbsoluteTolerance(robot.getConst("VelocityToleranceLeft",
		// 2));
		// rightVelocityController = new
		// VelocityPIDController(robot.getConst("VelocityRightkI", 0),
		// /* robot.getConst("VelocityRightkD", 0) */ 0,
		// robot.getConst("VelocityRightkP", 0),
		// 1 / robot.getConst("Max Low Speed", 84), rightEncRate, dtRight);
		// rightVelocityController.setInputRange(-robot.getConst("Max High Speed", 204),
		// robot.getConst("Max High Speed", 204));
		// rightVelocityController.setOutputRange(-1.0, 1.0);
		// rightVelocityController.setContinuous(false);
		// rightVelocityController.setAbsoluteTolerance(robot.getConst("VelocityToleranceRight",
		// 2));
		//
		// robotDrive = new DifferentialDrive(leftVelocityController,
		// rightVelocityController);
		// } else {
		// leftTalVelController = new
		// TalonVelocityController(robot.getConst("VelocityLeftkP", 0),
		// robot.getConst("VelocityLeftkI", 0), robot.getConst("VelocityLeftkD", 0),
		// 1 / robot.getConst("Max Low Speed", 84), leftEncRate, dtLeftMaster, 0, 0);
		//
		// rightTalVelController = new
		// TalonVelocityController(robot.getConst("VelocityRightkP", 0),
		// robot.getConst("VelocityRightkI", 0), robot.getConst("VelocityRightkD", 0),
		// 1 / robot.getConst("Max Low Speed", 84), rightEncRate, dtRightMaster, 0, 0);
		//
		// robotDrive = new DifferentialDrive(leftTalVelController,
		// rightTalVelController);
		// }
		//
		// robotDrive.setMaxOutput(robot.getConst("Max High Speed", 204));

		rightVelocityController = new VelocityPIDController(robot.getConst("VelocityRightkI", 0), 0,
				robot.getConst("VelocityRightkD", calcDefkD(robot.getConst("Max Low Speed", 84))),
				1 / robot.getConst("Max Low Speed", 84), rightEncRate, dtRight);
		rightVelocityController.setInputRange(-robot.getConst("Max High Speed", 204),
				robot.getConst("Max High Speed", 204));
		rightVelocityController.setOutputRange(-1.0, 1.0);
		rightVelocityController.setContinuous(false);
		rightVelocityController.setAbsoluteTolerance(robot.getConst("VelocityToleranceRight", 2));

		// robotDrive = new DifferentialDrive(leftVelocityController,
		// rightVelocityController);
		// robotDrive.setMaxOutput(robot.getConst("Max High Speed", 204));
		robotDrive = new DifferentialDrive(dtLeft, dtRight);

		distEncAvg = new PIDSourceAverage(leftEncDist, rightEncDist);
		fancyGyro = new AHRS(SPI.Port.kMXP);
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
			if (!SmartDashboard.putNumber("Port/" + key, def)) {
				System.err.println("SmartDashboard Key" + "Port/" + key + "already taken by a different type");
				return def;
			}
		}
		return (int) SmartDashboard.getNumber("Port/" + key, def);
	}

	/**
	 * Uses SmartDashboard and math to calculate a *great* default kD
	 */
	public double calcDefkD(double maxSpeed) {
		/*
		 * timeConstant is proportional to max speed of the shaft (which is the max
		 * speed of the cim divided by the gear reduction), half the mass (because the
		 * half of the drivetrain only has to support half of the robot), and radius of
		 * the drivetrain wheels squared. It's inversely proportional to the stall
		 * torque of the shaft, which is found by multiplying the stall torque of the
		 * motor with the gear reduction by the amount of motors. The omegaMax needs to
		 * be converted from rpm to radians per second, so divide by 60 and multiply to
		 * get radians.
		 */
		double gearReduction = robot.getBool("High Gear", false) ? robot.getConst("High Gear Gear Reduction", 5.392)
				: robot.getConst("Low Gear Gear Reduction", 12.255);
		double radius = robot.getConst("Radius of Drivetrain Wheel", 0.0635);
		double timeConstant = robot.getConst("Omega Max", 5330) / gearReduction / 60 * 2 * Math.PI
				* convertNtokG(robot.getConst("Weight of Robot", 342)) / 2 * radius * radius
				/ (robot.getConst("Stall Torque", 2.41) * gearReduction * 2);
		double cycleTime = robot.getConst("Code cycle time", 0.05);
		/*
		 * The denominator of kD is 1-(e ^ -cycleTime / timeConstant). The numerator is
		 * one.
		 */
		double denominator = Math.pow(Math.E, 1 * cycleTime / timeConstant) - 1;
		return 1 / denominator / maxSpeed;
	}

	private double convertLbsTokG(double lbs) {
		// number from google ;)
		return lbs * 0.45359237;
	}

	private double convertNtokG(double newtons) {
		// weight / accel due to grav = kg
		return newtons / 9.81;
	}
}
