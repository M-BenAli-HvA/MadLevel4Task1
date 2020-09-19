package com.example.madlevel4task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task1.models.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private val products: List<Product>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun databind(product: Product) {
            itemView.tv_product_name.text = product.name
            itemView.tv_product_amount.text = product.amount.toString().plus("X")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(products[position])
    }


}