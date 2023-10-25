import * as L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './leaflet.css';
import { Waypoint } from '../model/model';

const circleIcon = L.divIcon({className: 'cicon', iconSize: [8, 8]})


let map: L.Map;
export function initMap() {
    map = L.map('map');
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);
}

export function setWaypoints(waypoints: Waypoint[]){
    const markers: L.Marker[] = waypoints.map(waypoint => L.marker([waypoint.lat, waypoint.lon], {icon: circleIcon}).bindPopup(sanitizeHtml(waypoint.name + '<b>&gt;</b>')));
    const group = L.featureGroup(markers);
    map.fitBounds(group.getBounds());
    group.addTo(map);
}

function sanitizeHtml (text: string): string {
    // beware of home made security ;-)
    return text
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')

}