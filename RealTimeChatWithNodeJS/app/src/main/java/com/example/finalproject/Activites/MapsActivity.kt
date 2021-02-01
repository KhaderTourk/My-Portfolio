package com.example.finalproject.Activites

import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var mLocation: Location? = null
    var lat = 0.0
    var lng = 0.0
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                mLocation = location
            }
            lat = mLocation!!.latitude
            lng = mLocation!!.longitude

            val gaza = LatLng(lat, lng)
            val resturant = LatLng(ComanionClass.lat.toDouble(), ComanionClass.long.toDouble())
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gaza, 14f))

            mMap.addMarker(MarkerOptions().position(gaza).title("My Location"))
            mMap.addMarker(MarkerOptions().position(resturant).title(ComanionClass.resturantName))

            mMap.addPolyline(PolylineOptions().add(gaza, resturant))
            Log.e("MapsActivity", "$lat $lng")
            if (mFusedLocationClient != null) {
                mFusedLocationClient!!.removeLocationUpdates(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationClient!!.requestLocationUpdates(
            getLocationRequest(),
            mLocationCallback,
            Looper.myLooper()
        )

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.isMyLocationEnabled = true

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,
            R.raw.style
        ))

        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Marker"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
            Toast.makeText(
                this,
                latLng.latitude.toString() + " " + latLng.longitude.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun getLocationRequest(): LocationRequest? {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 9000
        return locationRequest
    }

}
