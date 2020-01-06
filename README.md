# google-maps-bug-demo

This sample demonstrates that calling [GoogleMap.moveCamera()](https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap.html#moveCamera(com.google.android.gms.maps.CameraUpdate)) to position the camera at a specific location, then immediately calling `moveCamera` again with the `CameraUpdate` returned by [CameraUpdateFactory.zoomOut()](https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory.html#zoomOut()) does not work as you might expect. In particular, the camera is positioned at "the centre of the world" with a very high zoom level, instead of behing positioned at the chosen location then zoomed out by 1.

In code, this means that the following snippet doesn't work as you might expect:

```
map.moveCamera(aCameraUpdateToASpecifiLocation)
map.moveCamera(CameraUpdateFactory.zoomOut())      // FAIL: positioned at "centre of the world"
```

The documentation for `GoogleMap.moveCamera` specifies:

> The move is instantaneous, and a subsequent getCameraPosition() will reflect the new position.

and indeed, if we "manually" zoom out, we will get the expected behaviour:

```
map.moveCamera(aCameraUpdateToASpecifiLocation)
map.moveCamera(CameraUpdateFactory.zoomTo(cameraPosition.zoom - 1))    // SUCCESS: positioned at specific location and zoomed out by 1
```

However it is undocumented whether this instantaneous move should be taken into account by the call to `CameraUpdateFactory.zoomOut()` and if this should work. One might argue this is a reasonable expectation.

## How to use this sample

1. Update the value of the `com.google.android.geo.API_KEY` element with your Google Maps API key in _AndroidManifest.xml_
1. Run the sample
1. Observe that the camera is not in the expected position

- Expected behaviour: the camera should be positioned so that coordinates LatLng(-37.82604, 145.00125000000003) and LatLng(-37.830449, 145.05401200000006) are visible, then zoomed out by 1
- Actual behaviour: the camera is positioned at "the centre of the world" with a very high zoom level