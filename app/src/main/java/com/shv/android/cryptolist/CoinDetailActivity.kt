package com.shv.android.cryptolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*
import kotlinx.android.synthetic.main.activity_coin_detail.view.*
import kotlinx.android.synthetic.main.item_coin_info.*

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
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.getDetailInfo(fromSymbol!!).observe(this, Observer {
            tvPrice.text = it.price.toString()
            tvMinDay.text = it.lowDay.toString()
            tvMaxDay.text = it.highDay.toString()
            tvLastDeal.text = it.lastMarket
            tvUpdate.text = it.getFormattedTime()

            textViewSymbolDetail.text = it.fromSymbol
            textViewCurrency.text = it.toSymbol

            Picasso.get().load(it.getFullImageUrl()).into(imageViewLogoCoinDetail)
        })
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}