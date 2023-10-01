package adif.geoviewer.processor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import adif.geoviewer.processor.gpxmodel.Gpx;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class Writer {

    private static Marshaller gpxWriter;
    
    public void write(Gpx gpx, File file) {

        ensureGpxWriterInitialized();

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            gpxWriter.marshal(gpx, out);
            out.flush();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureGpxWriterInitialized() {
        if(gpxWriter == null) {
            try {
                gpxWriter = JAXBContext.newInstance(Gpx.class).createMarshaller();
                gpxWriter.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
