package org.usfirst.frc.team199.Robot2018.subsystems;

import org.usfirst.frc.team199.Robot2018.Robot;
import org.usfirst.frc.team199.Robot2018.commands.DefaultIntake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeEject extends Subsystem implements IntakeEjectInterface {

	private Robot rob;
	private PowerDistributionPanel pdp;
	private VictorSP leftIntakeMotor;
	private VictorSP rightIntakeMotor;
	private DoubleSolenoid leftVerticalSolenoid;
	private DoubleSolenoid rightVerticalSolenoid;
	private DoubleSolenoid leftHorizontalSolenoid;
	private DoubleSolenoid rightHorizontalSolenoid;

	/**
	 * Constructs this subsystem and instantiates all of its components.
	 * 
	 * @param rMap
	 *            the actual RobotMap object, created in Robot
	 */
	public IntakeEject(Robot robot) {
		rob = robot;
		pdp = robot.rmap.pdp;
		leftIntakeMotor = robot.rmap.leftIntakeMotor;
		rightIntakeMotor = robot.rmap.rightIntakeMotor;
		leftVerticalSolenoid = robot.rmap.leftIntakeVerticalSolenoid;
		rightVerticalSolenoid = robot.rmap.rightIntakeVerticalSolenoid;
		leftHorizontalSolenoid = robot.rmap.leftIntakeHorizontalSolenoid;
		rightHorizontalSolenoid = robot.rmap.rightIntakeHorizontalSolenoid;
	}

	/**
	 * Set the default command for a subsystem here.
	 */
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultIntake(rob));
	}

	/**
	 * @return current left motor value
	 */
	@Override
	public double getLeftIntakeSpeed() {
		return leftIntakeMotor.get();
	}

	/**
	 * @return current right motor value
	 */
	@Override
	public double getRightIntakeSpeed() {
		return rightIntakeMotor.get();
	}

	/**
	 * Uses current to check if the wheels are blocked aka the cube is inside the
	 * robot
	 */
	@Override
	public boolean hasCube() {
		return pdp.getCurrent(rob.rmap.getPort("PDP Intake Left Channel", 4)) > rob.getConst("Max Current", 39)
				|| pdp.getCurrent(rob.rmap.getPort("PDP Intake Right Channel", 11)) > rob.getConst("Max Current", 39);
	}

	/**
	 * stops the motors
	 * 
	 */
	@Override
	public void stopIntake() {
		leftIntakeMotor.stopMotor();
		rightIntakeMotor.stopMotor();
	}

	/**
	 * Sets the left roller to run at the specified speed
	 * 
	 * @param speed
	 *            Speed the left motor should run at
	 */
	@Override
	public void runLeftIntake(double speed) {
		double actualSpeed = speed * rob.getConst("Intake Motor Left Speed Multiplier", 1);
		leftIntakeMotor.set(actualSpeed);
	}

	/**
	 * Sets the left roller to run at the specified speed
	 * 
	 * @param speed
	 *            Speed the left motor should run at
	 */
	@Override
	public void runRightIntake(double speed) {
		double actualSpeed = speed * rob.getConst("Intake Motor Right Speed Multiplier", 1);
		rightIntakeMotor.set(actualSpeed);
	}

	/**
	 * Spins the rollers
	 * 
	 * @param speed
	 *            - positive -> rollers in, negative -> rollers out
	 */
	@Override
	public void runIntake(double speed) {
		runLeftIntake(speed);
		runRightIntake(speed);
	}

	/**
	 * Raises the intake
	 */
	@Override
	public void raiseIntake() {
		DoubleSolenoid.Value leftSet = rob.getBool("Intake Left Vertical Solenoid Inverted", false)
				? DoubleSolenoid.Value.kReverse
				: DoubleSolenoid.Value.kForward;
		DoubleSolenoid.Value rightSet = rob.getBool("Intake Right Vertical Solenoid Inverted", false)
				? DoubleSolenoid.Value.kReverse
				: DoubleSolenoid.Value.kForward;
		leftVerticalSolenoid.set(leftSet);
		rightVerticalSolenoid.set(rightSet);
	}

	/**
	 * Lowers the intake
	 */
	@Override
	public void lowerIntake() {
		DoubleSolenoid.Value leftSet = rob.getBool("Intake Left Vertical Solenoid Inverted", false)
				? DoubleSolenoid.Value.kForward
				: DoubleSolenoid.Value.kReverse;
		DoubleSolenoid.Value rightSet = rob.getBool("Intake Right Vertical Solenoid Inverted", false)
				? DoubleSolenoid.Value.kForward
				: DoubleSolenoid.Value.kReverse;
		leftVerticalSolenoid.set(leftSet);
		rightVerticalSolenoid.set(rightSet);
	}

	/**
	 * Closes the intake
	 */
	@Override
	public void closeIntake() {
		DoubleSolenoid.Value leftSet = rob.getBool("Intake Left Horizontal Solenoid Inverted", false)
				? DoubleSolenoid.Value.kReverse
				: DoubleSolenoid.Value.kForward;
		DoubleSolenoid.Value rightSet = rob.getBool("Intake Right Horizontal Solenoid Inverted", false)
				? DoubleSolenoid.Value.kReverse
				: DoubleSolenoid.Value.kForward;
		leftHorizontalSolenoid.set(leftSet);
		rightHorizontalSolenoid.set(rightSet);
	}

	/**
	 * Opens the intake
	 */
	@Override
	public void openIntake() {
		DoubleSolenoid.Value leftSet = rob.getBool("Intake Left Horizontal Solenoid Inverted", false)
				? DoubleSolenoid.Value.kForward
				: DoubleSolenoid.Value.kReverse;
		DoubleSolenoid.Value rightSet = rob.getBool("Intake Right Horizontal Solenoid Inverted", false)
				? DoubleSolenoid.Value.kForward
				: DoubleSolenoid.Value.kReverse;
		leftHorizontalSolenoid.set(leftSet);
		rightHorizontalSolenoid.set(rightSet);
	}
}
