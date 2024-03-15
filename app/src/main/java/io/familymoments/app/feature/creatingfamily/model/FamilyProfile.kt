package io.familymoments.app.feature.creatingfamily.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FamilyProfile(
    val name:String,
    val img:Bitmap?
):Parcelable
