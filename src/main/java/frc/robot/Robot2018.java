/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;

public class Robot2018 extends TimedRobot {
	private WPI_VictorSPX leftFront, leftBack, rightFront, rightBack, lift;
	private SpeedControllerGroup leftDrive, rightDrive;
	private DifferentialDrive differentialDrive;
	private Joystick joystick;
	private Timer m_timer = new Timer();
	private Servo hatch;
	private Boolean hatchIssHeld = false;
	private Boolean hatchIsReleased = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		hatch = new Servo(0);
		leftFront = new WPI_VictorSPX(1);
		leftBack = new WPI_VictorSPX(2);
		rightFront = new WPI_VictorSPX(3);
		rightBack = new WPI_VictorSPX(4);
		lift = new WPI_VictorSPX(5);
		
		leftDrive = new SpeedControllerGroup(leftFront, leftBack);
		rightDrive = new SpeedControllerGroup(rightFront, rightBack);
		differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
		joystick = new Joystick(0);
	}

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		m_timer.reset();
		m_timer.start();
		holdHatch();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		differentialDrive.arcadeDrive(joystick.getY(), -joystick.getX());
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		holdHatch();
	}
	
	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		differentialDrive.arcadeDrive(joystick.getY(), -joystick.getX());
		Boolean holdHatch = joystick.getRawButton(3);
		Boolean releaseHatch = joystick.getRawButton(1);
		Boolean liftRobot = joystick.getRawButton(6);
		Boolean lowerRobot = joystick.getRawButton(4);
		if (holdHatch && !hatchIssHeld)
		{
			holdHatch();
		}
		else if(releaseHatch && !hatchIsReleased)
		{
			releaseHatch();
		}
		if (liftRobot)
		{
			//lift.set(1.5);
		} 
		else if (lowerRobot)
		{
			//lift.set(-0.5);
		}
		else
		{
			lift.set(0);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	/**
	 * Private functions
	 */
	private void holdHatch()
	{
		hatch.set(0);
	}
	private void releaseHatch()
	{
		hatch.set(1);
	}
}
