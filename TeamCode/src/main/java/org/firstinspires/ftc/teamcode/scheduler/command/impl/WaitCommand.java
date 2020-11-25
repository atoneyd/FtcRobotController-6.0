package org.firstinspires.ftc.teamcode.scheduler.command.impl;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.scheduler.command.interfeaces.ICommand;

/**
 * Created by Andrew on 3/9/2018.
 */
public class WaitCommand implements ICommand {
    private long time;

    public WaitCommand(long time) {
        this.time = time;
    }

    @Override
    public void run(OpMode opMode) {
        long start = System.currentTimeMillis();
        while (!Thread.interrupted() && System.currentTimeMillis() - start < time) {
            Thread.yield();
        }
    }
}
