package frc.robot.subsystems.Intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.ctre.phoenix6.hardware.CANrange;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final SparkMax mPivotMotor;
    private final SparkMax mFlyWheelMotor;
    private final CANrange mRangeSensor;

    private double mRequestedPivotVolts = 0.0; 

    public Intake() {
        mPivotMotor = new SparkMax(IntakeConstants.kPivotMotorID, IntakeConstants.kMotorType);
        mFlyWheelMotor = new SparkMax(IntakeConstants.kFlyWheelMotorID, IntakeConstants.kMotorType);

        mPivotMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mFlyWheelMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        mRangeSensor = new CANrange(IntakeConstants.kRangeSensorID);
    }

    
    public void intake() {
        mRequestedPivotVolts = IntakeConstants.kIntakeVolts;
        mFlyWheelMotor.setVoltage(IntakeConstants.kIntakeVolts);
    }

    public void outtake() {
        mRequestedPivotVolts = IntakeConstants.kOuttakeVolts;
        mFlyWheelMotor.setVoltage(IntakeConstants.kOuttakeVolts);
    }

    public void hold() {
        mRequestedPivotVolts = IntakeConstants.kHoldVolts;
        mFlyWheelMotor.setVoltage(IntakeConstants.kHoldVolts);
    }

    public void stop() {
        mRequestedPivotVolts = 0.0;
        mFlyWheelMotor.setVoltage(0);
    }


    public boolean hasPiece() {
        double distance = mRangeSensor.getDistance().getValueAsDouble();
        return distance > 0 && distance < 20;
    }


    @Override
    public void periodic() {
        double pivotPos = mPivotMotor.getEncoder().getPosition();
        double appliedVolts = mRequestedPivotVolts;


        if (pivotPos >= IntakeConstants.kForwardLimitRotations && appliedVolts > 0) {
            appliedVolts = 0;
        }
        if (pivotPos <= IntakeConstants.kReverseLimitRotations && appliedVolts < 0) {
            appliedVolts = 0;
        }

        if (appliedVolts < 0) {
            appliedVolts *= 1.2;  
        }

        mPivotMotor.setVoltage(appliedVolts);


        SmartDashboard.putBoolean("Intake has piece", hasPiece());
        SmartDashboard.putNumber("Intake distance", mRangeSensor.getDistance().getValueAsDouble());
        SmartDashboard.putNumber("Pivot Encoder Pos", pivotPos);
        SmartDashboard.putNumber("Pivot Applied Volts", appliedVolts);
    }
}
