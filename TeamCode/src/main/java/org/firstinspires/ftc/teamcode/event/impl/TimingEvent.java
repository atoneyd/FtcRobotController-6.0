package org.firstinspires.ftc.teamcode.event.impl;

import org.firstinspires.ftc.teamcode.event.IEvent;

public class TimingEvent implements IEvent {

    public enum Timing {
        BOOTSTRAP_COMPLETE, INIT, START, END
    }

    private Timing timing;

    public TimingEvent(Timing timing) {
        this.timing = timing;
    }

    public Timing getTiming() {
        return timing;
    }

    public void setTiming(Timing timing) {
        this.timing = timing;
    }
}
