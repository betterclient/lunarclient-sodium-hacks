package io.github.betterclient.lunarutil.mod;

public class Module {
    public int key;
    public boolean enabled;
    public final String name;


    public Module(int key, String name) {
        this.key = key;
        this.name = name;
        this.enabled = false;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void toggle() {
        this.enabled = !this.enabled;

        if(this.enabled)
            onEnable();
        else
            onDisable();
    }
}
