package adif.geoviewer.processor.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImageFiles {

    String title;
    List<ImageFile> images;
    List<ImageFile> incompleteImages;
    
}
