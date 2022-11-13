package com.yusril.storyapp.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.yusril.storyapp.R
import com.yusril.storyapp.data.Result
import com.yusril.storyapp.data.local.LoginPrefs
import com.yusril.storyapp.databinding.ActivityMapsBinding
import com.yusril.storyapp.ui.story.StoryViewModel
import com.yusril.storyapp.ui.story.ViewModelFactoryStory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
//    private val viewModel : MapsViewModel by viewModels()
    private val viewModel : StoryViewModel by viewModels(){
    ViewModelFactoryStory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        getLocationStory()
    }



    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }
    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocationStory(){
        val tokenPref = LoginPrefs(this).getToken()
        Log.d("getToken", tokenPref.toString())
//        viewModel.fetchResponseStory("Bearer $tokenPref")
        viewModel.mapStory("Bearer $tokenPref").observe(this){
            if (it != null){
                when(it){
                    is Result.Success ->{
                        val boundsBuilder = LatLngBounds.Builder()
                        it.data.listStory.forEach { story ->
                            val latLng = LatLng(story.lat!!, story.lon!!)
                            mMap.addMarker(MarkerOptions().position(latLng).title(story.name))
                            boundsBuilder.include(latLng)
                        }
                        val bounds: LatLngBounds = boundsBuilder.build()
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )
                    }

                    is Result.Loading->{

                    }

                    is Result.Error->{

                    }
                }
            }

        }
    }
}