package io.github.betterclient.lunarutil.autosetup;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        var logs = new File(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().concat(File.separator + "logs"+ System.currentTimeMillis() + ".txt"));
        if(!logs.createNewFile()) {
            if(!logs.delete()) System.exit(0);
            if(!logs.createNewFile()) System.exit(0);
        }
        new ConsoleFrame(new FileWriter(logs));

        var f = new File(System.getProperty("user.home") + "/.lunarclient/offline/multiver/Sodium_v1_19_2.jar");
        System.out.println("Found Sodium Jar" + f.getAbsolutePath());
        var jar = new JarFile(f);

        var ff = new File(f.getParent() + File.separator + "overrides" + File.separator + (f.getAbsolutePath().substring(f.getParent().length() + 1)));
        System.out.println("Creating Override: " + ff.getAbsolutePath());
        if(!ff.createNewFile()) {
            System.out.println("[ERROR] Already Installed");
            var result = JOptionPane.showConfirmDialog(null, "Overwrite the existing one?", "File already exists.", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION) {
                if(!ff.delete()) System.exit(0);
                if(!ff.createNewFile()) System.exit(0);
            } else {
                System.exit(0);
            }
        }

        var outJar = new JarOutputStream(new FileOutputStream(ff));
        AtomicBoolean modifiedFabric = new AtomicBoolean(false);
        appendEntries(jar, outJar, (original, name) -> {
            if(!name.equals("fabric.mod.json"))
                return original;
            modifiedFabric.set(true);
            var text = new String(original);
            int startIndex = text.indexOf("\"client\": [");
            int endIndex = text.indexOf("],", startIndex);
            text = text.substring(0, endIndex) + ", \"io.github.betterclient.lunarutil.InjectedClientMod\"    \n" + text.substring(endIndex);
            text = text.replace("\"mixins\": [", "\"mixins\": [\n    \"injected.mixins.json\",");

            return text.getBytes();
        }, f);

        if(!modifiedFabric.get()) {
            System.out.println("[ERROR] Isn't a fabric mod!?");
        }

        Thread.sleep(500);
        var fff = new JarFile(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
        System.out.println("Asserting Injection Data: " + fff.getName());

        appendEntries(fff, outJar, (original, name) -> original, new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()));

        fff.close();
        outJar.close();
        jar.close();
        System.out.println("Loading utility client done!");
        Thread.sleep(500);
        System.exit(0);
    }

    private static void appendEntries(JarFile from, JarOutputStream to, ByteEditor editor, File from1) throws Exception {
        var entriesOriginal = from.entries();
        while (entriesOriginal.hasMoreElements()) {
            var e = entriesOriginal.nextElement();

            var out = editor.edit(from.getInputStream(e).readAllBytes(), e.getName());

            if(out != null) {
                System.out.println("Mixing " + e.getName() + " to: " + from1.getName());
                to.putNextEntry(e);

                to.write(out);

                to.closeEntry();
            }
        }
    }
}
