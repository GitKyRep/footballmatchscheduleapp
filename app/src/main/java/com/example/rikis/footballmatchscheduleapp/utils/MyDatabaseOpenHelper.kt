package com.example.rikis.footballmatchscheduleapp.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.rikis.footballmatchscheduleapp.data.FavoritesEvent
import org.jetbrains.anko.db.*


class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoritesEvent.TABLE_FAVORITE, true,
                FavoritesEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoritesEvent.EVENT_ID to TEXT + UNIQUE,
                FavoritesEvent.HOME_TEAM to TEXT,
                FavoritesEvent.AWAY_TEAM to TEXT,
                FavoritesEvent.DATE_EVENT to TEXT,
                FavoritesEvent.HOME_SCORE to TEXT,
                FavoritesEvent.AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoritesEvent.TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)