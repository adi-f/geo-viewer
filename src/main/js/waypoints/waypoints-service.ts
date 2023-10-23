import { Waypoint } from "../model/model";


export async function readWaypoints(): Promise<Waypoint[]> {
    const gpx = await redGpx();
    return gpxToWaypoints(gpx);
}

async function redGpx(): Promise<Document> {
    const response = await fetch('gpx');
    const xml = await response.text();
    return new DOMParser().parseFromString(xml, 'application/xml');
}

function gpxToWaypoints(gpx: Document): Waypoint[] {
    const gpxWaypoints: NodeList = gpx.querySelectorAll('gpx>wpt');
    return map(gpxWaypoints, toWaypoint);
}

function toWaypoint(gpxWpt: Element): Waypoint {
    const lat: number = parseFloat(gpxWpt.getAttribute('lat'));
    const lon: number = parseFloat(gpxWpt.getAttribute('lon'));
    const name = gpxWpt.querySelector('name')?.textContent || '???';
    return {
        lat,
        lon,
        name
    }
}

function map<E extends Node, T>(arrayLike: NodeListOf<E>, mapper: (element: E) => T): T[] {
    return Array.prototype.map.call(arrayLike, mapper);
}