package com.shv.android.cryptolist.domain.usecases

import androidx.lifecycle.LiveData
import com.shv.android.cryptolist.domain.CoinRepository
import com.shv.android.cryptolist.domain.entities.CoinInfo

class GetCoinInfoUseCase(private val repository: CoinRepository) {

    operator fun invoke(fromSymbol: String): LiveData<CoinInfo> {
        return repository.getCoinInfo(fromSymbol)
    }
}