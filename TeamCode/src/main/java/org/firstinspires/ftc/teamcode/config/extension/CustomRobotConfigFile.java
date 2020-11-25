package org.firstinspires.ftc.teamcode.config.extension;

//import android.support.annotation.NonNull;

import com.qualcomm.ftccommon.configuration.RobotConfigFile;
import com.qualcomm.hardware.lynx.LynxUsbDevice;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.ConfigManager;
import org.firstinspires.ftc.teamcode.config.generate.XmlBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

public class CustomRobotConfigFile extends RobotConfigFile {
    private List<LynxUsbDevice> modules;
    private boolean isEmpty;
    private Telemetry telemetry;
    private String xml;

    public CustomRobotConfigFile(String name, List<LynxUsbDevice> modules, Telemetry telemetry) {
        super(name, 0);
        this.modules = modules;
        this.isEmpty = false;
        this.telemetry = telemetry;
        telemetry.addLine("Constructed CustomRobotConfigFile");
        telemetry.update();
    }

    public CustomRobotConfigFile() {
        super("none", 0);
        this.isEmpty = true;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public boolean containedIn(Collection<RobotConfigFile> configFiles) {
        return super.containedIn(configFiles);
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public void markClean() {
        super.markClean();
    }

    @Override
    public boolean isDirty() {
        return super.isDirty();
    }

    @Override
    public String getName() {
        telemetry.addLine("Getting name");
        telemetry.update();
        return super.getName();
    }

    @Override
    public File getFullPath() {
        telemetry.addLine("Getting full path");
        telemetry.update();
        return super.getFullPath();
    }

    @Override
    public int getResourceId() {
        telemetry.addLine("Getting resource id");
        telemetry.update();
        return super.getResourceId();
    }

    @Override
    public FileLocation getLocation() {
        return super.getLocation();
    }

    @Override
    protected XmlPullParser getXmlNone() {
        return getXml();
    }

    @Override
    protected XmlPullParser getXmlLocalStorage() {
        return getXml();
    }

    @Override
    protected XmlPullParser getXmlResource() {
        return getXml();
    }

    @Override
    public boolean isNoConfig() {
        return false;
    }

    @Override
    public XmlPullParser getXml() {
        telemetry.addLine("Getting XML from custom config");
        telemetry.update();
        XmlPullParser result = null;
        try {
            xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>\n" +
                    "<Robot type=\"FirstInspires-FTC\">";

            if (modules.size() > 0) {
                XmlBuilder xmlBuilder = new XmlBuilder();

                String jsonText;
                InputStream inputStream = ConfigManager.getInstance().getFile("configs/" + this.getName() + ".json");
                if (inputStream != null) {
                    try {
                        byte[] buffer = new byte[inputStream.available()];
                        inputStream.read(buffer);
                        inputStream.close();
                        jsonText = new String(buffer, "UTF-8");
                        JSONObject jsonObject = new JSONObject(jsonText);

                        int parentAddress = 1;
                        if (jsonObject.has("INFO")) {
                            JSONObject info = (JSONObject) jsonObject.get("INFO");
                            if (info.has("PARENT_ADDRESS")) {
                                parentAddress = info.getInt("PARENT_ADDRESS");
                            }
                        }
                        xmlBuilder.addLynxUsbDevice("Expansion Hub Portal 1", modules.get(0).getSerialNumber().toString(), parentAddress);

                        JSONArray controllers = jsonObject.getJSONArray("CONTROLLERS");
                        for(int i = 0; i < controllers.length(); i++) {
                            XmlBuilder secondaryBuilder = new XmlBuilder();

                            JSONObject controller = controllers.getJSONObject(i);

                            secondaryBuilder.addLynxModule("Expansion Hub " + controller.getInt("ADDRESS"), controller.getInt("ADDRESS"));

                            JSONArray devices = controller.getJSONArray("DEVICES");

                            for (int j = 0; j < devices.length(); j++) {
                                JSONObject o = devices.getJSONObject(j);

                                if (o.has("bus")) {
                                    secondaryBuilder.add(o.getString("type"), o.getString("name"), o.getInt("port"), o.getInt("bus"));
                                } else {
                                    secondaryBuilder.add(o.getString("type"), o.getString("name"), o.getInt("port"));
                                }
                            }
                            xmlBuilder.add(secondaryBuilder);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                xml += xmlBuilder.build();
            }
            xml += "</Robot>\n";
            StringReader stringReader = new StringReader(xml);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            result = factory.newPullParser();
            result.setInput(stringReader);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }

   // @Override
//    public @NonNull
//    String toString() {
//        getXml();
//        return xml;
//    }
}
