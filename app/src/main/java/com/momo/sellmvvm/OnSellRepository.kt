package com.momo.sellmvvm

import com.momo.sellmvvm.api.RetrofitClient

class OnSellRepository {

    suspend fun getOnSellList(page: Int) =
        RetrofitClient.apiService.getOnSellList(page).apiData()

}