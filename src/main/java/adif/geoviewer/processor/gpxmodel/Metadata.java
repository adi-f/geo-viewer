package adif.geoviewer.processor.gpxmodel;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    @XmlElement(namespace = Gpx.GPX_NAMESPACE)
    String name;
}
