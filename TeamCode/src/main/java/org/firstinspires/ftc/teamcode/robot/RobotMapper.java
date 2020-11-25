package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import java.util.HashMap;
import java.util.Map;

public class RobotMapper {
    private static RobotMapper instance;

    private Map<Types, HardwareDevice> map;
    private Map<Types, BNO055IMU> imus;

    private OpMode activeOpMode;

    public void reset() {
        map.clear();
    }

    public enum Types {
        NONE, DRIVE_FRONT_LEFT, DRIVE_FRONT_RIGHT, DRIVE_BACK_LEFT, DRIVE_BACK_RIGHT,
        GYRO_PRIMARY
    }

    public static RobotMapper getInstance() {
        if (instance == null) {
            instance = new RobotMapper();
        }
        return instance;
    }

    private RobotMapper() {
        map = new HashMap<>();
    }

    public void set(HardwareDevice device, Types type) {
        if (map.containsKey(type))
            throw new RuntimeException("Type already set");
        map.put(type, device);
    }

    //TODO: More elegant method of combining imu and device; proxy, bridge, adapter?
    public void set(BNO055IMU imu, Types type) {
        if (imus.containsKey(type))
            throw new RuntimeException("Type already set");
        imus.put(type, imu);
    }

    public void setActiveOpMode(OpMode activeOpMode) {
        this.activeOpMode = activeOpMode;
    }

    public HardwareDevice get(Types type) {
        return map.get(type);
    }

    public <T extends HardwareDevice> T get(Types type, Class<T> clazz) {
        return (T) map.get(type);
    }
}
