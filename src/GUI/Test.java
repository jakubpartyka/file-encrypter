package GUI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class Test extends JPanel {

    private JButton startButton;
    private JTextArea taskOutput;

    public Test() {
        super(new BorderLayout());

        final JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5, 5, 5, 5));
        taskOutput.setEditable(false);

        // Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(ae -> {
            startButton.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Instances of javax.swing.SwingWorker are not reusuable, so
            // we create new instances as needed.
            final Task task = new Task();
            task.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent pce) {
                    if ("progress".equals(pce.getPropertyName())) {
                        int progress = (Integer) pce.getNewValue();
                        progressBar.setValue(progress);
                        taskOutput.append(String.format("Completed %d%% of task.\n", task.getProgress()));
                    }
                }
            });
            task.execute();
        });

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    /**
     * Create the GUI and show it. As with all GUI code, this must run on the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ProgressBarDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel progressBarPanel = new Test();
        frame.add(progressBarPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private class Task extends SwingWorker<Void, Void> {

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            // Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                // Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000)-15);
                } catch (InterruptedException ignore) {
                }
                // Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); // turn off the wait cursor
            taskOutput.append("Done!\n");
        }
    }
}
