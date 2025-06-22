package com.example.carrecomendations.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carrecomendations.databinding.ItemCarBinding

class CarAdapter(private val carList: List<Car>, private val onClick: (Car) -> Unit) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.bind(car)
        holder.itemView.setOnClickListener { onClick(car) }
    }

    override fun getItemCount(): Int = carList.size

    class CarViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.ivCarItemImage.setImageResource(car.imageResId)
            binding.tvCarItemName.text = car.name
        }
    }
}
