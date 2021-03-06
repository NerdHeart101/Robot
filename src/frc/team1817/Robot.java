/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1817;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// If you rename or move this class, update the build.properties file in the project root
public class Robot extends SampleRobot 
{

    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";

    private DifferentialDrive robotDrive = new DifferentialDrive(new Spark(0), new Spark(1));
    private Joystick stick = new Joystick(0);
    private SendableChooser<String> chooser = new SendableChooser<>();

    public Robot() 
    {
        robotDrive.setExpiration(0.1);
    }

    @Override
    public void robotInit() 
    {
        chooser.addDefault("Default Auto", DEFAULT_AUTO);
        chooser.addObject("My Auto", CUSTOM_AUTO);
        SmartDashboard.putData("Auto modes", chooser);
    }

    @Override
    public void autonomous() 
    {
        String autoSelected = chooser.getSelected();
        // String autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        System.out.println("Auto selected: " + autoSelected);

        // MotorSafety improves safety when motors are updated in loops
        // but is disabled here because motor updates are not looped in
        // this autonomous mode.
        robotDrive.setSafetyEnabled(false);

        switch (autoSelected) 
        {
            case CUSTOM_AUTO:
                // Spin at half speed for two seconds
                robotDrive.arcadeDrive(0.0, 0.5);
                Timer.delay(2.0);

                // Stop robot
                robotDrive.arcadeDrive(0.0, 0.0);
                break;
            case DEFAULT_AUTO:
            default:
                // Drive forwards for two seconds
                robotDrive.arcadeDrive(-0.5, 0.0);
                Timer.delay(2.0);

                // Stop robot
                robotDrive.arcadeDrive(0.0, 0.0);
                break;
        }
    }

    /**
     * Runs the motors with arcade steering.
     *
     * <p>If you wanted to run a similar teleoperated mode with an IterativeRobot
     * you would write:
     *
     * <blockquote><pre>{@code
     * // This method is called periodically during operator control
     * public void teleopPeriodic() {
     *     myRobot.arcadeDrive(stick);
     * }
     * }</pre></blockquote>
     */
    @Override
    public void operatorControl() 
    {
        robotDrive.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) 
        {
            // Drive arcade style
            robotDrive.arcadeDrive(-stick.getY(), stick.getX());

            // The motors will be updated every 5ms
            Timer.delay(0.005);
        }
    }

    /**
     * Runs during test mode.
     */
    @Override
    public void test() 
    {
        
    }
}
