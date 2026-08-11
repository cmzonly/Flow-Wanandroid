package com.czwd.flow_wanandroid.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.czwd.flow_wanandroid.utils.GlobalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = getViewBinding()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            GlobalViewModel.saveStatusBarHeight(systemBars.top)
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
        initView()
        initData()
        initObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun initObserver()

    abstract fun initData()

    abstract fun initView()

    abstract fun getViewBinding(): VB


    fun startObserve(block : CoroutineScope.() -> Unit){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                block.invoke(this)
            }
        }
    }
}