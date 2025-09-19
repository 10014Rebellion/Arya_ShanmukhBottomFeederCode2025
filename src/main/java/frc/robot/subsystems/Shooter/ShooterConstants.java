package frc.robot.subsystems.Shooter;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;


public class ShooterConstants {
    public static final int kDriverControllerPort = 0;

    //Algae Handover Constants
    public static final int kTopShooterMotorID = 32;
    public static final int kMiddleShooterMotorID = 34;
    public static final int kBottomShooterMotorID = 33;
    public static final int kBeamBreakDIOPort = 1;

    public static final MotorType kShooterMotorsType = MotorType.kBrushless;
    public static final SparkFlexConfig kShooterMotorConfig = new SparkFlexConfig();
    

    static {
            kShooterMotorConfig.smartCurrentLimit(50).idleMode(IdleMode.kCoast);
    }

    
}
