package ishtiaq.codingchallenge.carsmaplist.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ishtiaq.codingchallenge.carsmaplist.R
import ishtiaq.codingchallenge.carsmaplist.model.Car

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imageUrl: String?) {

    imageUrl?.let {
        val imageUri = it.toUri().buildUpon().scheme("https").build()
        imgView.load(imageUri) {
            placeholder(R.drawable.ic_twotone_directions_car_24)
            error(R.drawable.ic_twotone_directions_car_24)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Car>?
) {

    val adapter = recyclerView.adapter as CarAdapter
    adapter.submitList(data)
}
