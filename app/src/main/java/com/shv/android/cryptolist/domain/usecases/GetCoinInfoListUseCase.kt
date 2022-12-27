package com.shv.android.cryptolist.domain.usecases

import androidx.lifecycle.LiveData
import com.shv.android.cryptolist.domain.CoinRepository
import com.shv.android.cryptolist.domain.entities.CoinInfo

class GetCoinInfoListUseCase(private val repository: CoinRepository) {

    operator fun invoke(): LiveData<List<CoinInfo>> {
        return repository.getCoinInfoList()
    }
}