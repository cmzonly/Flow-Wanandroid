package com.czwd.flow_wanandroid.module.web

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.databinding.FragmentWebBinding
import com.just.agentweb.AgentWeb
import kotlinx.coroutines.flow.collectLatest

class WebFragment : BaseFragment<FragmentWebBinding>() {
    private val webViewModel: WebViewModel by viewModels()
    companion object{
        const val KEY_URL = "url"

        fun startToWebFragment(fragment : Fragment  , url : String){
            fragment.findNavController().navigate(R.id.nav_to_web , bundleOf(KEY_URL  to url))
        }
    }


    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWebBinding  =
        FragmentWebBinding.inflate(layoutInflater , container ,false)

    override fun provideViewModel(): BaseViewModel? = webViewModel

    override fun onFirstLoad() {
        arguments?.getString(KEY_URL)?.let {
            webViewModel.setUrl(it)
        }
    }

    override fun initView() {

    }

    override fun initObserver() {
        startObserverOnStarted {
            webViewModel.bundleFlow.collectLatest {
                it?.let {
                    AgentWeb.with(activity)
                        .setAgentWebParent(binding.rlWeb, LinearLayout.LayoutParams(-1, -1))
                        .useDefaultIndicator()
                        .createAgentWeb()
                        .ready()
                        .go(it)
                }

            }
        }

    }

}