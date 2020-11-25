package org.firstinspires.ftc.teamcode.config.generate;

import com.qualcomm.hardware.lynx.LynxUsbDevice;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.extension.CustomRobotConfigFile;

import java.util.List;

public class ConfigGenerator {

    public static CustomRobotConfigFile generateConfig(String name, List<LynxUsbDevice> modules, Telemetry telemetry) {
        CustomRobotConfigFile config = new CustomRobotConfigFile(name, modules, telemetry);

        config.getXml();

        return config;
    }
}
