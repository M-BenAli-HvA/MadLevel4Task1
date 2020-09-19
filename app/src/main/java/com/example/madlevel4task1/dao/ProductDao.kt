package com.example.madlevel4task1.dao

import androidx.room.*
import com.example.madlevel4task1.models.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<Product>

    @Insert
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()
}