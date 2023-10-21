import './leaflet/leaflet-service';
import { readWaypoints } from './waypoints/waypoints-service';

const text: string = 'Webpack works' + ' + TypeScript';
document.querySelector('h1').innerText = text;

readWaypoints().then(wpts => console.log(wpts))
