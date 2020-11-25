package org.firstinspires.ftc.teamcode.processor.annotation.component;

import org.firstinspires.ftc.teamcode.robot.RobotMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    String id() default "";

    RobotMapper.Types map() default RobotMapper.Types.NONE;
}
