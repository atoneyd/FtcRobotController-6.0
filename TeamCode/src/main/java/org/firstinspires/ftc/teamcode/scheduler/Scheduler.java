package org.firstinspires.ftc.teamcode.scheduler;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.scheduler.command.interfeaces.ICommand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Andrew on 3/9/2018.
 */
public class Scheduler {
    private static Scheduler instance;
    private List<Callable<Void>> callables;
    private ExecutorService executorService;
    private ExecutorCompletionService<Void> completionService;
    private ScheduledExecutorService scheduledExecutorService;
    private AtomicBoolean halted;

    public enum Mode {
        WAIT, CONTINUE
    }

    private OpMode opMode;
    private Mode mode;

    private Scheduler() {
        callables = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(4);
        completionService = new ExecutorCompletionService<>(executorService);
        scheduledExecutorService = Executors.newScheduledThreadPool(0);
        halted = new AtomicBoolean(false);
        mode = Mode.WAIT;
    }

    public static Scheduler getInstance() {
        if (instance == null) {
            instance = new Scheduler();
        }
        return instance;
    }

    public void reset() {
        shutdownNow();
        instance = new Scheduler();
    }

    /**
     * Provides reference to hardware, stop(), etc...
     */
    public void setOpMode(OpMode opMode) {
        this.opMode = opMode;
    }

    /**
     * WAIT for threads to complete concurrently
     * CONTINUE with program after concurrent start
     * <p>
     * Integrity of command order is lost in CONTINUE mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Schedule a command to be run every so often
     */
//    public void addPeriodic(ICommand command, long initialDelay, long delay, TimeUnit timeUnit) {
//        if (halted.get()) return;
//        Runnable runnable = () -> {
//            if (Thread.interrupted()) return;
//            command.run(opMode);
//        };
//        scheduledExecutorService.scheduleWithFixedDelay(runnable, initialDelay, delay, timeUnit);
//    }

    public void addPeriodic(ICommand command, long initialDelay, long delay, TimeUnit timeUnit, long lifetime) {
        if (halted.get()) return;
        final long startTime = System.currentTimeMillis();
//        Runnable runnable = () -> {
//            if (System.currentTimeMillis() - startTime > lifetime) return;
//            if (Thread.interrupted()) return;
//            command.run(opMode);
//        };
       // scheduledExecutorService.scheduleWithFixedDelay(runnable, initialDelay, delay, timeUnit);
    }

    /**
     * Required for all concurrent commands
     * WAIT mode requires runConcurrent() to be run after a set of concurrent threads are added (max = 4)
     */
    public void addConcurrent(ICommand command) {
        if (halted.get()) return;
//        Callable<Void> callable = () -> {
//            command.run(opMode);
//            return null;
//        };
//        if (mode == Mode.CONTINUE) {
//            completionService.submit(callable);
//        } else {
//            callables.add(callable);
//        }
    }

    /**
     * Only necessary for WAIT mode
     * (CONTINUE will cause addConcurrent to immediately run thread and continue)
     */
    public void runConcurrent() {
        if (halted.get()) return;
        if (mode == Mode.CONTINUE) return;
        try {
            executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callables.clear();
    }

    /**
     * Straight-forward sequential linear program
     * Using concurrent method to allow for shutdownNow
     */
    public void addSequential(ICommand command) {
        if (halted.get()) return;
        if (!callables.isEmpty() && mode == Mode.WAIT) runConcurrent();
        addConcurrent(command);
        runConcurrent();
    }

    /**
     * Terminates executorService
     * New instance will need to be created if called
     */
    public void shutdown() {
        if (halted.get()) return;
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }

    /**
     * Immediately stop all threads
     * Completely halts the Scheduler
     */
    public synchronized void shutdownNow() {
        halted.set(true);
        executorService.shutdownNow();
        scheduledExecutorService.shutdownNow();
    }

    public boolean getHalted() {
        return halted.get();
    }

    public void restart() {
        halted.set(false);
        instance = new Scheduler();
    }
}
