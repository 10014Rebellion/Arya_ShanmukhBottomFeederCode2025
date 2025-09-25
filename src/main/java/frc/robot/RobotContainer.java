package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeConstants;

public class RobotContainer {
    private final CommandXboxController mDriverController =
        new CommandXboxController(0); 

    private final Intake mIntake = new Intake();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
    
        
        mDriverController.rightTrigger()
        .whileTrue(new InstantCommand(() -> mIntake.intake(), mIntake))
        .onFalse(new InstantCommand(() -> mIntake.stop(), mIntake));

        mDriverController.leftTrigger()
        .whileTrue(new InstantCommand(() -> mIntake.outtake(), mIntake))
        .onFalse(new InstantCommand(() -> mIntake.stop(), mIntake));


        mDriverController.a()
            .whileTrue(new InstantCommand(() -> mIntake.hold(), mIntake))
            .onFalse(new InstantCommand(() -> mIntake.stop(), mIntake));
    
        
        mDriverController.rightBumper()
            .onTrue(new InstantCommand(() -> mIntake.setPivotVoltage(+6))) 
            .onFalse(new InstantCommand(() -> mIntake.setPivotVoltage(0)));

     
        mDriverController.b()
            .onTrue(new InstantCommand(() -> mIntake.setPivotVoltage(-6))) 
            .onFalse(new InstantCommand(() -> mIntake.setPivotVoltage(0)));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
