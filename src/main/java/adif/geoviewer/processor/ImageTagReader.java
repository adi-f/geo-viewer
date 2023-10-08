package adif.geoviewer.processor;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata.GPSInfo;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;

import adif.geoviewer.processor.model.ImageFile;


public class ImageTagReader {

    private static DateTimeFormatter EXIF_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    ImageFile readTags(File imageFile) {
        ImageFile.ImageFileBuilder imageFileBuilder = ImageFile.builder()
            .name(imageFile.getName());

        try {
            ImageMetadata metadata = Imaging.getMetadata(imageFile);

            if(!(metadata instanceof JpegImageMetadata)) {
                return imageFileBuilder.build();
            }
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            
            jpegMetadata.dump();
            String dateTaken = jpegMetadata.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL).getStringValue();
            if(dateTaken != null) {
                imageFileBuilder.timestamp(LocalDateTime.parse(dateTaken, EXIF_DATE_TIME_FORMAT));
            }

            TiffImageMetadata exif = jpegMetadata.getExif();
            if (exif != null) {
                GPSInfo gpsInfo = exif.getGPS();
            
                if (gpsInfo != null) {
                    imageFileBuilder.longitude(gpsInfo.getLongitudeAsDegreesEast());
                    imageFileBuilder.latitude(gpsInfo.getLatitudeAsDegreesNorth());
                }
            }
        }
        catch(Exception e) {
            imageFileBuilder.exception(e);
        }
        return imageFileBuilder.build();
    }
    
}
