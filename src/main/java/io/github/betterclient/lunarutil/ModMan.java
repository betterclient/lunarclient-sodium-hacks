package io.github.betterclient.lunarutil;

import io.github.betterclient.lunarutil.mod.Module;

import java.util.ArrayList;
import java.util.List;

public class ModMan {
    public static List<Module> modules = new ArrayList<>();

    public static List<Module> getModules() {
        return modules;
    }

    public static Module getModule(String name) {
        return modules.stream().filter(module -> module.name.equalsIgnoreCase(name)).findFirst().orElseThrow();
    }
}
