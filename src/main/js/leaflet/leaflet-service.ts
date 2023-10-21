import * as L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './leaflet.css';


const map: L.Map = L.map('map').setView([46.9465, 7.4441], 13);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

L.marker([46.9465, 7.4441])
    .addTo(map)
    .bindPopup('Bundeshaus Bern')
    .openPopup();