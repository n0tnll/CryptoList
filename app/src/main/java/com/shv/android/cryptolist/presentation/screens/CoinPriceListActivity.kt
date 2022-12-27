package com.shv.android.cryptolist.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shv.android.cryptolist.databinding.ActivityCointPriceListBinding
import com.shv.android.cryptolist.domain.entities.CoinInfo
import com.shv.android.cryptolist.presentation.CoinViewModel
import com.shv.android.cryptolist.presentation.adapters.CoinInfoAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    private lateinit var binding: ActivityCointPriceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCointPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                val intent =
                    CoinDetailActivity.newIntent(this@MainActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }

        }
        binding.recyclerViewCoinPriceList.adapter = adapter

        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }
}