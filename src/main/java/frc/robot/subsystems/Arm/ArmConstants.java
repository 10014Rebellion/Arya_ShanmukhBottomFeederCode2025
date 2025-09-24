package frc.robot.subsystems.Arm;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ArmConstants {
    public static final int kDriverControllerPort = 0;

    public static final int kArmMotorID = 31;

    public static final MotorType kArmMotorType = MotorType.kBrushless;
    public static final SparkMaxConfig kArmMotorConfig = new SparkMaxConfig();

    public static double kPositionConversionFactor = 360.0; //converting arm position to degrees
    public static double kEncoderOffsetRotations = 0.716; 
    //figures out what the position "0" would start from
    // and then offsets all the other position values

    public static double kP = 0.1; //p value
    public static double kI = 0.0; //i value
    public static double kD = 0.0; //d value

    public static double kForwardSoftLimit = 148; //limit the robot can go in the positive directoin
    public static double kReverseSoftLimit = 0.1; //limit the robot can go toward 0 in this case(robot cant go in the negative direction)
    

    

    static {
            kArmMotorConfig.smartCurrentLimit(50).idleMode(IdleMode.kBrake);
            kArmMotorConfig
                .absoluteEncoder
                .zeroOffset(kEncoderOffsetRotations); //defines 0 position for encoder
    }



    
    
}
