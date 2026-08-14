package com.czwd.flow_wanandroid.module.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentUserBinding
import com.czwd.flow_wanandroid.db.datastore.UserInfoManager
import com.czwd.flow_wanandroid.network.CookieDataStoreManager
import com.czwd.flow_wanandroid.network.NetworkResult
import com.czwd.flow_wanandroid.utils.GlobalViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment<FragmentUserBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding  = FragmentUserBinding.inflate(inflater,container,false)

    private val userViewModel by viewModel<UserViewModel>()

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initListen() {
        binding.btnLoginout.setOnClickListener {
            userViewModel.loginOut()
        }
    }

    override fun initObserver() {
        startObserverOnStarted {
            launch {
                UserInfoManager.userInfoFlow.collectLatest {
                    binding.tvUsername.text = it?.username ?: "未登录"
                }
            }

            launch {
                userViewModel.loginOutFlow.collectLatest {
                    when(it){
                        NetworkResult.Loading -> {
                            isShowLoading(true)
                        }
                        is NetworkResult.Success<*> -> {
                            isShowLoading(false)
                            UserInfoManager.clearUserInfo()
                            CookieDataStoreManager.clearCookies()
                        }
                        is NetworkResult.Error -> {
                            isShowLoading(false)
                        }
                        NetworkResult.Idle -> {}

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
    }
}