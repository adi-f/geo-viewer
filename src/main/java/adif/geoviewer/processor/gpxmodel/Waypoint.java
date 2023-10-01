package adif.geoviewer.processor.gpxmodel;

import adif.geoviewer.processor.model.ImageFile;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {
    @XmlAttribute
    String lat;
    @XmlAttribute
    String lon;

    @XmlElement(namespace = Gpx.GPX_NAMESPACE)
    String time;
    @XmlElement(namespace = Gpx.GPX_NAMESPACE)
    String name;

    public static Waypoint from(ImageFile imageFile) {
        return Waypoint.builder()
            .lat(Double.toString(imageFile.getLongitude()))
            .lon(Double.toString(imageFile.getLongitude()))
            .time(String.valueOf(imageFile.getTimestamp()))
            .name(imageFile.getName())
            .build();
    }
}
