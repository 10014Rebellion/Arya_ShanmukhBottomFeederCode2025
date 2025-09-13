// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.subsystems.Shooter.ShooterIntake;
import frc.robot.subsystems.Shooter.ShooterConstants;
import frc.robot.subsystems.Intake.Intake;

public class RobotContainer {

    private final CommandXboxController mDriverController = new CommandXboxController(ShooterConstants.kDriverControllerPort);

    // Subsystems
    private final ShooterIntake mShooterIntake = new ShooterIntake();
    private final Intake mIntake = new Intake();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
       
        mDriverController.rightBumper()
            .onTrue(
                new ParallelCommandGroup(
                    new InstantCommand(() -> mShooterIntake.setTopVolts(8)),
                    new InstantCommand(() -> mShooterIntake.setMiddleVolts(8)),
                    new InstantCommand(() -> mShooterIntake.setBottomVolts(-8))
                )
            )
            .onFalse(
                new ParallelCommandGroup(
                    new InstantCommand(() -> mShooterIntake.setTopVolts(0)),
                    new InstantCommand(() -> mShooterIntake.setMiddleVolts(0)),
                    new InstantCommand(() -> mShooterIntake.setBottomVolts(0))
                )
            );

       
        mDriverController.rightTrigger()
            .whileTrue(new InstantCommand(() -> mIntake.intake(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stop(), mIntake));

        mDriverController.leftTrigger()
            .whileTrue(new InstantCommand(() -> mIntake.outtake(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stop(), mIntake));

       
        mDriverController.a()
            .whileTrue(new InstantCommand(() -> mIntake.hold(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stop(), mIntake));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
