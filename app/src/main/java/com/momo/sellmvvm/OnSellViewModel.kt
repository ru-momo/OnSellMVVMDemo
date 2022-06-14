package com.momo.sellmvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momo.sellmvvm.base.LoadState
import com.momo.sellmvvm.domain.MapData
import com.momo.sellmvvm.utils.ThreadUtils
import kotlinx.coroutines.launch

class OnSellViewModel : ViewModel() {

    val contentList = MutableLiveData<List<MapData>>()

    val loadState = MutableLiveData<LoadState>()

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
        ThreadUtils.taskHandler.post {
            loadState.postValue(LoadState.LOADING)
            listContentByPage(mCurrentPage)
        }
    }

    private fun listContentByPage(page: Int) {
        try {
            viewModelScope.launch {
                val onSellList = onSellRepository.getOnSellList(page)
                if (onSellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.postValue(LoadState.EMPTY)
                }else{
                    contentList.postValue(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                    loadState.postValue(LoadState.SUCCESS)
                }
            }
        } catch (e: Exception) {
            loadState.postValue(LoadState.ERROR)
            e.printStackTrace()
        }

    }

    /**
     * 加载更多内容
     */
    fun loadMore() {

    }

}