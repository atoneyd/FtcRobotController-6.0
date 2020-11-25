package org.firstinspires.ftc.teamcode.robot.movement;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.robot.RobotMapper;
import org.firstinspires.ftc.teamcode.robot.control.PID;
import org.firstinspires.ftc.teamcode.robot.sensors.BNO055;

public class Drive {

    static final double TOLERANCE = 5;

    public static void power(double left, double right) {
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_FRONT_LEFT, DcMotorImplEx.class).setPower(left);
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_FRONT_RIGHT, DcMotorImplEx.class).setPower(right);
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_LEFT, DcMotorImplEx.class).setPower(left);
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_RIGHT, DcMotorImplEx.class).setPower(right);
    }

    public static void stop() {
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_FRONT_LEFT, DcMotorImplEx.class).setPower(0);
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_FRONT_RIGHT, DcMotorImplEx.class).setPower(0);
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_LEFT, DcMotorImplEx.class).setPower(0);
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_RIGHT, DcMotorImplEx.class).setPower(0);
    }

    //TODO: Check motor directions
    public static void mecanum(double x, double y, double yawPower) {
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_FRONT_LEFT, DcMotorImplEx.class).setPower(Range.clip(-y + x + yawPower, -1, 1));
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_FRONT_RIGHT, DcMotorImplEx.class).setPower(Range.clip(y + x + yawPower, -1, 1));
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_RIGHT, DcMotorImplEx.class).setPower(Range.clip(y - x + yawPower, -1, 1));
        RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_LEFT, DcMotorImplEx.class).setPower(Range.clip(-y - x + yawPower, -1, 1));
    }

    public static void driveDistance(LinearOpMode om,  int ticks) {
        DcMotorImplEx motorA = RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_LEFT, DcMotorImplEx.class);

//        motorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int initial = motorA.getCurrentPosition();

        int lastError = Integer.MAX_VALUE;

        if (ticks > 0) {
            Drive.mecanum(0, -0.4, 0);
            int error = Math.abs(motorA.getCurrentPosition() - (initial - ticks));
            while (om.opModeIsActive() && error > 5) {
                //while (om.opModeIsActive() && motorA.getCurrentPosition() - (initial - ticks) > 5) {
                om.idle();

                // error should be strictly decreasing
                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs(motorA.getCurrentPosition() - (initial - ticks));
            }
        } else {
            Drive.mecanum(0, 0.4, 0);
            int error = Math.abs((initial - ticks) - motorA.getCurrentPosition());
            while (om.opModeIsActive() && error > 5) {
                //while (om.opModeIsActive() && (initial - ticks) - motorA.getCurrentPosition() > 5) {
                om.idle();

                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs((initial - ticks) - motorA.getCurrentPosition());
            }
        }

        Drive.stop();
        /*
        double max = 1D;

        if (ticks > 0) {
            while (om.opModeIsActive() && pid.determine((initial + ticks) - motorA.getCurrentPosition())) {
                double out = pid.getOutput();
                if (out > max) max = out;

                Drive.mecanum(0, -clamp(0.1, 0.25, out), 0);
                om.idle();
            }
        } else {
            while (om.opModeIsActive() && pid.determine(motorA.getCurrentPosition() - (initial + ticks))) {
                double out = pid.getOutput();
                if (out > max) max = out;

                Drive.mecanum(0, clamp(0.1, 0.25, out), 0);
                om.idle();
            }
        }
        Drive.stop();
        */
    }

    public static void strafeDistance(LinearOpMode om, PID pid, int ticks) {
        DcMotorImplEx motorA = RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_LEFT, DcMotorImplEx.class);

