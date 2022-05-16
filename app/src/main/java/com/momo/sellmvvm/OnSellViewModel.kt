package com.momo.sellmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class OnSellViewModel : ViewModel() {

    private val onSellRepository by lazy {
        OnSellRepository()
    }

    companion object {
        //默认第一页
        const val DEFAULT_PAGE = 1
    }

    //当前页面
    private val mCurrentPage = DEFAULT_PAGE

    /**
     * 加载首页内容
     */
    fun loadContent() {
        listContentByPage(mCurrentPage)
    }

    private fun listContentByPage(page: Int) {
        viewModelScope.launch {
            val onSellList = onSellRepository.getOnSellList(page)
            println("result size: ${onSellList.tbk_dg_optimus_material_response.result_list.map_data.size} ;")

        }
    }

    /**
     * 加载更多内容
     */
    fun loadMore() {

    }

}