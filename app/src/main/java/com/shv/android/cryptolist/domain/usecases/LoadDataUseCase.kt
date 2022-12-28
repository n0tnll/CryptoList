package com.shv.android.cryptolist.domain.usecases

import com.shv.android.cryptolist.domain.CoinRepository

class LoadDataUseCase(private val repository: CoinRepository) {

    operator fun invoke() {
        repository.loadData()
    }
}