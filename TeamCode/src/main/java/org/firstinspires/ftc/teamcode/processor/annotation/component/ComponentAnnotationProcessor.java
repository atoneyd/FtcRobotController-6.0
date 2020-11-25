package org.firstinspires.ftc.teamcode.processor.annotation.component;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.processor.IProcessor;
import org.firstinspires.ftc.teamcode.processor.annotation.IAnnotationProcessor;
import org.firstinspires.ftc.teamcode.robot.RobotMapper;

import java.lang.reflect.Field;

public class ComponentAnnotationProcessor implements IProcessor, IAnnotationProcessor {

    private OpMode opMode;

    private ComponentAnnotationProcessor(OpMode opMode) {
        this.opMode = opMode;
    }

    public static ComponentAnnotationProcessor of(OpMode opMode) {
        return new ComponentAnnotationProcessor(opMode);
    }

    @Override
    public void process() {
        Class<? extends OpMode> clazz = opMode.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Component.class)) continue;
            field.setAccessible(true);

            Component component = field.getAnnotation(Component.class);
            String id = component.id();
            if (id.isEmpty()) id = field.getName();
            Class<?> type = field.getType();
            RobotMapper.Types mapping = component.map();

            try {
                field.set(opMode, opMode.hardwareMap.get(type, id));
                if (mapping.equals(RobotMapper.Types.NONE) || !(field.get(opMode) instanceof HardwareDevice))
                    continue;
                RobotMapper.getInstance().set((HardwareDevice) field.get(opMode), mapping);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
