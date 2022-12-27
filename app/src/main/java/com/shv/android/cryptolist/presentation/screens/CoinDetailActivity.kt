package com.shv.android.cryptolist.presentation.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shv.android.cryptolist.R
import com.shv.android.cryptolist.presentation.CoinViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*

private const val TAG = "CoinDetailActivity"

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.getDetailInfo(fromSymbol).observe(this) {
            tvPrice.text = it.price.toString()
            tvMinDay.text = it.lowDay.toString()
            tvMaxDay.text = it.highDay.toString()
            tvLastDeal.text = it.lastMarket
            tvUpdate.text = it.lastUpdate

            textViewSymbolDetail.text = it.fromSymbol
            textViewCurrency.text = it.toSymbol

            Picasso.get().load(it.imageUrl).into(imageViewLogoCoinDetail)
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}