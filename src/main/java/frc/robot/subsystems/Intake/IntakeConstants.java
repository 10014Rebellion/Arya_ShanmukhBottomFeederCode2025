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

    static {
        kMotorConfig.smartCurrentLimit(40).idleMode(IdleMode.kBrake);
    }
}
