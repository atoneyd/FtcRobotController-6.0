package org.firstinspires.ftc.teamcode.mode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Bootstrapper;
import org.firstinspires.ftc.teamcode.event.Bus;
import org.firstinspires.ftc.teamcode.event.impl.TimingEvent;

/**
 * AP does not work with this implementation
 */
public abstract class LOM extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Bootstrapper.bootstrap(this);

        Bus.getInstance().fire(new TimingEvent(TimingEvent.Timing.INIT));

        m_init();

        waitForStart();

        Bus.getInstance().fire(new TimingEvent(TimingEvent.Timing.START));

        m_main();

        while (opModeIsActive()) {
            idle();
        }

        Bus.getInstance().fire(new TimingEvent(TimingEvent.Timing.END));
    }

    public abstract void m_init();

    public abstract void m_main();
}
