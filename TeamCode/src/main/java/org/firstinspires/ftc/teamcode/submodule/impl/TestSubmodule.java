package org.firstinspires.ftc.teamcode.submodule.impl;

import org.firstinspires.ftc.teamcode.event.Subscriber;
import org.firstinspires.ftc.teamcode.event.impl.TimingEvent;
import org.firstinspires.ftc.teamcode.submodule.interfaces.ISubmodule;

public class TestSubmodule implements ISubmodule {

    @Subscriber
    public void onTimingEvent(TimingEvent event) {
        if (event.getTiming() == TimingEvent.Timing.INIT) {
            
        }
    }

    public void test() {

    }
}
