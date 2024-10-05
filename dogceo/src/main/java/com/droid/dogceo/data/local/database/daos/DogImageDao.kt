package com.droid.dogceo.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.droid.dogceo.data.local.database.entities.DogImage

@Dao
interface DogImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: DogImage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<DogImage>)

    @Query("SELECT * FROM images LIMIT :count")
    suspend fun getImages(count: Int): List<DogImage>

    @Query("SELECT COUNT(*) FROM images")
    suspend fun getImageCount(): Int
}