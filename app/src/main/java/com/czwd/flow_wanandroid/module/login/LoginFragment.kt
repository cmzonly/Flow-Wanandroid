package com.czwd.flow_wanandroid.module.login

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentLoginBinding
import com.czwd.flow_wanandroid.db.datastore.UserInfoManager
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val loginViewModel : LoginViewModel by viewModel()

    companion object{
        private const val TAG = "LoginFragment"
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater , container , false)

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initObserver() {
        startObserverOnStarted{
            launch {
                loginViewModel.registerFlow.collectLatest {
                    when(it){
                        NetworkResult.Loading -> {
                           isShowLoading(true)
                        }
                        is NetworkResult.Success<RegisterResponse> -> {
                            isShowLoading(false)
                            ToastUtils.showLong("注册成功")
                            toGoneView()
                        }
                        is NetworkResult.Error -> {
                            Log.d(TAG, "Error:${it.message} ")
                            ToastUtils.showLong(it.message)
                        }

                        NetworkResult.Idle -> {}
                    }
                }
            }

            launch {
                loginViewModel.loginFlow.collectLatest {
                    when(it){
                        NetworkResult.Loading -> {
                            isShowLoading(true)
                        }
                        is NetworkResult.Success<LoginResponse> -> {
                            isShowLoading(false)
                            UserInfoManager.saveUserInfo(
                                    id = it.data.id,
                                    username = it.data.username,
                                    nickname = it.data.nickname,
                                    email = it.data.email,
                                    icon = it.data.icon,
                                    token = it.data.token
                            )
                            ToastUtils.showLong("登录成功")
                            findNavController().popBackStack()
                        }
                        is NetworkResult.Error -> {
                            isShowLoading(false)
                            ToastUtils.showLong("登录失败,${it.message}")
                            Log.d(TAG, "登录Error:${it.message} ")
                        }

                        NetworkResult.Idle -> {}
                    }
                }
            }
        }
    }

    fun toGoneView() {
        binding.etPassWordAgin.visibility = View.GONE
        binding.btnRegister.visibility = View.GONE
    }

    override fun initListen() {

        binding.btnRegister.setOnClickListener {
            Log.d(TAG, "inputSure===${inputSure()} ")
           if (inputSure()){
               loginViewModel.register(
                   binding.etUserName.text.toString() ,
                   binding.etPassWord.text.toString(),
                   binding.etPassWordAgin.text.toString()
               )
           }
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.login(binding.etUserName.text.toString() , binding.etPassWord.text.toString())
        }
    }

    fun inputSure() : Boolean {
      return (!StringUtils.isTrimEmpty(binding.etUserName.text.toString())
              and
              !StringUtils.isTrimEmpty(binding.etPassWord.text.toString())
              and
              !StringUtils.isTrimEmpty(binding.etPassWordAgin.text.toString())
              and
              (binding.etPassWord.text.toString() == binding.etPassWordAgin.text.toString()))

    }
}