package adif.geoviewer.processor.gpxmodel;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(namespace = Gpx.GPX_NAMESPACE)
public class Gpx {
    static final String GPX_NAMESPACE = "http://www.topografix.com/GPX/1/1";
    @XmlElement(namespace = GPX_NAMESPACE)
    private Metadata metadata;

    @XmlElement(name = "wtp", namespace = GPX_NAMESPACE)
    private List<Waypoint> waypoints;
}