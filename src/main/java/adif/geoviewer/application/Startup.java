package adif.geoviewer.application;

import adif.geoviewer.processor.Processor;
import adif.geoviewer.processor.Processor.AbortCallback;
import adif.geoviewer.processor.model.Parameters;
import adif.geoviewer.server.WebServer;

public class Startup {

    private SwingUi ui;
    public static void main(String[] args) {
        new Startup().run();
    }

    void run() {
        ui = new SwingUi();
        ui.setOnRunListener(this::processImages);
        setupServer();
        ui.show();
    }

    private void setupServer() {
        WebServer webServer = WebServer.builder()
        .port(8089)
        .staticResourceDir("dist")
        .build();
        ui.setServerControl(() -> {
            webServer.setGpxFile(ui.getServeFile());
            webServer.start();
        }, () -> webServer.stop());
    }

    void processImages(Parameters parameters) {
        AbortListener abortListener = new AbortListener();
        ui.setOnAbortListener(abortListener);

        Processor processor = new Processor();
        processor.setProgressCallback(ui::setProgress);
        processor.setAbortCallback(abortListener);

        new Thread(() -> processor.process(parameters)).start();
    }

    private static class AbortListener implements Runnable, AbortCallback {

        private volatile boolean abort;

        @Override
        public void run() {
            abort = true;
        }

        @Override
        public boolean shouldAbort() {
            return abort;
        }



    }
}
