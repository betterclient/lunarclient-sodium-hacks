package io.github.betterclient.lunarutil.autosetup;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class Console extends OutputStream {
    private final JTextArea textArea;
    private final StringBuilder sb = new StringBuilder();

    public Console(final JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    @Override
    public void write(int b) throws IOException {

        if (b == '\r')
            return;

        if (b == '\n') {
            final String text = sb.toString() + "\n";
            textArea.append(text);
            sb.setLength(0);
            return;
        }

        sb.append((char) b);
    }
}
