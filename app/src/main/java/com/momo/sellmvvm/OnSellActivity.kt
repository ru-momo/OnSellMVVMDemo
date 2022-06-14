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
import com.momo.sellmvvm.base.LoadState
import com.momo.sellmvvm.databinding.ActivityMainBinding
import com.momo.sellmvvm.utils.SizeUtils

class OnSellActivity : AppCompatActivity() {

    private val rootBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(OnSellViewModel::class.java)
    }

    private val mAdapter by lazy {
        OnSellListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        initView()
        initObserver()
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        rootBinding.contentListRv.run {
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
            contentList.observe(this@OnSellActivity) {
                //内容更新
                //更新适配器
                mAdapter.setData(it)
            }
            loadState.observe(this@OnSellActivity) {
                hideAllView()
                when (it) {
                    LoadState.SUCCESS -> rootBinding.contentListRv.visibility = View.VISIBLE
                    LoadState.EMPTY -> rootBinding.emptyView.root.visibility = View.VISIBLE
                    LoadState.LOADING -> rootBinding.loadingView.root.visibility = View.VISIBLE
                    LoadState.ERROR -> rootBinding.errorView.root.visibility = View.VISIBLE
                }
            }
        }.loadContent()
    }

    /**
     * 隐藏所有view
     */
    private fun hideAllView() {
        for (i in 0 until rootBinding.root.childCount) {
            rootBinding.root.getChildAt(i).visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}