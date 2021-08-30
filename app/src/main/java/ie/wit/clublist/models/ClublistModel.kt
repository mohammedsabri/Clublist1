package it.wit.clublist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClublistModel(var id: Long = 0,
                          var title: String = "",
                          var details: String = "",
                         var value : String = "",
                          var image: String = "", ) : Parcelable




