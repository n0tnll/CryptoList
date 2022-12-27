package com.shv.android.cryptolist.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shv.android.cryptolist.R
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
                if (isOnePaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }

        }
        binding.recyclerViewCoinPriceList.adapter = adapter
        binding.recyclerViewCoinPriceList.itemAnimator = null

        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }

    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

    private fun launchDetailActivity(fromSymbol: String) {
        val intent =
            CoinDetailActivity.newIntent(this@MainActivity, fromSymbol)
        startActivity(intent)
    }
}