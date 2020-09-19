package com.example.madlevel4task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task1.models.Product
import kotlinx.android.synthetic.main.fragment_shopping_list.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ShoppingListFragment : Fragment() {

    private val products: ArrayList<Product> = arrayListOf(Product("some product", 2),
        Product("some product 2", 3),
        Product("some product 4", 4))
    private val productAdapter: ProductAdapter = ProductAdapter(products)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_products.layoutManager = LinearLayoutManager(
            this.requireContext(), RecyclerView.VERTICAL, false)
        rv_products.adapter = productAdapter
        val dvItem: DividerItemDecoration = DividerItemDecoration(rv_products.context,
        RecyclerView.VERTICAL)
        rv_products.addItemDecoration(dvItem)

    }
}