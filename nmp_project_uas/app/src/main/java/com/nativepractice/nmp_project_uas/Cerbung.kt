package com.nativepractice.nmp_project_uas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cerbung( val id:Int,
                    val judul:String,
                    val description:String,
                    val num_likes:Int,
                    val user_id:Int,
                    val image_url:String,
                    val genre_id:Int,
                    val paragraf:String,
                    val username:String):Parcelable{

                    }
