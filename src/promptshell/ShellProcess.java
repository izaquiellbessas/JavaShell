/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package promptshell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author Izaquiel Bessas
 */
public class ShellProcess implements ActionListener {

    private final JFrame frame;
    private final JTextArea txtArea;
    private final JScrollPane scroll;
    private final String cmd;
    private final int delay;
    private Timer timer;

    public ShellProcess(JFrame frame, JTextArea txtArea, JScrollPane scroll, String cmd, int delay) {
        this.frame = frame;
        this.txtArea = txtArea;
        this.scroll = scroll;
        this.cmd = cmd;
        this.delay = delay;
    }

    public void init() {
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Process processo = Runtime.getRuntime().exec(this.cmd);

            try (InputStream is = processo.getInputStream()) {
                int v;
                StringBuilder sb = new StringBuilder();
                while ((v = is.read()) != -1) {
                    sb.append((char) v);

                    if (((char) v) == '\n') {
                        txtArea.append(sb.toString());
                        System.out.print(sb.toString());
                        
                        txtArea.repaint();
                        txtArea.updateUI();
                        txtArea.update(txtArea.getGraphics());
                        
                        scroll.repaint();
                        scroll.updateUI();
                        scroll.update(scroll.getGraphics());
                        
                        frame.repaint();
                        frame.update(frame.getGraphics());
                        
                        sb.delete(0, sb.length());
                    }
                }
                timer.stop();
            }
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
