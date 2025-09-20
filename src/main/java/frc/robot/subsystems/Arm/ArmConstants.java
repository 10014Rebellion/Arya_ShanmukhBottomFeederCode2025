package frc.robot.subsystems.Arm;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ArmConstants {
    public static final int kDriverControllerPort = 0;

    public static final int kArmMotorID = 31;

    public static final MotorType kArmMotorType = MotorType.kBrushless;
    public static final SparkMaxConfig kArmMotorConfig = new SparkMaxConfig();

    public static double kPositionConversionFactor = 360.0;
    public static double kEncoderOffsetRotations = 0.716;

    public static double kP = 0.1;
    public static double kI = 0.0;
    public static double kD = 0.0;

    public static double kForwardSoftLimit = 148; 
    public static double kReverseSoftLimit = 0.1;
    

    

    static {
            kArmMotorConfig.smartCurrentLimit(50).idleMode(IdleMode.kBrake);
            kArmMotorConfig
                .absoluteEncoder
                .zeroOffset(kEncoderOffsetRotations);
    }



    
    
}
