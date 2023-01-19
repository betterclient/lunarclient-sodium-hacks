package io.github.betterclient.lunarutil.autosetup;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        new ConsoleFrame();
        var f = args.length > 0 ? new File(args[0]) : selectFile();
        var jar = new JarFile(f);

        var ff = new File(f.getParent() + File.separator + "overrides" + File.separator + "Sodium_v1_19_2.jar");
        System.out.println(ff.getAbsolutePath());
        if(!ff.createNewFile()) {
            System.out.println("[ERROR] Already Installed");
            System.exit(0);
        }

        var outJar = new JarOutputStream(new FileOutputStream(ff));

        appendEntries(jar, outJar, (original, name) -> {
            if(!name.equals("fabric.mod.json"))
                return original;

            var text = new String(original);
            int startIndex = text.indexOf("\"client\": [");
            int endIndex = text.indexOf("],", startIndex);
            text = text.substring(0, endIndex) + ", \"io.github.betterclient.lunarutil.InjectedClientMod\"    \n" + text.substring(endIndex);
            text = text.replace("\"mixins\": [", "\"mixins\": [\n    \"injected.mixins.json\",");

            return text.getBytes();
        }, f);
        Thread.sleep(500);
        var fff = new JarFile(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()));

        appendEntries(fff, outJar, (original, name) -> {

            if(name.startsWith("io/github/betterclient/lunarutil/") || name.equals("injected.mixins.json"))
                return original;

            return null;
        }, new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()));

        fff.close();
        outJar.close();
        jar.close();
        System.out.println("Loading utility client done!");
        Thread.sleep(500);
        System.exit(0);
    }

    private static File selectFile() {
        var fd = new FileDialog(new Frame("penis"));
        fd.setVisible(true);
        fd.setAlwaysOnTop(true);
        fd.setFilenameFilter((file, s) -> s.equals("Sodium_v1_19_2.jar"));
        String str = fd.getFile();

        if (str == null) {
            System.exit(0);
            throw new RuntimeException("System.exit(0) returned, this shouldn't happen!");
        } else {
            return new File(fd.getDirectory() + str);
        }
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
