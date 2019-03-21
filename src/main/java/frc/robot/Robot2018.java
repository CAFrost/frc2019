/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2018 extends TimedRobot {
	//private CameraServer camera;

	//private WPI_VictorSPX arm;
	//private WPI_VictorSPX leftFront, leftBack, rightFront, rightBack;
	private SpeedControllerGroup leftDrive, rightDrive;
	private DifferentialDrive differentialDrive;
	private Joystick joystick;
	private Timer m_timer = new Timer();
	private Servo leftClaw, rightClaw, poker;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//camera = CameraServer.getInstance();
		//camera.startAutomaticCapture(0);           //originally ("cam0"), changed to int
		//arm = new WPI_VictorSPX(5);
		//leftFront = new WPI_VictorSPX(2);
		//leftBack = new WPI_VictorSPX(1);
		//rightFront = new WPI_VictorSPX(3);
		//rightBack = new WPI_VictorSPX(4);
		
		//leftDrive = new SpeedControllerGroup(leftFront, leftBack);
		//rightDrive = new SpeedControllerGroup(rightFront, rightBack);
		
		//differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
		
		// servos
		// 6 = PWN0
		// 7 = PWM1
		// 8? = PWM2
		leftClaw = new Servo(1);
		rightClaw = new Servo(0);
		//poker = new Servo(2); //valid range is 0.25 - 0.99

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
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (m_timer.get() < 2.0) {
			differentialDrive.arcadeDrive(-0.65, 0.0); // drive forwards half speed
		} else {
			differentialDrive.stopMotor(); // stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		openClaw();
	}

	
	Boolean isClosed = false;
	Boolean isOpen = false;
	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		differentialDrive.arcadeDrive(joystick.getY(), -joystick.getX());
		Boolean close = joystick.getRawButton(1);
		Boolean open = joystick.getRawButton(2);
		Boolean push = joystick.getRawButton(7);
		if (close && !isClosed)
		{
			closeClaw();
		}
		else if(open && !isOpen)
		{
			openClaw();
		}
		if (push)
		{
			//poker.set(0.90);
		}
		else
		{
			//poker.set(0.25);
		}
		int povValue = joystick.getPOV(); // -1 for no input, 0 for up, 90 for right, 180 for down, and 270 for left.
		if (povValue == 0)
		{
			lowerArm();
		}
		else if (povValue == 180)
		{
			raiseArm();
		}
		else
		{
			holdArm();
		}
	}

	private void raiseArm()
	{
		//arm.set(0.20);
	}
	private void lowerArm()
	{
		//arm.set(-0.05);
	}
	private void holdArm()
	{
		//arm.set(0.05);		
	}
	
	private void closeClaw()
	{
		leftClaw.set(0.45); // 0 = close
		rightClaw.set(0.47); //1 = close
		isOpen = false;
		isClosed = true;
	}

	private void openClaw()
	{
		leftClaw.set(0.73); // 1 = open
		rightClaw.set(0.23); // 0 = open
		isOpen = true;
		isClosed = false;
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
