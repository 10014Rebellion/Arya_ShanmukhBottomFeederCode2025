// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Shooter.ShooterIntake;
import frc.robot.subsystems.Shooter.ShooterConstants;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class RobotContainer {
    private final CommandXboxController mDriverController = new CommandXboxController(ShooterConstants.kDriverControllerPort);
    private final ShooterIntake mShooterIntake = new ShooterIntake();



  public RobotContainer() {
    
    mDriverController.rightBumper()
    .onTrue(
      new ParallelCommandGroup(
        new InstantCommand(() -> mShooterIntake.setTopVolts(8)),
        new InstantCommand(() -> mShooterIntake.setMiddleVolts(8)),
        new InstantCommand(() -> mShooterIntake.setBottomVolts(8 * -1))
      )
    )
    .onFalse(
      new ParallelCommandGroup(
        new InstantCommand(() -> mShooterIntake.setTopVolts(0)),
        new InstantCommand(() -> mShooterIntake.setMiddleVolts(0)),
        new InstantCommand(() -> mShooterIntake.setBottomVolts(0))
      )
    );
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
