package org.firstinspires.ftc.teamcode.processor.annotation.preference;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.processor.IProcessor;
import org.firstinspires.ftc.teamcode.processor.annotation.IAnnotationProcessor;

import java.lang.reflect.Field;

public class MotorAnnotationProcessor implements IProcessor, IAnnotationProcessor {

    private OpMode opMode;

    private MotorAnnotationProcessor(OpMode opMode) {
        this.opMode = opMode;
    }

    public static MotorAnnotationProcessor of(OpMode opMode) {
        return new MotorAnnotationProcessor(opMode);
    }

    @Override
    public void process() {
        Class<? extends OpMode> clazz = opMode.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Motor.class)) continue;
            field.setAccessible(true);

            Motor motor = field.getAnnotation(Motor.class);
            DcMotorSimple.Direction direction = motor.dir();
            DcMotor.RunMode runMode = motor.mode();

            try {
                if (!(field.get(opMode) instanceof DcMotor)) continue;
                ((DcMotor) field.get(opMode)).setMode(runMode);
                ((DcMotor) field.get(opMode)).setDirection(direction);
                ((DcMotor) field.get(opMode)).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
