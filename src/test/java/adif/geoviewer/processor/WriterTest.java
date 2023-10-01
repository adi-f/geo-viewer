package adif.geoviewer.processor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import adif.geoviewer.processor.gpxmodel.Gpx;
import adif.geoviewer.processor.gpxmodel.Metadata;
import adif.geoviewer.processor.gpxmodel.Waypoint;

public class WriterTest {

    private Writer writer = new Writer();

    @Test
    void testWrite() throws IOException {
        File output = File.createTempFile("Gpx", ".gpx");
        output.deleteOnExit();

        writer.write(createGpx(), output);

        try(Scanner scanner = new Scanner(output)) {
            while(scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } 
    }

    private Gpx createGpx() {
        return Gpx.builder()
        .metadata(Metadata.builder().name("Test-Gpx").build())
        .waypoints(List.of(
            Waypoint.builder().lat("3.5").lon("5.5").name("DSC123.jpg").time("2020-05-05T15:15:15Z").build(),
            Waypoint.builder().lat("3.6").lon("5.6").build()
        ))
        .build();
    }
    
}
