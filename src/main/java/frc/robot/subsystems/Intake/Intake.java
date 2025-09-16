package frc.robot.subsystems.Intake;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.CANrange;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final SparkMax mPivotMotor;
    private final SparkMax mFlyWheelMotor;
    private final CANrange mRangeSensor;

    public Intake() {
        mPivotMotor = new SparkMax(IntakeConstants.kPivotMotorID, IntakeConstants.kMotorType);
        mFlyWheelMotor = new SparkMax(IntakeConstants.kFlyWheelMotorID, IntakeConstants.kMotorType);

        mPivotMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mFlyWheelMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        
        mRangeSensor = new CANrange(IntakeConstants.kRangeSensorID);
        
    }

    public void intake() {
        mPivotMotor.setVoltage(IntakeConstants.kIntakeVolts);
        mFlyWheelMotor.setVoltage(IntakeConstants.kIntakeVolts);
    }

    public void outtake() {
        mPivotMotor.setVoltage(IntakeConstants.kOuttakeVolts);
        mFlyWheelMotor.setVoltage(IntakeConstants.kOuttakeVolts);
    }

    public void hold() {
        mPivotMotor.setVoltage(IntakeConstants.kHoldVolts);
        mFlyWheelMotor.setVoltage(IntakeConstants.kHoldVolts);
    }

    public void stop() {
        mPivotMotor.setVoltage(0);
        mFlyWheelMotor.setVoltage(0);
    }

    public boolean hasPiece() {
        // Detect if a piece is within range
        double distance = mRangeSensor.getRange();
        return distance > 0 && distance < 20; // example threshold in cm
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Intake has piece", hasPiece());
        SmartDashboard.putNumber("Intake distance", mRangeSensor.getRange());
    }
}
