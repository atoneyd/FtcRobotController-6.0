package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.ConfigManager;
import org.firstinspires.ftc.teamcode.event.Bus;
import org.firstinspires.ftc.teamcode.event.Subscriber;
import org.firstinspires.ftc.teamcode.event.impl.TimingEvent;
import org.firstinspires.ftc.teamcode.processor.ProcessorProvider;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.RobotMapper;
import org.firstinspires.ftc.teamcode.scheduler.Scheduler;
import org.firstinspires.ftc.teamcode.submodule.impl.TestSubmodule;
import org.firstinspires.ftc.teamcode.submodule.interfaces.ISubmodule;

public class Bootstrapper {

    public enum Mode {
        AUTO, TELEOP
    }

    public static void bootstrap(OpMode opMode /*, Mode mode*/) {
        Bus.getInstance().reset();
        RobotMapper.getInstance().reset();
        RobotMapper.getInstance().setActiveOpMode(opMode);
        ConfigManager.getInstance().bind(opMode.hardwareMap.appContext.getAssets());

        ProcessorProvider.init(opMode);

        Robot.getInstance().reset();

        registerSubmodules();
        registerListeners();

        Scheduler.getInstance().reset();

        //setup(mode);

        Bus.getInstance().fire(new TimingEvent(TimingEvent.Timing.BOOTSTRAP_COMPLETE));
    }

    private static void registerListeners() {

    }

    private static void registerSubmodules() {
        registerSubscriberAndListener(TestSubmodule.class);
    }

    private static void registerSubscriberAndListener(Class... classes) {
        for (Class<?> clazz : classes) {
            try {
                if (ISubmodule.class.isAssignableFrom(clazz)) {
                    ISubmodule submodule = (ISubmodule) clazz.newInstance();
                    Robot.getInstance().getSubmoduleManager().add(submodule);
                    if (clazz.isAnnotationPresent(Subscriber.class))
                        Bus.getInstance().register(clazz, submodule);
                } else {
                    Bus.getInstance().register(clazz, clazz.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void shutdown() {

    }

    //TODO: implement control
    private static void setup(Mode mode) {

        switch (mode) {
            case AUTO:

                break;
            case TELEOP:

                break;
        }
    }
}
