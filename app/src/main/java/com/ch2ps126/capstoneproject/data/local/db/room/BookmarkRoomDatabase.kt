package com.ch2ps126.capstoneproject.data.local.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ch2ps126.capstoneproject.data.local.db.entity.Bookmark
import com.ch2ps126.capstoneproject.util.Converters

@Database(entities = [Bookmark::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BookmarkRoomDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkRoomDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): BookmarkRoomDatabase {
            if (INSTANCE == null) {
                synchronized(BookmarkRoomDatabase::class.java)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkRoomDatabase::class.java,
                        "bookmark_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as BookmarkRoomDatabase
        }
    }
}