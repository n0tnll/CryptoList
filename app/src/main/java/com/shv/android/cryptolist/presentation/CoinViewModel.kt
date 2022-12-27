package com.shv.android.cryptolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shv.android.cryptolist.data.CoinRepositoryImpl
import com.shv.android.cryptolist.domain.usecases.GetCoinInfoListUseCase
import com.shv.android.cryptolist.domain.usecases.GetCoinInfoUseCase
import com.shv.android.cryptolist.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch

private const val TAG = "CoinViewModel"

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

}