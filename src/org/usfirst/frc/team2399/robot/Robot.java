package org.usfirst.frc.team2399.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Joystick stick;
	DoubleSolenoid tester;
	
	CANTalon leftFrontTalon;
	CANTalon leftMiddleTalon;
	CANTalon leftBackTalon;
	
	CANTalon rightFrontTalon;
	CANTalon rightMiddleTalon;
	CANTalon rightBackTalon;
	

	@Override
	public void robotInit() {
		stick = new Joystick(0);
//		tester = new DoubleSolenoid(3, 2, 3);
		tester = new DoubleSolenoid(3, 0, 1);
		
		leftFrontTalon = new CANTalon(8);
		leftMiddleTalon = new CANTalon(7);
		leftBackTalon = new CANTalon(3);
		
		rightFrontTalon = new CANTalon(1);
		rightMiddleTalon = new CANTalon(2);
		rightBackTalon = new CANTalon(5);
		
		follow(leftMiddleTalon, leftFrontTalon);
		follow(leftBackTalon, leftFrontTalon);
		follow(rightMiddleTalon, rightFrontTalon);
		follow(rightBackTalon, rightFrontTalon);
		
	}	
	

	public static void follow(CANTalon follower, CANTalon leader) {
		follower.changeControlMode(CANTalon.TalonControlMode.Follower);
		follower.set(leader.getDeviceID());
	}


	@Override
	public void teleopPeriodic() {

// 1 = full forward/right
// -1 = full backwards/left
		
		double leftStickY = stick.getRawAxis(1) * -1;
		double rightStickY = stick.getRawAxis(3) * -1;
		double leftStickX = stick.getRawAxis(0);
		double rightStickX = stick.getRawAxis(2);
		
		
		tankDrive(leftStickY, rightStickY, leftFrontTalon, rightFrontTalon);
//		arcadeDrive(leftStickY, rightStickX, leftFrontTalon, rightFrontTalon);
		
		if(stick.getRawButton(5)) {
			tester.set(DoubleSolenoid.Value.kForward);
		} else if(stick.getRawButton(6)) {
			tester.set(DoubleSolenoid.Value.kReverse);
		}
		
	}


	private static void tankDrive(double leftPercent, double rightPercent, CANTalon leftTalon, CANTalon rightTalon) {
		
		double leftSideSpeed = leftPercent * -1;
		double rightSideSpeed = rightPercent;
		
		leftTalon.set(leftSideSpeed);
		rightTalon.set(rightSideSpeed);
		
	}
	
	private static void arcadeDrive(double forwardPercent, double turnPercent, CANTalon leftTalon, CANTalon rightTalon) {
		double leftSideSpeed = (forwardPercent + turnPercent) * -1;
		double rightSideSpeed = (forwardPercent - turnPercent);
		
		leftTalon.set(leftSideSpeed);
		rightTalon.set(rightSideSpeed);
	}
	
	@Override
	public void teleopInit() {
		tester.set(DoubleSolenoid.Value.kForward);
	}
	
}