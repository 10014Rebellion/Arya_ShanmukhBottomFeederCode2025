package frc.robot.subsystems.Shooter;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;


public class ShooterIntake extends SubsystemBase{
    private final SparkFlex mTopShooterMotor;
    private final SparkFlex mMiddleShooterMotor;
    private final SparkFlex mBottomShooterMotor;

    private final DigitalInput mBeamBreak;

    
    public ShooterIntake(){
        this.mTopShooterMotor = new SparkFlex(ShooterConstants.kTopShooterMotorID, ShooterConstants.kShooterMotorsType);
        this.mMiddleShooterMotor = new SparkFlex(ShooterConstants.kMiddleShooterMotorID, ShooterConstants.kShooterMotorsType);
        this.mBottomShooterMotor = new SparkFlex(ShooterConstants.kBottomShooterMotorID, ShooterConstants.kShooterMotorsType);
    
        mTopShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mMiddleShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        mBottomShooterMotor.configure(ShooterConstants.kShooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.mBeamBreak = new DigitalInput(ShooterConstants.kBeamBreakDIOPort);

    }


    //if LB held
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

    public Command idleShooterCommand(){
        return new ParallelCommandGroup(
        new InstantCommand(() -> setTopVolts(0)),
        new InstantCommand(() -> setMiddleVolts(3)),
        new InstantCommand(() -> setBottomVolts(0))
      );
    }

    public Command intakeAlgaeCmd(){
        return new FunctionalCommand(() -> {
            setTopVolts(8);
            setMiddleVolts(8);
            setBottomVolts(8*-1);
        }, () -> {}, (interrupted) -> {
            setTopVolts(0);
            setMiddleVolts(3);
            setBottomVolts(0);
        }, () -> hasPiece(), 
        this
        );
    }

     @Override
    public void periodic() {
        SmartDashboard.putBoolean("Beam Break detects Algae", hasPiece());
    }


    
    




}
