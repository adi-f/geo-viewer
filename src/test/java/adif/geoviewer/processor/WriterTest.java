package adif.geoviewer.processor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
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

        try(InputStream is = new FileInputStream(output)) {
            String result = IOUtils.toString(is, StandardCharsets.UTF_8);
            System.out.println(result);

            assertThat(result, containsString("<gpx xmlns=\"http://www.topografix.com/GPX/1/1\">"));
            assertThat(result, containsString("<wtp lat=\"3.5\" lon=\"5.5\">"));
            assertThat(result, containsString("<wtp lat=\"3.6\" lon=\"5.6\"/>"));

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
