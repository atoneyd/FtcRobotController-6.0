package org.firstinspires.ftc.teamcode.config;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class ConfigManager {

    private static ConfigManager instance;

    private AssetManager assetManager;

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private ConfigManager() {

    }

    public void bind(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public InputStream getFile(String name) {
        try {
            return assetManager.open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
