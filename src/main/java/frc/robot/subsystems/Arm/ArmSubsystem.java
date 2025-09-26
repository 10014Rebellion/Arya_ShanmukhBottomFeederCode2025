package frc.robot.subsystems.Arm;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    private final SparkMax mArmMotor;
    private final AbsoluteEncoder mArmEncoder;

    private ProfiledPIDController controller; //method that essentially creates a controller that assings our values of PID
    

  public ArmSubsystem() {
      controller = new ProfiledPIDController(
        ArmConstants.kP,
        ArmConstants.kI,
        ArmConstants.kD,
        new Constraints(1000, 1200)
      );

      this.mArmMotor = new SparkMax(ArmConstants.kArmMotorID, ArmConstants.kArmMotorType);
      mArmMotor.configure(ArmConstants.kArmMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      this.mArmEncoder = mArmMotor.getAbsoluteEncoder(); //reports position of encoder even when robot is stopped or smth happens
  }
  
  public boolean isPIDAtGoal() {
    return controller.atGoal();
  }

  public void setMotorVoltage(double pVoltage){ //setting motor volts
    if(isOutOfBounds(pVoltage)) mArmMotor.stopMotor(); //if we are out of bounds, we want motor to stop
    else mArmMotor.setVoltage(pVoltage); //otherwise apply the voltage
  }

  public FunctionalCommand setPidCmd(double pSetPoint){ //parameters takes desired setpoint
    
    return new FunctionalCommand(
      ()-> {
        controller.setGoal(pSetPoint); 
        controller.reset(mArmEncoder.getPosition());
      },

      ()-> {
        double calculatedPID = controller.calculate(getEncoderReading()); //calculating what to apply to motor using encoder reading
        SmartDashboard.putNumber("Arm/Calculated output", calculatedPID); 
        SmartDashboard.putNumber("Arm/Goal", pSetPoint);
        SmartDashboard.putNumber("Arm/kP", ArmConstants.kP);
        SmartDashboard.putNumber("Arm/kD", ArmConstants.kP);
        
        setMotorVoltage(calculatedPID); //setting motor voltage as whatever we get from calculation
      },

      (interrupted)-> {
        setMotorVoltage(0);
      },

       () -> isPIDAtGoal(),
       
      
       this);
  }

  private boolean isOutOfBounds(double pInput) { //creating boolean for if robot goes beyond soft limits
    return (pInput > 0 && getEncoderReading() >= ArmConstants.kForwardSoftLimit)
        || (pInput < 0 && getEncoderReading() <= ArmConstants.kReverseSoftLimit);
  }

  public double getEncoderReading(){ //getting encoder reading and multiplying by 360 to get it in degrees
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
