package Encryption;

import javax.swing.*;
import java.util.List;

public class Executor extends SwingWorker {
    @Override
    protected Object doInBackground() throws Exception {
        setProgress(10);
        return null;
    }

    @Override
    protected void process(List chunks) {
        super.process(chunks);
    }
}
