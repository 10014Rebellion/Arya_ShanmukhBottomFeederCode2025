package frc.robot.subsystems.Shooter;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;


public class ShooterSubsystem extends SubsystemBase{
    private final SparkFlex mTopShooterMotor;
    private final SparkFlex mMiddleShooterMotor;
    private final SparkFlex mBottomShooterMotor;

    private final DigitalInput mBeamBreak;

    
    public ShooterSubsystem(){
        this.mTopShooterMotor = new SparkFlex(ShooterConstants.kTopShooterMotorID, ShooterConstants.kShooterMotorsType);
        this.mMiddleShooterMotor = new SparkFlex(ShooterConstants.kMiddleShooterMotorID, ShooterConstants.kShooterMotorsType);
        this.mBottomShooterMotor = new SparkFlex(ShooterConstants.kBottomShooterMotorID, ShooterConstants.kShooterMotorsType);
    
        mTopShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mMiddleShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mBottomShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.mBeamBreak = new DigitalInput(ShooterConstants.kBeamBreakDIOPort);

    }


    
    public void setTopVolts(double pVolts){
        mTopShooterMotor.setVoltage(pVolts);
    }

    public void setMiddleVolts(double pVolts){
        mMiddleShooterMotor.setVoltage(pVolts);
    }

    public void setBottomVolts(double pVolts){
        mBottomShooterMotor.setVoltage(pVolts);
    }
    public boolean hasPiece() {
        return !mBeamBreak.get();
    }


    public Command intakeAlgaeCmd(){
        return new FunctionalCommand(() -> {
            setTopVolts(8);
            setMiddleVolts(8);
            setBottomVolts(8*-1);
        }, 

        () -> {}, 
        
        (interrupted) -> {
            setTopVolts(0);
            setMiddleVolts(3);
            setBottomVolts(0);
        }, 

        () -> hasPiece(),

        this);
    }

    public Command shootAlgaeCmd(){
        return new FunctionalCommand(
            () -> {
                setTopVolts(-10);
                setMiddleVolts(5);
                setBottomVolts(10);
            }, 
            
            () -> {}, 
            
            (interrupted) -> {
                setTopVolts(-10);
                setMiddleVolts(-5);
                setBottomVolts(10);
            },

             ()->!hasPiece(),
             this);
    }

    public Command holdAlgaeCmd(){
        return new InstantCommand(() -> {
            setTopVolts(0);
            setMiddleVolts(3);
            setBottomVolts(0);
        });
    }

    public Command noPiece(){
        return new InstantCommand(() -> {
            setTopVolts(0);
            setMiddleVolts(0);
            setBottomVolts(0);
        });
    }

     @Override
    public void periodic() {
        SmartDashboard.putBoolean("Beam Break detects Algae", hasPiece());
    }


    
    




}
