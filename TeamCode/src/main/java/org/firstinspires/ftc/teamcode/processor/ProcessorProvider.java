package org.firstinspires.ftc.teamcode.processor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.processor.annotation.component.ComponentAnnotationProcessor;
import org.firstinspires.ftc.teamcode.processor.annotation.preference.MotorAnnotationProcessor;

public class ProcessorProvider {

    private static ComponentAnnotationProcessor componentAnnotationProcessor;
    private static MotorAnnotationProcessor motorAnnotationProcessor;

    public enum Type {
        COMPONENT, MOTOR
    }

    public static void init(OpMode opMode) {
        componentAnnotationProcessor = ComponentAnnotationProcessor.of(opMode);
        componentAnnotationProcessor.process();

        motorAnnotationProcessor = MotorAnnotationProcessor.of(opMode);
        motorAnnotationProcessor.process();
    }

    public static IProcessor getProcessor(Type type) {
        if (type.equals(Type.COMPONENT)) {
            return componentAnnotationProcessor;
        } else if (type.equals(Type.MOTOR)) {
            return motorAnnotationProcessor;
        }
        return null;
    }
}
