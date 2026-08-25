package com.czwd.flow_wanandroid.module.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentDetailsBinding
import com.czwd.flow_wanandroid.module.details.vm.DetailsViewModel
import com.czwd.flow_wanandroid.module.home.adapter.ArticleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    companion object{
        fun toDetailFragment(fragment : Fragment){
            fragment.findNavController().navigate(R.id.nav_to_details , )

        }
    }
    lateinit var adapter : ArticleAdapter
    private val viewmodel: DetailsViewModel by viewModel()
    override fun initView() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsBinding.inflate(inflater, container, false)
}