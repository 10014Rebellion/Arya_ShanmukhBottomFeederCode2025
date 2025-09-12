package frc.robot.subsystems.Shooter;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    private final SparkFlex mTopShooterMotor;
    private final SparkFlex mMiddleShooterMotor;
    private final SparkFlex mBottomShooterMotor;

    private final DigitalInput mBeamBreak;

    
    public Shooter(){
        this.mTopShooterMotor = new SparkFlex(ShooterConstants.kTopShooterMotorID, ShooterConstants.kShooterMotorsType);
        this.mMiddleShooterMotor = new SparkFlex(ShooterConstants.kTopShooterMotorID, ShooterConstants.kShooterMotorsType);
        this.mBottomShooterMotor = new SparkFlex(ShooterConstants.kTopShooterMotorID, ShooterConstants.kShooterMotorsType);
    
        mTopShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mMiddleShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mBottomShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.mBeamBreak = new DigitalInput(ShooterConstants.kBeamBreakDIOPort);





    }

}
