package org.firstinspires.ftc.teamcode.config.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qualcomm.ftccommon.CommandList;
import com.qualcomm.ftccommon.configuration.RobotConfigFile;
import com.qualcomm.ftccommon.configuration.RobotConfigFileManager;
import com.qualcomm.hardware.lynx.LynxUsbDevice;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.configuration.WriteXMLFileHandler;
import com.qualcomm.robotcore.robocol.Command;

import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Bootstrapper;
import org.firstinspires.ftc.teamcode.config.extension.CustomRobotConfigFile;
import org.firstinspires.ftc.teamcode.config.generate.ConfigGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Autonomous
public class GenerateConfig extends LinearOpMode {
    private RobotConfigFileManager robotConfigFileManager;

    @Override
    public void runOpMode() throws InterruptedException {
        Bootstrapper.bootstrap(this);

        robotConfigFileManager = new RobotConfigFileManager(AppUtil.getInstance().getActivity());

        telemetry.setAutoClear(false);

        waitForStart();

        // reset
        //robotConfigFileManager.setActiveConfigAndUpdateUI(false, new CustomRobotConfigFile());

        // Debug start
        //TODO: Check if the rev expansion hub is the only LynxUsbDevice connected to the robot controller
        List<LynxUsbDevice> modules = this.hardwareMap.getAll(LynxUsbDevice.class); // RobotUsbModule

        telemetry.addData("Modules found", modules.size());
        for (int i = 0; i < modules.size(); i++) {
            telemetry.addData("" + i, modules.get(i).toString());
        }
        telemetry.update();

        telemetry.addLine("Creating config...");
        telemetry.update();
        // Debug end

        long start = System.currentTimeMillis();

        //TODO: If this works -> bootstrap
        CustomRobotConfigFile file = ConfigGenerator.generateConfig("default", modules, telemetry);

        telemetry.addLine("Setting config...");
        telemetry.update();

        Context context = AppUtil.getInstance().getActivity().getApplication();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        File configFolder = AppUtil.CONFIG_FILES_DIR;

        //WriteXMLFileHandler writer = new WriteXMLFileHandler(context);

        File target = new File(configFolder.getPath(), file.getName() + ".xml");

        if (target.exists()) {
            target.delete();
        }

//        try {
//            writer.writeToFile(file.toString(), configFolder, file.getName() + ".xml");
//        } catch (RobotCoreException | IOException e) {
//            e.printStackTrace();
//        }

        RobotConfigFile cfgFile = new RobotConfigFile(robotConfigFileManager, file.getName());
        robotConfigFileManager.setActiveConfigAndUpdateUI(false, cfgFile);

        NetworkConnectionHandler connectionHandler = NetworkConnectionHandler.getInstance();
        connectionHandler.injectReceivedCommand(new Command(CommandList.CMD_RESTART_ROBOT));

        telemetry.addLine(String.format("Finished config creation in %dms", (System.currentTimeMillis() - start)));
        telemetry.addLine("Robot will restart. Do not stop the program");
        telemetry.update();

        while (opModeIsActive());
    }
}
