package com.example.googlemapsbugdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.mapView

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync { map ->
            if (mapView.hasDimensions) {
                map.setUp()
            } else {
                mapView.doOnNextLayout {
                    map.setUp()
                }
            }

        }
    }

    private val View.hasDimensions: Boolean
        get() = width > 0 && height > 0

    private fun GoogleMap.setUp() {
        val locations = arrayOf(
            LatLng(-37.82604, 145.00125000000003),
            LatLng(-37.830449, 145.05401200000006)
        )

        moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                LatLngBounds.builder().apply {
                    locations.forEach {
                        include(it)
                    }
                }.build(), 0
            )
        )

        moveCamera(CameraUpdateFactory.zoomOut())

        // Manually zooming out using the updated camera position works
        // moveCamera(CameraUpdateFactory.zoomTo(cameraPosition.zoom - 1))

        locations.forEach {
            addMarker(MarkerOptions().position(it))
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        mapView.onDestroy()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
