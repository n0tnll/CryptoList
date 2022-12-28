package com.shv.android.cryptolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.shv.android.cryptolist.data.database.AppDatabase
import com.shv.android.cryptolist.data.mapper.CoinMapper
import com.shv.android.cryptolist.data.worker.RefreshDataWorker
import com.shv.android.cryptolist.domain.CoinRepository
import com.shv.android.cryptolist.domain.entities.CoinInfo

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val coinInfoDAO = AppDatabase.getInstance(application).coinPriceInfoDao()
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

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.REFRESH_DATA_WORKER,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}