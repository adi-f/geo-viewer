package adif.geoviewer.processor;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import adif.geoviewer.processor.model.ImageFile;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class ImageTagReaderTest {

    ImageTagReader reader = new ImageTagReader();

    @Test
    void testReadTags() throws URISyntaxException {
        // prepare
        URL imageUrl = this.getClass().getClassLoader().getResource("lysefjord_20230811_130342.jpg");
        File imageFile = new File(imageUrl.toURI());

        //  test
        ImageFile result = reader.readTags(imageFile);

        // verify
        assertThat(result, equalTo(ImageFile.builder()
            .name("lysefjord_20230811_130342.jpg")
            .build()
        ));
    }
    
}
