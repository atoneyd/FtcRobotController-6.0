package org.firstinspires.ftc.teamcode.robot.control;

/**
 * Created by Andrew on 12/20/2017.
 */

public class PID {
    private double kp, ki, kd;
    private double lastError, errorSum;
    private double interval, lastTime;
    private double output;
    private double tolerance;
    private double integral, derivative;

    public PID(double kp, double ki, double kd, double tolerance, double interval) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.lastError = 0;
        this.errorSum = 0;
        this.output = 0;
        this.tolerance = tolerance;
        this.interval = interval;
    }

    public void reset() {
        this.lastError = 0;
        this.errorSum = 0;
        this.output = 0;
    }

    public boolean determine(double error) {
        if (System.currentTimeMillis() - lastTime >= interval) {
            integral += error * (System.currentTimeMillis() - lastTime);
            derivative = (error - lastError) / (System.currentTimeMillis() - lastTime);

            output = kp * error + ki * integral + kd * derivative;
            lastError = error;
            lastTime = System.currentTimeMillis();
        }
        if (Math.abs(error) <= tolerance) {
            // TODO: PID is self resetting
            reset();
            return false;
        } else {
            return true;
        }
    }

    public double getOutput() {
        return output;
    }

}