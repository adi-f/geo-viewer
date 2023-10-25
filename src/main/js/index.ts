import './leaflet/leaflet-service';
import { initMap, setWaypoints } from './leaflet/leaflet-service';
import { Waypoint } from './model/model';
import { readWaypoints } from './waypoints/waypoints-service';

initMap();

// const waypoints: Waypoint[] = [
//     {lat: 46.9465, lon: 7.4441, name: 'Bundeshaus'},
//     {lat: 46.6854, lon: 7.7471, name: 'Beatenbucht'},
//     {lat: 47.0035, lon: 7.9402, name: 'Napf'},
// ];
// setWaypoints(waypoints)


readWaypoints().then((waypoints: Waypoint[]) => {
    console.log(waypoints);
    setWaypoints(waypoints);
})
