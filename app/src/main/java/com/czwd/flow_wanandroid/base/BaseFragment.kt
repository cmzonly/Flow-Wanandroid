package com.czwd.flow_wanandroid.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.core.view.isNotEmpty
import androidx.core.view.marginTop
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blankj.utilcode.util.BarUtils
import com.czwd.flow_wanandroid.utils.GlobalViewModel
import kotlinx.coroutines.flow.collectLatest
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
        initData()
        initListen()
        initObserver()
    }

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

    open fun initObserver(){
    }

    abstract fun initData()

    abstract fun initView()
}