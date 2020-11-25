package org.firstinspires.ftc.teamcode.math;

public class MathUtils {

    public static float calculateInitialAngle(float initialAngle, float preTheta) {
        float targetTheta = preTheta + initialAngle;

        if (targetTheta > 180) targetTheta -= 360;
        else if (targetTheta < -180) targetTheta += 360;

        return targetTheta;
    }
}
