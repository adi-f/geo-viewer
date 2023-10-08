package adif.geoviewer.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adif.geoviewer.processor.gpxmodel.Gpx;
import adif.geoviewer.processor.model.ImageFile;
import adif.geoviewer.processor.model.ImageFiles;
import adif.geoviewer.processor.model.Parameters;

public class Processor {
    ImageTagReader imageTagReader = new ImageTagReader();
    Writer writer = new Writer();

    public void process(Parameters parameters) {
        ImageFiles imageFiles = read(parameters.getPathsSeparatedByFileSeparator());
        log(imageFiles);
        write(imageFiles, parameters.getOutputGpxFile());
    }

    private ImageFiles read(String pathsSeparatedByFileSeparator) {

        FileIterator fileIterator = FileIterator.createJpegIteratorForPaths(pathsSeparatedByFileSeparator);

        List<ImageFile> images = new ArrayList<>();
        List<ImageFile> incompleteImages = new ArrayList<>();

        for(File file : fileIterator) {
            ImageFile image = imageTagReader.readTags(file);
            if(image.isComplete()) {
                images.add(image);
            } else {
                incompleteImages.add(image);
            }
        }
        images.sort(Comparator.comparing(ImageFile::getTimestamp));
        incompleteImages.sort(Comparator.comparing(ImageFile::getName));

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

}
