package com.example.carrecomendations.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carrecomendations.databinding.ItemFavoriteCarBinding
import com.example.carrecomendations.ui.home.Car

class FavoritesAdapter(
    private var carList: List<Car>,
    private val onItemClick: (Car) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val car = carList[position]
        holder.bind(car)
        holder.itemView.setOnClickListener {
            onItemClick(car)
        }
    }

    override fun getItemCount(): Int = carList.size

    fun updateData(newCarList: List<Car>) {
        carList = newCarList
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(private val binding: ItemFavoriteCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.ivFavCarImage.setImageResource(car.imageResId)
            binding.tvFavCarName.text = car.name
            binding.tvFavCarPrice.text = car.price
        }
    }
}
