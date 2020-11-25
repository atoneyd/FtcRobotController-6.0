package org.firstinspires.ftc.teamcode.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Andrew on 3/25/2018.
 */
public class Bus {
    private static Bus instance;
    private String id;
    private Map<Class, Object> registered;

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus("main");
        }
        return instance;
    }

    public void reset() {
        instance = new Bus("main");
    }

    public static Bus of(String id) {
        return new Bus(id);
    }

    private Bus(String id) {
        this.id = id;
        registered = new ConcurrentHashMap<>();
    }

    public void fire(IEvent event) {
        for (Class clazz : registered.keySet()) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Subscriber.class)) {
                    method.setAccessible(true);
                    try {
                        if (method.getParameterTypes()[0].isInstance(event))
                            method.invoke(registered.get(clazz), event);
                    } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void register(Class clazz, Object instance) {
        if (!registered.keySet().contains(clazz)) registered.put(clazz, instance);
    }

    public void unregister(Class clazz) {
        if (registered.keySet().contains(clazz)) registered.remove(clazz);
    }

    public void unregisterAll() {
        registered.clear();
    }
}
