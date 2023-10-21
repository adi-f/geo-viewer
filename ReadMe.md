# Geo Viewer
Reads the geo tags of JPEG images, creates a GPX file and visualizes the positions on a map.

## WORK IN PROGRESS
Currently,
* you have to compile & run it in your IDE (run `adif.geoviewer.application.Startup`)
* only the fist feature works: processing images to a GPX file (waypoints).
  Use any GPX visualizer to show the output

## Usage
 * Paste all file directory paths to look for JPEGs to _"Lookup paths"_.
   You can specify multiple paths separated by the path separator (`;` or `:`, OS depending)
 * Choose the GPX output file by _"Write to"_
 * Press _"Run"_