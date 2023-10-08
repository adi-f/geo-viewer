package adif.geoviewer.processor.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Parameters {
    String pathsSeparatedByFileSeparator;
    String outputGpxFile;
}
