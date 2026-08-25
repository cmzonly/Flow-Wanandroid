package com.czwd.flow_wanandroid.module.system.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.databinding.FragmentSystemBinding
import com.czwd.flow_wanandroid.module.details.ui.DetailsFragment
import com.czwd.flow_wanandroid.module.system.adapter.SystemAdapter
import com.czwd.flow_wanandroid.module.system.bean.SystemResponse
import com.czwd.flow_wanandroid.module.system.vm.SystemViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class SystemFragment : BaseFragment<FragmentSystemBinding>() {
    private val viewmodel by viewModel<SystemViewModel>()

    private lateinit var systemAdapter: SystemAdapter

    override fun initView() {
        setUpRecyclerview()
    }

    override fun initObserver() {
        startObserverOnStarted {
            viewmodel.systemFlow.collectLatest {
                systemAdapter.submitList(it.systemList)
            }
        }
    }

    private fun setUpRecyclerview() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
            systemAdapter = SystemAdapter{
                val bundle = Bundle()
                bundle.putString("json" , it.toString())
                findNavController().navigate(R.id.nav_to_details)
            }
            adapter = systemAdapter
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSystemBinding.inflate(inflater, container, false)

    override fun provideViewModel(): BaseViewModel?  = viewmodel

    override fun onFirstLoad() {
        viewmodel.getSystemData()
    }
}