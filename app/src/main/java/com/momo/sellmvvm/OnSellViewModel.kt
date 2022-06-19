package com.momo.sellmvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momo.sellmvvm.base.LoadState
import com.momo.sellmvvm.domain.MapData
import com.momo.sellmvvm.utils.ThreadUtils
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class OnSellViewModel : ViewModel() {

    private val TAG: String = "OnSellViewModel"
    val contentList = MutableLiveData<MutableList<MapData>>()

    val loadState = MutableLiveData<LoadState>()

    private val onSellRepository by lazy {
        OnSellRepository()
    }

    companion object {
        //默认第一页
        const val DEFAULT_PAGE = 1
    }

    //当前页面
    private var mCurrentPage = DEFAULT_PAGE

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
        Log.d(TAG, "listContentByPage: $page")
        viewModelScope.launch {
            try {
                val onSellList = onSellRepository.getOnSellList(page)
                val oldValue = contentList.value ?: mutableListOf()
                oldValue.addAll(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                contentList.postValue(oldValue)
                if (onSellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.postValue(LoadState.EMPTY)
                } else {
                    loadState.postValue(if (mCurrentPage > 1) LoadState.LOADING_MORE_SUCCESS else LoadState.SUCCESS)
                }
            } catch (e: NullPointerException) {
                // 没有更多内容时，会返回一个空指针
                e.printStackTrace()
                loadState.postValue(LoadState.LOADING_MORE_EMPTY)
                mCurrentPage --
            } catch (e: Exception) {
                e.printStackTrace()
                loadState.postValue(if (mCurrentPage > 1) LoadState.LOADING_MORE_ERROR else LoadState.ERROR)
                mCurrentPage --
            }
        }
    }

    /**
     * 加载更多内容
     */
    fun loadMore() {
        Log.d(TAG, "loadMore: 加载更多!")
        mCurrentPage++
        loadState.postValue(LoadState.LOADING_MORE_LOADING)
        listContentByPage(mCurrentPage)
    }

}