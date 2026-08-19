// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    //KSP 从 2.3.0 版本起已与 Kotlin 版本解耦（官方说明），且 KSP 2.3.1 明确添加了对 AGP 9.0 和内置 Kotlin 的支持
    alias(libs.plugins.ksp) apply false
}