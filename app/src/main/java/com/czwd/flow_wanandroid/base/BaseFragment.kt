package com.czwd.flow_wanandroid.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.jessyan.autosize.AutoSize


abstract class BaseFragment<VB : ViewBinding> : Fragment(){

    /** 保存 RecyclerView 滚动状态 */
    private var pendingRvState: Parcelable? = null

    private var _binding : VB?=null
    val binding get() = _binding ?: throw IllegalStateException("Binding accessed after onDestroyView")
    lateinit var loadingPopupView: LoadingPopupView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initBinding(inflater , container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingPopupView = XPopup.Builder(context).asLoading("正在加载中")
        initView()
        initData()
        checkFirstLoad()
        initListen()
        initObserver()
    }


    //============ 首次加载必须重写的两个方法 ============
    protected open fun provideViewModel() : BaseViewModel?=null

    protected open fun onFirstLoad() {

    }

    private  fun checkFirstLoad() {
        val vm = provideViewModel()
        if (vm != null && vm.isFirstLoad){
            onFirstLoad()
            vm.isFirstLoad = false
        }

    }

    // ==================== 抽象方法 ====================

   protected abstract fun initView()

    protected open fun initData(){}

    protected open fun initListen(){}

   protected open fun initObserver(){}

    protected abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB



    // ==================== Flow 收集工具 ====================
   protected  fun startObserverOnStarted(block : (suspend CoroutineScope.() -> Unit)?=null){
        lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    block?.invoke(this)
                }

        }
    }

    /**
     * onresume调用,处理跳转webview后回退异常尺寸问题
     */
    override fun onResume() {
        super.onResume()
        AutoSize.autoConvertDensityOfGlobal(activity)
    }

    // ==================== RecyclerView 状态保存 ====================

    /**
     * 保存 RecyclerView 滚动位置
     * 子类重写此方法以提供 LayoutManager
     */
    protected open fun saveRecyclerViewState() {
        // 默认不保存，子类按需实现
    }

    /**
     * 恢复 RecyclerView 滚动位置
     * 子类重写此方法以恢复 LayoutManager 状态
     */
    protected open fun restoreRecyclerViewState(state: Parcelable) {
        // 默认不恢复，子类按需实现
    }

    /**
     * 暂存滚动状态（在 onPause 中调用）
     */
    protected fun holdRecyclerViewState(state: Parcelable?) {
        pendingRvState = state
    }

    /**
     * 此方法在 onViewCreated(View, Bundle) 之后、onStart() 之前调用
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // 恢复滚动状态
        pendingRvState?.let { state ->
            restoreRecyclerViewState(state)
            //pendingRvState 是一个"一次性"的状态包：存进去 → 恢复一次 → 清空，保证不会用过期数据覆盖当前的滚动位置。
            pendingRvState = null
        }
    }

    /**
     * 设置状态栏是否为亮色模式
     */
    protected fun setLightStatusBar(isLight: Boolean) {
        WindowInsetsControllerCompat(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = isLight
    }

    fun isShowLoading(isShow : Boolean){
        if (isShow) loadingPopupView.show() else loadingPopupView.dismiss()
    }

    /**
     * 暂停保存 RecyclerView 滚动状态
     * 当布局管理器需要保存其状态时调用。这是保存滚动位置、配置以及其他任何可能需要的信息的好时机，
     * 以便在重新创建布局管理器时恢复相同的布局状态
     */
    override fun onPause() {
        super.onPause()
        // 保存滚动状态（子类可重写）
        saveRecyclerViewState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}