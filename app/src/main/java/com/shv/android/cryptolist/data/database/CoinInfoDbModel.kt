package com.shv.android.cryptolist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.shv.android.cryptolist.data.network.ApiFactory.BASE_IMAGE_URL
import com.shv.android.cryptolist.utils.convertTimestampToTime

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String? = null,
    val price: Double? = null,
    val lastUpdate: Int? = null,
    val highDay: Double? = null,
    val lowDay: Double? = null,
    val lastMarket: String? = null,
    val imageUrl: String? = null
)