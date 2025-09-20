package frc.robot.subsystems.Arm;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    private final SparkMax mArmMotor;
    private final AbsoluteEncoder mArmEncoder;

    private PIDController controller =
      new PIDController(
          ArmConstants.kP,
          ArmConstants.kI,
          ArmConstants.kD);

  public ArmSubsystem() {
      controller.enableContinuousInput(0, 360);
      this.mArmMotor = new SparkMax(ArmConstants.kArmMotorID, ArmConstants.kArmMotorType);
      mArmMotor.configure(ArmConstants.kArmMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      this.mArmEncoder = mArmMotor.getAbsoluteEncoder();
  }

  public void setMotorVoltage(double pVoltage){
    if(isOutOfBounds(pVoltage)) mArmMotor.stopMotor();
    else mArmMotor.setVoltage(pVoltage);
  }

  public FunctionalCommand setPidCmd(double pSetPoint){
    
    return new FunctionalCommand(
      ()-> {
        controller.setSetpoint(pSetPoint);
      },

      ()-> {
        double calculation = controller.calculate(getEncoderReading());
        SmartDashboard.putNumber("Arm/Calculated output", calculation);
        setMotorVoltage(calculation);
      },

      (interrupted)-> {
        setMotorVoltage(0);
      },

       () -> false,
       
      
       this);
  }

  private boolean isOutOfBounds(double pInput) {
    return (pInput > 0 && getEncoderReading() >= ArmConstants.kForwardSoftLimit)
        || (pInput < 0 && getEncoderReading() <= ArmConstants.kReverseSoftLimit);
  }

  public double getEncoderReading(){
    return mArmEncoder.getPosition() * ArmConstants.kPositionConversionFactor; 
  }
  @Override
  public void periodic() {
    if(isOutOfBounds(mArmMotor.getAppliedOutput())) mArmMotor.stopMotor();
      SmartDashboard.putNumber("Arm/Encoder Reading", getEncoderReading());
      SmartDashboard.putNumber("Arm/Bus Voltage", mArmMotor.getBusVoltage());
      SmartDashboard.putNumber("Arm/Temperature", mArmMotor.getMotorTemperature());
  }
}
