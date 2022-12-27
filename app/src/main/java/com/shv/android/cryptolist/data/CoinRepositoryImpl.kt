package com.shv.android.cryptolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shv.android.cryptolist.data.database.AppDatabase
import com.shv.android.cryptolist.data.mapper.CoinMapper
import com.shv.android.cryptolist.data.network.ApiFactory
import com.shv.android.cryptolist.domain.CoinRepository
import com.shv.android.cryptolist.domain.entities.CoinInfo
import kotlinx.coroutines.delay

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val coinInfoDAO = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDAO.getPriceList()) {
            it.map { coinInfoDbModel ->
                mapper.mapDbModelToEntity(coinInfoDbModel)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDAO.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSyms = mapper.mapNamesListDtoToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
            val listCoinInfo = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            val dbModelList = listCoinInfo.map {
                mapper.mapInfoDtoToDbModel(it)
            }
            coinInfoDAO.insertPriceList(dbModelList)
            delay(10000)
        }
    }
}