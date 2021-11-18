package ishtiaq.codingchallenge.carsmaplist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import ishtiaq.codingchallenge.carsmaplist.adapter.CarAdapter
import ishtiaq.codingchallenge.carsmaplist.databinding.ActivityMainBinding
import ishtiaq.codingchallenge.carsmaplist.datasource.getDatabase
import ishtiaq.codingchallenge.carsmaplist.model.Car
import ishtiaq.codingchallenge.carsmaplist.view.list.ApiStatus
import ishtiaq.codingchallenge.carsmaplist.view.list.CarListRepository
import ishtiaq.codingchallenge.carsmaplist.view.list.CarListViewModel

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding // after wrapping with <layout> make sure to build
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private val viewModel: CarListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // List
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Map
        mapFragment = binding.carmap.getFragment()
        mapFragment.getMapAsync(this)

        // Network observer
        viewModel.status.observe(this) { apiStatus ->
            onApiStatusReceived(apiStatus)
        }

        // Database Observer
        CarListRepository(getDatabase(application))
            .getCarsFromDB().observe(this) { cars ->
                populateMap(cars)
            }

        // Setting Adapter
        binding.rcvCars.adapter =
            CarAdapter { car -> locateOnMap(car) }


    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        /*googleMap.setOnMarkerClickListener {marker ->
            marker.showInfoWindow()
            true
        }*/
    }

    private fun onApiStatusReceived(apiStatus: ApiStatus?) {
        when (apiStatus) {
            ApiStatus.LOADING -> binding.pbLoading.visibility = View.VISIBLE
            ApiStatus.DONE -> binding.pbLoading.visibility = View.GONE

            ApiStatus.ERROR -> {
                binding.pbLoading.visibility = View.INVISIBLE
                Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show()
            }
            null -> Log.wtf(
                "WIN MILLION DOLLER!!!",
                "visit: https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions"
            )
        }
    }

    private fun populateMap(cars: List<Car>) {

        try {
            cars.first().also {
                googleMap.clear()
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.latitude,
                            it.longitude
                        ), 12f
                    )
                )
            }
        } catch (e: NoSuchElementException) {
            // Handle Error
        }

        cars.map {
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude, it.longitude))
                    .title("Car name: ${it.carName}")
//                  .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
            )
        }
    }

    private fun locateOnMap(car: Car) { // get the callback here from viewHolder onItemClicked exec
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(car.latitude, car.longitude),
                14f
            )
        )

    }

}