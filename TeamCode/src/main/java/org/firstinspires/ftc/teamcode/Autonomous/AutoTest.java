package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Bootstrapper;
import org.firstinspires.ftc.teamcode.processor.annotation.component.Component;
import org.firstinspires.ftc.teamcode.processor.annotation.preference.Motor;
import org.firstinspires.ftc.teamcode.robot.RobotMapper;
import org.firstinspires.ftc.teamcode.robot.control.PID;
import org.firstinspires.ftc.teamcode.robot.movement.Drive;

@Autonomous
public class AutoTest extends LinearOpMode {

    @Motor(dir = DcMotorSimple.Direction.REVERSE)
    @Component(map = RobotMapper.Types.DRIVE_FRONT_LEFT)
    DcMotorImplEx motorA;

    @Motor
    @Component(map = RobotMapper.Types.DRIVE_FRONT_RIGHT)
    DcMotorImplEx motorB;

    @Motor
    @Component(map = RobotMapper.Types.DRIVE_BACK_RIGHT)
    DcMotorImplEx motorC;

    @Motor(dir = DcMotorSimple.Direction.REVERSE)
    @Component(map = RobotMapper.Types.DRIVE_BACK_LEFT)
    DcMotorImplEx motorD;

//    @Motor(dir = DcMotorSimple.Direction.REVERSE, mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER)
//    @Component
//    DcMotorImplEx elevator;

    @Component(map = RobotMapper.Types.GYRO_PRIMARY)
    BNO055IMU imu;

   // private PID pid;

    @Override
    public void runOpMode() throws InterruptedException {
        Bootstrapper.bootstrap(this);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = false;

        imu.initialize(parameters);

        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        motorA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorC.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       // pid = new PID(0.010, 0.0025, 0.0025, 2, 0);

        waitForStart();

        Drive.driveDistance(this,  2000);

//        Drive.strafeDistance(this, pid, -150);
//
//        Drive.strafeDistance(this, pid, 150);
//
//        Drive.driveDistance(this, pid, -450);
//
//        Drive.driveDistance(this, pid, 450);
//
//        Drive.strafeDistance(this, pid, -150);
//
//        Drive.strafeDistance(this, pid, 150);
//
//        Drive.driveDistance(this, pid, -450);
    }

    /*
    private void driveDistance(int ticks) {
        int initial = motorA.getCurrentPosition();
        if (ticks > 0) {
            while (opModeIsActive() && pid.determine((initial + ticks) - motorA.getCurrentPosition())) {
                Drive.mecanum(0, -clamp(0.1, 0.25, pid.getOutput()), 0);
                idle();
            }
        } else {
            while (opModeIsActive() && pid.determine(motorA.getCurrentPosition() - (initial + ticks))) {
                Drive.mecanum(0, clamp(0.1, 0.25, pid.getOutput()), 0);
                idle();
            }
        }
        Drive.stop();
    }

    private void strafeDistance(int ticks) {
        int initial = motorA.getCurrentPosition();
        if (ticks > 0) {
            while (opModeIsActive() && pid.determine((initial + ticks) - motorA.getCurrentPosition())) {
                Drive.mecanum(clamp(0.2, 0.35, pid.getOutput()), 0, 0);
                idle();
            }
        } else {
            while (opModeIsActive() && pid.determine(motorA.getCurrentPosition() - (initial + ticks))) {
                Drive.mecanum(-clamp(0.2, 0.35, pid.getOutput()), 0, 0);
                idle();
            }
        }
        Drive.stop();
    }

    private double clamp(double min, double max, double value) {
        return Math.max(min, Math.min(max, value));
    }
    */
}
