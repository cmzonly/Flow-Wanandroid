package com.czwd.flow_wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.czwd.flow_wanandroid.utils.GlobalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding> : Fragment(){

    private var _binding : VB?=null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater , container)
        return binding.root
    }

    protected fun setLightStatusBar(isLight: Boolean) {
        WindowInsetsControllerCompat(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = isLight
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initLazyData()
        initListen()
        initObserver()
    }

    private  fun initLazyData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                initData()
            }
        }

    }

    open fun initObserver(){}

    open fun initListen(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    override fun onStart() {
        super.onStart()
        binding.root.apply {
            GlobalViewModel.statusBarHeightFlow.value.let {
                if (it > 0){
                    setPadding(paddingLeft, GlobalViewModel.statusBarHeightFlow.value, paddingRight, paddingBottom)
                }
            }

        }
    }

   protected  fun userObserverOnStarted( block : ( CoroutineScope.() -> Unit)?=null){
        lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    block?.invoke(this)
                }

        }
    }

    protected  fun userObserver( block : (CoroutineScope. () -> Unit)?=null){
        lifecycleScope.launch {
            block?.invoke(this)
        }
    }

    abstract fun initData()

    abstract fun initView()
}