package com.czwd.flow_wanandroid.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
   protected val binding get() = _binding ?: throw IllegalStateException("Binding accessed after onDestroy")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = initBinding()
        setContentView(binding.root)
        initView()
        initData()
        initObserver()
    }



    protected  abstract fun initObserver()

    protected abstract fun initData()

    protected abstract fun initView()

   protected abstract fun initBinding(): VB


    fun startObserveOnStarted(block : CoroutineScope.() -> Unit){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                block.invoke(this)
            }
        }
    }

    fun startObserve(block: CoroutineScope.() -> Unit){
        lifecycleScope.launch {
            block.invoke(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}