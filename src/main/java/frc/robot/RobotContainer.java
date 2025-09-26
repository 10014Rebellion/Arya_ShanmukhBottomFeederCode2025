package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.subsystems.Intake.Intake;

public class RobotContainer {
    private final CommandXboxController mDriverController = new CommandXboxController(0);
    private final Intake mIntake = new Intake();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        mDriverController.rightTrigger()
            .whileTrue(new RunCommand(() -> mIntake.intake(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stopPivotAndFlywheels(), mIntake));

        mDriverController.leftTrigger()
            .whileTrue(new RunCommand(() -> mIntake.outtake(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stopPivotAndFlywheels(), mIntake));

        mDriverController.a()
            .whileTrue(new RunCommand(() -> mIntake.hold(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stopPivotAndFlywheels(), mIntake));

        mDriverController.rightBumper()
            .whileTrue(new RunCommand(() -> mIntake.pivotUp(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stopPivotAndFlywheels(), mIntake));

        mDriverController.b()
            .whileTrue(new RunCommand(() -> mIntake.pivotDown(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stopPivotAndFlywheels(), mIntake));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
