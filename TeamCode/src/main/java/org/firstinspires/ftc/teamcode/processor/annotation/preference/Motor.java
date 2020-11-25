package org.firstinspires.ftc.teamcode.processor.annotation.preference;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Motor {
    DcMotorSimple.Direction dir() default DcMotorSimple.Direction.FORWARD;

    DcMotor.RunMode mode() default DcMotor.RunMode.RUN_WITHOUT_ENCODER;
}

