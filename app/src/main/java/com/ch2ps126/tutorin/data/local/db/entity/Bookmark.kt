package com.ch2ps126.tutorin.data.local.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ch2ps126.tutorin.util.Converters
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize

data class Bookmark(
    @PrimaryKey
    var id: Int,
    var equipmentImage: String? = null,
    var name: String? = null,
    var description: String? = null,
    @TypeConverters(Converters::class)
    var targetMuscles: List<String?>? = null,
    var videoTutorialLink: String? = null,
    @TypeConverters(Converters::class)
    var tutorial: List<String?>? = null,
) : Parcelable