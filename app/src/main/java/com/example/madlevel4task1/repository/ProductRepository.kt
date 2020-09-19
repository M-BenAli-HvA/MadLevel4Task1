package com.example.madlevel4task1.repository

import android.content.Context
import com.example.madlevel4task1.dao.ProductDao
import com.example.madlevel4task1.database.ShoppingListRoomDatabase
import com.example.madlevel4task1.models.Product

class ProductRepository(context: Context) {

    private val productDao: ProductDao

    init {
        val database = ShoppingListRoomDatabase.getDatabase(context)
        productDao = database!!.productDao()
    }

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    suspend fun insertProduct(product: Product) {
        return productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        return productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        return productDao.deleteProduct(product)
    }

    suspend fun deleteAllProducts() {
        return productDao.deleteAllProducts()
    }

}