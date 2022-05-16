package com.momo.sellmvvm.domain

import com.momo.sellmvvm.api.ApiException

data class ResultData<T>(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: T
) {
    companion object {
        const val CODE_SUCCESS = 10000
    }

    fun apiData(): T {
        //如果是成功则返回数据，失败抛出异常
        if (code == CODE_SUCCESS) {
            return data
        } else {
            throw ApiException(code, message)
        }
    }

}
