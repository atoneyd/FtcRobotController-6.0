package org.firstinspires.ftc.teamcode.submodule;

import org.firstinspires.ftc.teamcode.submodule.interfaces.ISubmodule;

import java.util.HashMap;
import java.util.Map;

public class SubmoduleManager {
    private Map<Class<? extends ISubmodule>, ISubmodule> submodules;

    public SubmoduleManager() {
        this.submodules = new HashMap<>();
    }

    public void add(ISubmodule submodule) {
        this.submodules.put(submodule.getClass(), submodule);
    }

    public <T extends ISubmodule> T getSubmodule(Class<T> clazz) {
        return clazz.cast(submodules.get(clazz));
    }
}
