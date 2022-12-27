package com.shv.android.cryptolist.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.shv.android.cryptolist.domain.entities.CoinInfo

class CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return newItem.fromSymbol == oldItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return newItem == oldItem
    }
}