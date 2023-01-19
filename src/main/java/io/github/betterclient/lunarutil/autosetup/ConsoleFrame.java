package io.github.betterclient.lunarutil.autosetup;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.PrintStream;

import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;

public class ConsoleFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextArea textArea = new JTextArea(15, 30);
    private Console taOutputStream = new Console(textArea);

    public ConsoleFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(ALWAYS_UPDATE);
        panel.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        System.setOut(new PrintStream(taOutputStream));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setSize(1000, 500);
        setVisible(true);
    }
}
