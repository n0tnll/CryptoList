package com.shv.android.cryptolist.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shv.android.cryptolist.R
import com.shv.android.cryptolist.domain.entities.CoinInfo
import com.shv.android.cryptolist.presentation.CoinViewModel
import com.shv.android.cryptolist.presentation.adapters.CoinInfoAdapter
import kotlinx.android.synthetic.main.activity_coint_price_list.*

private const val TAG = "MainActivityV"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coint_price_list)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(this@MainActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }

        }
        recyclerViewCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }
}