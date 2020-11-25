package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.submodule.SubmoduleManager;

public class Robot {

    private static Robot instance;
    private static SubmoduleManager submoduleManager;

    public static Robot getInstance() {
        if (instance == null) {
            instance = new Robot();
        }
        return instance;
    }

    public Robot() {
        submoduleManager = new SubmoduleManager();
    }

    public SubmoduleManager getSubmoduleManager() {
        return submoduleManager;
    }

    public void reset() {
        instance = new Robot();
    }
}
