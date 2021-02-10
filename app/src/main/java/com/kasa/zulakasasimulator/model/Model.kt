package com.kasa.zulakasasimulator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ZulaGameUsers(
        @SerializedName("zulaUserName")
        val zulaUserName: String?,
        @SerializedName("zulaUserMail")
        val zulaUserMail: String?,
        @SerializedName("zulaUserPassword")
        val zulaUserPassword: String?
)