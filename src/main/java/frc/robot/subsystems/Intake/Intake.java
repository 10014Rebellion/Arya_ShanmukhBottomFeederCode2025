package frc.robot.subsystems.Intake;


import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final SparkMax mLeftMotor;
    private final SparkMax mRightMotor;
    private final DigitalInput mBeamBreak;

    public Intake() {
        mLeftMotor = new SparkMax(IntakeConstants.kPivotMotorID, IntakeConstants.kMotorType);
        mRightMotor = new SparkMax(IntakeConstants.kFlyWheelMotorID, IntakeConstants.kMotorType);

        mLeftMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mRightMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        mBeamBreak = new DigitalInput(IntakeConstants.kBeamBreakDIOPort);
    }

    public void intake() {
        mLeftMotor.setVoltage(IntakeConstants.kIntakeVolts);
        mRightMotor.setVoltage(IntakeConstants.kIntakeVolts);
    }

    public void outtake() {
        mLeftMotor.setVoltage(IntakeConstants.kOuttakeVolts);
        mRightMotor.setVoltage(IntakeConstants.kOuttakeVolts);
    }

    public void hold() {
        mLeftMotor.setVoltage(IntakeConstants.kHoldVolts);
        mRightMotor.setVoltage(IntakeConstants.kHoldVolts);
    }

    public void stop() {
        mLeftMotor.setVoltage(0);
        mRightMotor.setVoltage(0);
    }

    public boolean hasPiece() {
        return !mBeamBreak.get(); // beam broken = piece present
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Intake has piece", hasPiece());
    }
}
