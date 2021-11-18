package ishtiaq.codingchallenge.carsmaplist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ishtiaq.codingchallenge.carsmaplist.databinding.RowCarsItemBinding
import ishtiaq.codingchallenge.carsmaplist.model.Car

class CarAdapter(private val onItemClicked:(Car) -> Unit) : ListAdapter<Car, CarAdapter.TheViewHolder>(DiffCallback) {


    class TheViewHolder(private var binding: RowCarsItemBinding, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(car: Car) {
            binding.car = car
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.carId == newItem.carId
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.carImage == newItem.carImage
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheViewHolder {
        val viewHolder=RowCarsItemBinding.inflate(
            LayoutInflater.from(parent.context)
        )

        return TheViewHolder(viewHolder){
            getItem(it)?.let(onItemClicked)
        }
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        val car = getItem(position)
        holder.bind(car)
    }
}