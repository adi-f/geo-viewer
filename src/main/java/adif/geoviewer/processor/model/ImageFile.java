package adif.geoviewer.processor.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Value;
import lombok.Builder.Default;

@Value
@Builder
public class ImageFile {
    String name;
    @Default
    double latitude = Double.MIN_VALUE;
    @Default
    double longitude = Double.MIN_VALUE;
    LocalDateTime timestamp;
    Exception exception;

    public boolean isComplete() {
        return name != null
        && latitude != Double.MIN_VALUE
        && longitude != Double.MIN_VALUE
        && timestamp != null
        && exception == null;
    }
}
