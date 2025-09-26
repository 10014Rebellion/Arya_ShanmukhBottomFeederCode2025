package frc.robot.subsystems.Intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.ctre.phoenix6.hardware.CANrange;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final SparkMax mPivotMotor;
    private final SparkMax mFlyWheelMotor;
    private final CANrange mRangeSensor;

    private final PIDController mPivotPID;
    private double mPivotTarget;

    public Intake() {
        mPivotMotor = new SparkMax(IntakeConstants.kPivotMotorID, IntakeConstants.kMotorType);
        mFlyWheelMotor = new SparkMax(IntakeConstants.kFlyWheelMotorID, IntakeConstants.kMotorType);

        mPivotMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mFlyWheelMotor.configure(IntakeConstants.kMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        mRangeSensor = new CANrange(IntakeConstants.kRangeSensorID);

        mPivotPID = new PIDController(IntakeConstants.kPivotP, IntakeConstants.kPivotI, IntakeConstants.kPivotD);
        mPivotPID.setTolerance(0.01);
        mPivotTarget = mPivotMotor.getEncoder().getPosition();
    }

    public void intake() {
        mFlyWheelMotor.setVoltage(IntakeConstants.kIntakeVolts);
    }

    public void outtake() {
        mFlyWheelMotor.setVoltage(IntakeConstants.kOuttakeVolts);
    }

    public void hold() {
        mFlyWheelMotor.setVoltage(IntakeConstants.kHoldVolts);
    }

    public void stopPivotAndFlywheels() {
        mPivotTarget = mPivotMotor.getEncoder().getPosition();
        mFlyWheelMotor.setVoltage(0);
    }

    public void pivotUp() {
        mPivotTarget = IntakeConstants.kForwardLimitRotations;
        mFlyWheelMotor.setVoltage(IntakeConstants.kIntakeVolts);
    }

    public void pivotDown() {
        mPivotTarget = IntakeConstants.kReverseLimitRotations;
        mFlyWheelMotor.setVoltage(IntakeConstants.kIntakeVolts);
    }

    public boolean hasPiece() {
        double distance = mRangeSensor.getDistance().getValueAsDouble();
        return distance > 0 && distance < 20;
    }

    @Override
    public void periodic() {
        double pivotPos = mPivotMotor.getEncoder().getPosition();
        double pidOutput = mPivotPID.calculate(pivotPos, mPivotTarget);
        double appliedVolts = Math.max(Math.min(pidOutput, IntakeConstants.kPivotMaxVolts), -IntakeConstants.kPivotMaxVolts);

        if (pivotPos >= IntakeConstants.kForwardLimitRotations && appliedVolts > 0) {
            appliedVolts = 0;
        }
        if (pivotPos <= IntakeConstants.kReverseLimitRotations && appliedVolts < 0) {
            appliedVolts = 0;
        }

        mPivotMotor.setVoltage(appliedVolts);

        SmartDashboard.putNumber("Pivot Position", pivotPos);
        SmartDashboard.putNumber("Pivot Target", mPivotTarget);
        SmartDashboard.putNumber("Pivot Output", appliedVolts);
        SmartDashboard.putBoolean("Intake has piece", hasPiece());
        SmartDashboard.putNumber("Intake distance", mRangeSensor.getDistance().getValueAsDouble());
    }
}
