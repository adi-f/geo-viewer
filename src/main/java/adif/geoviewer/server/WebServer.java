package adif.geoviewer.server;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Builder
public class WebServer {
    private int port;
    private String staticResourceDir;
    @Setter
    private String gpxFile;

    private Server server;

    public void start() {

        if(server != null) {
            throw new IllegalStateException("Server already running");
        }

         server = new Server(port);

        SingleFileHandler singleFileHandler = new SingleFileHandler("/gpx");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(staticResourceDir);

        server.setHandler(new HandlerList(singleFileHandler, resourceHandler));
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

    @AllArgsConstructor
    private class SingleFileHandler extends ResourceHandler {
        private String expectedUrl;

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            if(expectedUrl.equals(baseRequest.getPathInContext())) {
                super.handle(target, baseRequest, request, response);
            }
        }

        @Override
        public Resource getResource(String path) throws IOException {
            return new PathResource(new File(gpxFile));
        }

    }
}
