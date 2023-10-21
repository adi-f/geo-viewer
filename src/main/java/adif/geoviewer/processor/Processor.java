package adif.geoviewer.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;

import adif.geoviewer.processor.gpxmodel.Gpx;
import adif.geoviewer.processor.model.ImageFile;
import adif.geoviewer.processor.model.ImageFiles;
import adif.geoviewer.processor.model.Parameters;

public class Processor {
    ImageTagReader imageTagReader = new ImageTagReader();
    Writer writer = new Writer();
    private ProgressCallback progressCallback;
    private AbortCallback abortCallback;

    public void process(Parameters parameters) {
        ImageFiles imageFiles = read(parameters.getPathsSeparatedByFileSeparator());
        log(imageFiles);
        write(imageFiles, parameters.getOutputGpxFile());
        progressCallback.updateProgress(100,100);
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    public void setAbortCallback(AbortCallback abortCallback) {
        this.abortCallback = abortCallback;
    }

    private ImageFiles read(String pathsSeparatedByFileSeparator) {

        FileIterator fileIterator = FileIterator.createJpegIteratorForPaths(pathsSeparatedByFileSeparator);
        List<File> jpgFiles = IteratorUtils.toList(fileIterator.iterator());

        List<ImageFile> images = new ArrayList<>();
        List<ImageFile> incompleteImages = new ArrayList<>();

        for(int i = 0; i < jpgFiles.size() && !abortCallback.shouldAbort(); i++) {
            ImageFile image = imageTagReader.readTags(jpgFiles.get(i));
            if(image.isComplete()) {
                images.add(image);
            } else {
                incompleteImages.add(image);
            }
            progressCallback.updateProgress(i+1, jpgFiles.size());
        }

        progressCallback.updateProgress(0,100);
        images.sort(Comparator.comparing(ImageFile::getTimestamp));
        progressCallback.updateProgress(3,100);
        incompleteImages.sort(Comparator.comparing(ImageFile::getName));
        progressCallback.updateProgress(5,100);

        return ImageFiles.builder()
        .title("Image Geolocations")
        .images(Collections.unmodifiableList(images))
        .incompleteImages(Collections.unmodifiableList(incompleteImages))
        .build();
    }

    private void log(ImageFiles imageFiles) {
        System.out.println(imageFiles.getImages().size() + " processed successfully");
        System.out.println(imageFiles.getIncompleteImages().size() + " have missing attributes");
    }

    private void write(ImageFiles imageFiles, String outputGpxFile) {
        Gpx gpx = Gpx.from(imageFiles);
        writer.write(gpx, new File(outputGpxFile));
    }

    @FunctionalInterface
    public interface ProgressCallback {
        void updateProgress(int current, int max);
    }

    @FunctionalInterface
    public interface AbortCallback {
        boolean shouldAbort();
    }

}
