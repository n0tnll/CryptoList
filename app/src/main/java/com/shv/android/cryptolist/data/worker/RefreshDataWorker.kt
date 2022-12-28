package com.shv.android.cryptolist.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.shv.android.cryptolist.data.database.AppDatabase
import com.shv.android.cryptolist.data.mapper.CoinMapper
import com.shv.android.cryptolist.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val coinInfoDAO = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = mapper.mapNamesListDtoToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
                val listCoinInfo = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = listCoinInfo.map {
                    mapper.mapInfoDtoToDbModel(it)
                }
                coinInfoDAO.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {
        const val REFRESH_DATA_WORKER = "refresh_data"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .build()
        }
    }
}