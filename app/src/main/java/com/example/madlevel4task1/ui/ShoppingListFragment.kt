package com.example.madlevel4task1.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task1.R
import com.example.madlevel4task1.models.Product
import com.example.madlevel4task1.repository.ProductRepository
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ShoppingListFragment : Fragment() {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var productRepository: ProductRepository

    private var products: ArrayList<Product> = arrayListOf()
    private val productAdapter: ProductAdapter =
        ProductAdapter(products)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productRepository = ProductRepository(requireContext())

        initRv()

        add_fab.setOnClickListener {
            showAddProductDialog()
        }

        del_fab.setOnClickListener {
            removeAllProducts()
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val productToDelete = products[position]

                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        productRepository.deleteProduct(productToDelete)
                    }
                    getShoppingListFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }


    private fun initRv() {
        rv_products.layoutManager = LinearLayoutManager(
            this.requireContext(), RecyclerView.VERTICAL, false
        )
        rv_products.adapter = productAdapter
        val dvItem: DividerItemDecoration = DividerItemDecoration(
            rv_products.context,
            RecyclerView.VERTICAL
        )
        rv_products.addItemDecoration(dvItem)
        createItemTouchHelper().attachToRecyclerView(rv_products)
        getShoppingListFromDatabase()
    }

    private fun getShoppingListFromDatabase() {
        mainScope.launch {
            val products = withContext(Dispatchers.IO) {
                productRepository.getAllProducts()
            }
            this@ShoppingListFragment.products.clear()
            this@ShoppingListFragment.products.addAll(products)
            this@ShoppingListFragment.productAdapter.notifyDataSetChanged()
        }
    }

    private fun showAddProductDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.android_add_dialog_title))
        val dialogLayout = layoutInflater.inflate(R.layout.add_product_dialog, null)
        val productName = dialogLayout.findViewById<EditText>(R.id.et_product_name)
        val productAmount = dialogLayout.findViewById<EditText>(R.id.et_product_amount)

        builder.setView(dialogLayout)
        builder.setPositiveButton(R.string.dialog_ok_btn) { _:
                                                            DialogInterface, _: Int ->
            addProduct(productName, productAmount)
        }
        builder.show()
    }

    private fun addProduct(productName: EditText, productAmount: EditText) {
        if (validateFields(productName, productAmount)) {
            mainScope.launch {
                val product = Product(
                    productName.text.toString(),
                    productAmount.text.toString().toInt()
                )

                withContext(Dispatchers.IO) {
                    productRepository.insertProduct(product)
                }

                getShoppingListFromDatabase()
            }
        }

    }

    private fun removeAllProducts() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                productRepository.deleteAllProducts()
            }
            getShoppingListFromDatabase()
        }
    }

    private fun validateFields(productName: EditText, productAmount: EditText): Boolean {
        return if (productName.text.toString().isNotBlank() &&
            productAmount.text.toString().isNotBlank()
        ) {
            true
        } else {
            Toast.makeText(activity, "Please fill in the fields", Toast.LENGTH_LONG).show()
            false
        }
    }


}