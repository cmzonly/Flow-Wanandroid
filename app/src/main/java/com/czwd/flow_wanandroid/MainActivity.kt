package com.czwd.flow_wanandroid

import android.util.Log
import androidx.navigation.findNavController
import com.czwd.flow_wanandroid.base.BaseActivity
import com.czwd.flow_wanandroid.databinding.ActivityMainBinding
import com.czwd.flow_wanandroid.utils.GlobalViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun initObserver() {
        startObserveOnStarted {
            launch {
                GlobalViewModel.loginFlow.collectLatest {
                    Log.d("gggg", "initObserver: ")
                    //这里需要判断,否则当请求时多次返回-1001,会多次尝试导航到 LoginFragment,如果在loginframgnt
                    //发现找不到,就会报错
                   val navController =  findNavController(R.id.navView)
                    if (navController.currentDestination?.id != R.id.loginFragment){
                        navController.navigate(R.id.nva_to_login)
                    }
                }
            }

        }

    }

    override fun initData() {
    }

    override fun initView() {

    }

    override fun initBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)


}