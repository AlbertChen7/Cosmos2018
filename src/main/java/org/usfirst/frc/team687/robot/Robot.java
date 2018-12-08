/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team687.robot.commands.drive.characterization.VelocityPIDF;
import org.usfirst.frc.team687.robot.constants.DriveConstants;
import org.usfirst.frc.team687.robot.subsystems.Arm;
import org.usfirst.frc.team687.robot.subsystems.Claw;
import org.usfirst.frc.team687.robot.subsystems.Drive;

/**
 * 
 * @author dbarv
 * Code heavily based on Tedklin's, 90% is based on stuff I've learned from him
 *
 */

public class Robot extends TimedRobot {
	public static PowerDistributionPanel pdp;
	public static Drive drive;
	public static Arm arm;
	public static Claw claw;
	public static OI oi;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		pdp = new PowerDistributionPanel();
		arm = new Arm();
		drive = new Drive();
		claw = new Claw();
		oi = new OI();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Robot.drive.calcXY();
		Robot.drive.reportToSmartDashboard();
		Robot.arm.reportState();
		Robot.oi.reportToSmartDashboard();
		SmartDashboard.putData(pdp);
		Robot.drive.stopLog();
	}

	@Override
	public void disabledPeriodic() {
		Robot.drive.calcXY();
		Scheduler.getInstance().run();
		Robot.drive.reportToSmartDashboard();
		Robot.arm.reportState();
		Robot.oi.reportToSmartDashboard();
		SmartDashboard.putData(pdp);
	}

	
	@Override
	public void autonomousInit() {
		Robot.drive.calcXY();	
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		Robot.drive.calcXY();
	}

	@Override
	public void teleopInit() {
		Robot.drive.reportToSmartDashboard();
		Robot.arm.reportState();
		Robot.oi.reportToSmartDashboard();
		Robot.drive.calcXY();
		SmartDashboard.putData(pdp);
		Robot.drive.startLog();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		Robot.drive.calcXY();
		Robot.drive.reportToSmartDashboard();
		Robot.arm.reportState();
		Robot.oi.reportToSmartDashboard();
		SmartDashboard.putData(pdp);
		Robot.drive.logToCSV();

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Robot.drive.calcXY();
		Robot.drive.reportToSmartDashboard();
		Robot.arm.reportState();
		Robot.oi.reportToSmartDashboard();
		SmartDashboard.putData(pdp);

	}
}

