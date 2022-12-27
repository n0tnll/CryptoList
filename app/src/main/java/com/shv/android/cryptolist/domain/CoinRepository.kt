package com.shv.android.cryptolist.domain

import androidx.lifecycle.LiveData
import com.shv.android.cryptolist.data.model.CoinPriceInfo
import com.shv.android.cryptolist.domain.entities.CoinInfo

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>
}