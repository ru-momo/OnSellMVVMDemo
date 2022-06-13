package com.momo.sellmvvm

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.momo.sellmvvm.adapter.OnSellListAdapter
import com.momo.sellmvvm.utils.SizeUtils

class OnSellActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(OnSellViewModel::class.java)
    }

    private val mAdapter by lazy {
        OnSellListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initObserver()
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        findViewById<RecyclerView>(R.id.contentListRv).run {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@OnSellActivity)
            addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.apply {
                            val padding = SizeUtils.dip2px(this@OnSellActivity, 4.0f)
                            top = padding
                            left = padding
                            bottom = padding
                            right = padding
                        }
                    }
                }
            )
        }
    }

    /**
     * 观察数据变化
     */
    private fun initObserver() {
        viewModel.apply {
            contentList.observe(this@OnSellActivity, Observer {
                //内容更新
                //更新适配器
                mAdapter.setData(it)
            })
        }.loadContent()
    }

}