//        motorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int initial = motorA.getCurrentPosition();

        int lastError = Integer.MAX_VALUE;

        if (ticks > 0) {
            Drive.mecanum(0.5, 0, 0);
            int error = Math.abs(motorA.getCurrentPosition() - (initial + ticks));
            while (om.opModeIsActive() && error > 5) {

                // encoder: 0 to -target
                // (initial + ticks) + pos
                //while (om.opModeIsActive() && (-initial + ticks) + motorA.getCurrentPosition() > 5) {
                om.idle();

                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs(motorA.getCurrentPosition() - (initial + ticks));
            }
        } else {
            Drive.mecanum(-0.5, 0, 0);
            int error = Math.abs((initial + ticks) - motorA.getCurrentPosition());
            while (om.opModeIsActive() && error > 5) {

                // encoder: 0 to target
                //
                //while (om.opModeIsActive() && (initial + ticks) - motorA.getCurrentPosition() > 5) {
                om.idle();

                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs((initial + ticks) - motorA.getCurrentPosition());
            }
        }

        Drive.stop();

        /*
        double max = 1D;

        if (ticks > 0) {
            while (om.opModeIsActive() && pid.determine((initial + ticks) - motorA.getCurrentPosition())) {
                double out = pid.getOutput();
                if (out > max) max = out;

                Drive.mecanum(clamp(0.35, 0.5, out), 0, 0);
                om.idle();
            }
        } else {
            while (om.opModeIsActive() && pid.determine(motorA.getCurrentPosition() - (initial + ticks))) {
                om.telemetry.addData("pos", motorA.getCurrentPosition());
                om.telemetry.addData("target", initial + ticks);
                om.telemetry.update();

                double out = pid.getOutput();
                if (out > max) max = out;

                Drive.mecanum(-clamp(0.35, 0.5, out), 0, 0);
                om.idle();
            }
        }
        Drive.stop();
        */
    }

    private static double clamp(double min, double max, double value) {
        return Math.max(min, Math.min(max, value));
    }

    public static void turnDistance(LinearOpMode om, PID pid, int ticks) {

        DcMotorImplEx motorA = RobotMapper.getInstance().get(RobotMapper.Types.DRIVE_BACK_LEFT, DcMotorImplEx.class);


        int initial = motorA.getCurrentPosition();

        int lastError = Integer.MAX_VALUE;

        if (ticks > 0) {
            Drive.mecanum(0, 0, 0.5);
            int error = Math.abs(motorA.getCurrentPosition() - (initial - ticks));
            while (om.opModeIsActive() && error > 5) {
                //while (om.opModeIsActive() && motorA.getCurrentPosition() - (initial - ticks) > 5) {
                om.idle();

                // error should be strictly decreasing
                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs(motorA.getCurrentPosition() - (initial - ticks));
            }
        } else {
            Drive.mecanum(0, 0, -0.5);
            int error = Math.abs((initial - ticks) - motorA.getCurrentPosition());
            while (om.opModeIsActive() && error > 5) {
                //while (om.opModeIsActive() && (initial - ticks) - motorA.getCurrentPosition() > 5) {
                om.idle();

                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs((initial - ticks) - motorA.getCurrentPosition());
            }
        }

        Drive.stop();
    }

    public static void turnTo(LinearOpMode om, BNO055 bno055, double target) {
//        bno055.resetAngle();
        if (target > 0) {
            // ccw
            Drive.mecanum(0, 0, -0.4);
            while (om.opModeIsActive() && Math.abs(target - bno055.getAngle()) > 3) {
                om.idle();
            }
        } else {
            // cw
            Drive.mecanum(0, 0, 0.4);
            while (om.opModeIsActive() && Math.abs(bno055.getAngle() - target) > 3) {
                om.idle();
            }
        }
        Drive.stop();
    }

    public static void driveStraight() {
        BNO055IMU imu = (BNO055IMU) RobotMapper.getInstance().get(RobotMapper.Types.GYRO_PRIMARY);

    }

    public static void driveWithHeading() {
        BNO055IMU imu = (BNO055IMU) RobotMapper.getInstance().get(RobotMapper.Types.GYRO_PRIMARY);

    }
}
