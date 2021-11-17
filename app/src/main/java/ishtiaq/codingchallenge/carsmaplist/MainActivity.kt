package ishtiaq.codingchallenge.carsmaplist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import ishtiaq.codingchallenge.carsmaplist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding // after wrapping with <layout> make sure to build

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.carmap.getFragment<SupportMapFragment>().getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style))

    }

}