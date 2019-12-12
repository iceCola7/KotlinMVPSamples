package com.cxz.kotlin.samples.utils

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.location.LocationManager
import android.os.Binder
import android.os.Build
import android.support.v4.app.AppOpsManagerCompat
import android.support.v4.app.AppOpsManagerCompat.noteOp
import android.support.v4.app.FragmentActivity
import com.cxz.kotlin.samples.widgets.PermissionDialog
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * @author admin
 * @date 2019/10/24
 * @desc 权限申请
 */
object PermissionHelper {

    /**
     * 申请相机权限
     */
    fun requestCameraPermission(activity: FragmentActivity?, requestSuccess: (() -> Unit)? = null) {
        activity?.let {
            RxPermissions(activity)
                .requestEachCombined(Manifest.permission.CAMERA)
                .subscribe {
                    if (it.granted) {
                        requestSuccess?.invoke()
                    } else if (it.shouldShowRequestPermissionRationale) {
                        showPermissionDialog(
                            activity, "为了保证您正常使用此功能，需要获取您的相机使用权限，请允许。",
                            "去允许", true, requestSuccess
                        )
                    } else {
                        showPermissionDialog(
                            activity, "未取得您的相机使用权限，此功能无法使用。请前往应用权限设置打开权限。",
                            "去打开", false, requestSuccess
                        )
                    }
                }
        }
    }

    /**
     * 展示申请相机权限的对话框
     */
    private fun showPermissionDialog(
        activity: FragmentActivity, content: String, rightText: String,
        showPermission: Boolean = true, requestSuccess: (() -> Unit)? = null
    ) {
        PermissionDialog.newBuilder()
            .setTitle("温馨提示")
            .setContent(content)
            .setRightText(rightText)
            .build()
            ?.setOnConfirmClickListener {
                if (showPermission) {
                    requestCameraPermission(activity, requestSuccess)
                } else {
                    PermissionPageUtil.gotoPermission(activity)
                }
            }
            ?.show(activity.supportFragmentManager, "permission_dialog")
    }

    /**
     * 检查系统定位服务权限
     */
    fun checkLocServicePermission(activity: FragmentActivity, callback: (() -> Unit)? = null) {
        if (!PermissionHelper.isLocServiceEnable(activity)) { // 检测是否开启定位服务
            PermissionDialog.newBuilder()
                .setTitle("温馨提示")
                .setContent("开启定位服务，获取精准定位")
                .setRightText("去开启")
                .build()
                ?.setOnConfirmClickListener {
                    PermissionPageUtil.gotoLocServicePage(activity)
                }
                ?.show(activity.supportFragmentManager, "location_dialog")
        } else { // 检测用户是否将当前应用的定位权限拒绝
            if (callback != null) {
                callback?.invoke()
            } else {
                //其中2代表AppOpsManager.OP_GPS，如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager。OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
                val checkResult = checkOp(activity, 2, AppOpsManager.OPSTR_FINE_LOCATION)
                val checkResult2 = checkOp(activity, 1, AppOpsManager.OPSTR_FINE_LOCATION)
                if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
                    // DlgUtils.showLocIgnoredDialog(this)
                }
            }
        }
    }

    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    fun isLocServiceEnable(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }

    /**
     * 检查权限列表
     * 其中第二个参数2代表AppOpsManager.OP_GPS，
     * 如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager.OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
     *
     * @param context
     * @param op 这个值被hide了，去AppOpsManager类源码找，如位置权限  AppOpsManager.OP_GPS==2
     * @param opString 如判断定位权限 AppOpsManager.OPSTR_FINE_LOCATION
     * @return  @see 如果返回值 AppOpsManagerCompat.MODE_IGNORED 表示被禁用了
     */
    fun checkOp(context: Context, op: Int, opString: String): Int {
        val version = Build.VERSION.SDK_INT
        if (version >= 19) {
            val obj = context.getSystemService(Context.APP_OPS_SERVICE)
            // Object object = context.getSystemService("appops");
            val c = obj.javaClass
            try {
                val cArg = arrayOfNulls<Class<*>>(3)
                cArg[0] = Int::class.javaPrimitiveType
                cArg[1] = Int::class.javaPrimitiveType
                cArg[2] = String::class.java
                val lMethod = c.getDeclaredMethod("checkOp", *cArg)
                return lMethod.invoke(obj, op, Binder.getCallingUid(), context.packageName) as Int
            } catch (e: Exception) {
                e.printStackTrace()
                if (Build.VERSION.SDK_INT >= 23) {
                    return noteOp(
                        context,
                        opString,
                        context.applicationInfo.uid,
                        context.packageName
                    )
                }
            }
        }
        return -1
    }

}