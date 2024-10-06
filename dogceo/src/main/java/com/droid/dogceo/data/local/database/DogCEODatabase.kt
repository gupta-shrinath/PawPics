package com.droid.dogceo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droid.dogceo.data.local.database.daos.DogImageDao
import com.droid.dogceo.data.local.database.entities.DogImage

@Database(entities = [DogImage::class], version = 1)
internal abstract class DogCEODatabase : RoomDatabase() {
    abstract fun imageDao(): DogImageDao

    companion object {
        const val DATABASE_NAME = "dogceo"
    }
}
