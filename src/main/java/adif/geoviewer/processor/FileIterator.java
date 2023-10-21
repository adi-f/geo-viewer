package adif.geoviewer.processor;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FileIterator implements Iterable<File>{
    private final Predicate<File> filter;
    private final List<File> directories;

    public static FileIterator createJpegIteratorForPaths(String pathsSeparatedByFileSeparator) {
        Predicate<File> jpgFilter = file -> {
            if(!file.isFile()) {
                return false;
            }
            String fileNameLowerCase = file.getName().toLowerCase();
            return fileNameLowerCase.endsWith(".jpg") || fileNameLowerCase.endsWith(".jpeg");
        };


        List<File> directories = Stream.of(pathsSeparatedByFileSeparator.split(File.pathSeparator))
        .map(fileName -> fileName.trim())
        .map(File::new)
        .collect(Collectors.toUnmodifiableList());

        return new FileIterator(jpgFilter, directories);
    }

    @Override
    public Iterator<File> iterator() {
        return directories.stream()
        .flatMap(directory -> Stream.of(directory.listFiles()))
        .filter(filter)
        .iterator();
    }
    
}
