# Geo Viewer
Reads the geo tags of JPEG images, creates a GPX file and visualizes the positions on a map.

## WORK IN PROGRESS
Currently,
* you have to compile & run it in your IDE (run `adif.geoviewer.application.Startup`).
* you can process images to a GPX file (waypoints).
* you can visualize all points on a map (Web UI).
* the Web UI (map) must be build before starting the Java application (`npm run build`).

## Usage
 * Paste all file directory paths to look for JPEGs to _"Lookup paths"_.
   You can specify multiple paths separated by the path separator (`;` or `:`, OS depending)
 * Choose the GPX output file by _"Write to"_
 * Press _"Run"_
 * To visualize the GPX file, set the GPX file path in _"Serve GPX"_ and press _"Start Server"_.
   http://localhost:8089/ is opened automatically.