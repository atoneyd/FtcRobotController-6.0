package org.firstinspires.ftc.teamcode.config.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XmlBuilder {

    private String xml;
    private List<String> closure;

    public XmlBuilder() {
        this.xml = "";
        this.closure = new ArrayList<>();
    }

    public XmlBuilder addLynxUsbDevice(String name, String serialNumber, int parentModuleAddress) {
        this.xml += String.format("<LynxUsbDevice name=\"%s\" serialNumber=\"%s\" parentModuleAddress=\"%d\">\n", name, serialNumber, parentModuleAddress);
        this.closure.add("</LynxUsbDevice>\n");
        return this;
    }

    public XmlBuilder addLynxModule(String name, int port) {
        this.xml += String.format("<LynxModule name=\"%s\" port=\"%d\">\n", name, port);
        this.closure.add("</LynxModule>\n");
        return this;
    }

    public XmlBuilder add(String type, String name, int port) {
        this.xml += String.format("<%s name=\"%s\" port=\"%d\"/>\n", type, name, port);
        return this;
    }

    public XmlBuilder add(String type, String name, int port, int bus) {
        this.xml += String.format("<%s name=\"%s\" port=\"%d\" bus=\"%d\"/>\n", type, name, port, bus);
        return this;
    }

    public String build() {
        Collections.reverse(closure);
        for (String s : closure) {
            xml += s;
        }
        return xml;
    }

    public void add(XmlBuilder secondaryBuilder) {
        xml += secondaryBuilder.build();
    }
}
