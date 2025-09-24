package frc.robot.subsystems.Intake;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class IntakeConstants {
    public static final int kPivotMotorID = 41;
    public static final int kFlyWheelMotorID = 43;
    public static final int kRangeSensorID = 5;

    public static final MotorType kMotorType = MotorType.kBrushless;
    public static final SparkFlexConfig kMotorConfig = new SparkFlexConfig();


    public static final double kIntakeVolts = 8.0;
    public static final double kOuttakeVolts = -8.0;
    public static final double kHoldVolts = 1.5;


    public static final double kForwardLimitRotations = 0.582; 
    public static final double kReverseLimitRotations = 0.304;  


    public static final double kPivotP = 2.0;
    public static final double kPivotI = 0.0;
    public static final double kPivotD = 0.1;

    static {
        kMotorConfig.smartCurrentLimit(40).idleMode(IdleMode.kBrake);
    }
}
