package adif.geoviewer.server;

import java.awt.Desktop;
import java.net.URI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

import lombok.Builder;

@Builder
public class WebServer {
    private int port;
    private String staticResourceDir;
    private String gpxFile;

    private Server server;

    public void start() {

        if(server != null) {
            throw new IllegalStateException("Server already running");
        }

         server = new Server(port);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(staticResourceDir);

        server.setHandler(resourceHandler);
        try {
            server.start();
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI("http:localhost:" + port));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }        
    }

    public void stop() {
        if(server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            server = null;
        }
    }
}
