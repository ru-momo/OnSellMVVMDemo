package com.momo.sellmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.momo.sellmvvm.adapter.OnSellListAdapter

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