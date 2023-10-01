package adif.geoviewer.processor.gpxmodel;

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
}
