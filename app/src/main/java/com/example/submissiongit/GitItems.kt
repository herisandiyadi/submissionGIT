package com.example.submissiongit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitItems(
    var username: String? ="",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var follower: String? = "",
    var following: String? = "",
    var repository: String? = ""
) : Parcelable
