// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeConstants;

public class RobotContainer {
    private final CommandXboxController mDriverController =
        new CommandXboxController(0); // driver port (0 unless you moved it)

    private final Intake mIntake = new Intake();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {

        mDriverController.rightBumper().onTrue(
            new InstantCommand(() -> mIntake.setPivotSetpoint(IntakeConstants.kForwardLimitRotations))
        );

       
        mDriverController.b().onTrue(
            new InstantCommand(() -> mIntake.setPivotSetpoint(IntakeConstants.kReverseLimitRotations))
        );
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
