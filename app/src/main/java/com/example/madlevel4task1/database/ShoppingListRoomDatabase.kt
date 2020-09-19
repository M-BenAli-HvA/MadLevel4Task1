package com.example.madlevel4task1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.madlevel4task1.dao.ProductDao
import com.example.madlevel4task1.models.Product


@Database(entities = [Product::class],  version = 1, exportSchema = false)
abstract class ShoppingListRoomDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private const val DATABASE_NAME = "SHOPPING_LIST_DATABASE"

        @Volatile
        private var shoppingLIstRoomDatabaseInstance: ShoppingListRoomDatabase? =
            null

        fun getDatabase(context: Context): ShoppingListRoomDatabase? {
            if(shoppingLIstRoomDatabaseInstance == null) {
                synchronized(ShoppingListRoomDatabase::class.java) {
                    if(shoppingLIstRoomDatabaseInstance == null) {
                     shoppingLIstRoomDatabaseInstance = Room.databaseBuilder(
                         context.applicationContext, ShoppingListRoomDatabase::class.java,
                         DATABASE_NAME).build()
                    }
                }
            }
            return shoppingLIstRoomDatabaseInstance
        }



    }



}