// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Shooter.ShooterSubsystem;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.ShooterConstants;

public class RobotContainer {
    private final CommandXboxController mDriverController = new CommandXboxController(ShooterConstants.kDriverControllerPort);
    private final ShooterSubsystem mShooter = new ShooterSubsystem();

    private final Intake mIntake = new Intake();

  public RobotContainer() {    
    configureBindings();
  }

  private void configureShooterBindings() {
 new Trigger(() -> mShooter.hasPiece())
      .onTrue(mShooter.holdAlgaeCmd());

    new Trigger(() -> !mShooter.hasPiece()  && (!mDriverController.leftBumper().getAsBoolean()))
      .onTrue(mShooter.zeroCmd());

    new Trigger(() -> (!mShooter.hasPiece()) && (mDriverController.leftBumper().getAsBoolean()))
      .onTrue(mShooter.intakeAlgaeCmd())
      .onFalse(mShooter.zeroCmd());
  

    mDriverController.x()
      .whileTrue(
        mShooter.shootAlgaeCmd()
      );
  }

  private void configureIntakeBindings() {
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


    private void configureBindings() {
      configureIntakeBindings();
      configureShooterBindings();
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
