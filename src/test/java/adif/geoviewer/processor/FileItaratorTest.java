package adif.geoviewer.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class FileItaratorTest {

    @Test
    void testItarator() throws IOException{
        // praprare
        String paths = createSomeFiles();

        // test
        FileIterator iterator = FileIterator.createJpegIteratorForPaths(paths);
        
        List<String> result = StreamSupport.stream(iterator.spliterator(), false)
        .map(file -> file.getName())
        .sorted()
        .collect(Collectors.toUnmodifiableList());

        // verify
        assertThat(result, contains("file1.jpg", "file2.JpEg"));
    }
    

    private String createSomeFiles() throws IOException{
        File dir1 = Files.createTempDirectory("test1").toFile();
        File dir2 = Files.createTempDirectory("test1").toFile();

        new File(dir1, "file1.jpg").createNewFile();
        new File(dir1, "file2.png").createNewFile();

        new File(dir2, "file2.JpEg").createNewFile();
        new File(dir2, "dir2.jpeg").mkdir();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    FileUtils.deleteDirectory(dir1);
                    FileUtils.deleteDirectory(dir2);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return dir1.getAbsolutePath() + File.pathSeparatorChar + dir2.getAbsolutePath();
    }
}
