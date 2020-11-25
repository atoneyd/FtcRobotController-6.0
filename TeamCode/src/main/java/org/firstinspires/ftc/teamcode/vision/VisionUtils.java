package org.firstinspires.ftc.teamcode.vision;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public final class VisionUtils {

    public static void initStandard(String VUFORIA_KEY, VuforiaLocalizer vuforia, TFObjectDetector tfod, String TFOD_MODEL_ASSET, String LABEL_GOLD_MINERAL, String LABEL_SILVER_MINERAL) {
        initVuforia(VUFORIA_KEY, vuforia);

//        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
//            VisionUtils.initTfod(vuforia, tfod, TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
//        }
    }

    public static void initVuforia(String VUFORIA_KEY, VuforiaLocalizer vuforia) {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    public static void initTfod(VuforiaLocalizer vuforia, TFObjectDetector tfod, String TFOD_MODEL_ASSET, String LABEL_GOLD_MINERAL, String LABEL_SILVER_MINERAL) {
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters();
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
