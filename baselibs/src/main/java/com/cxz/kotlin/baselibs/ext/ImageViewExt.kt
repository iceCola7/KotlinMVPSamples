package com.cxz.kotlin.baselibs.ext

import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ImageView

/**
 * 设置自适应高度的ImageView
 */
fun ImageView.setScaleImg(bitmap: Bitmap) {
    post {
        setImageBitmap(bitmap)
        /*
         * 获取ImageView中image的宽高
         * 注意：这里的宽高是Image原始的宽高，不是当前在ImageView中显示的宽高
         */
        val imgWidth = this.drawable.bounds.width()
        val imgHeight = this.drawable.bounds.height()

        // 获取ImageView中Image的变换矩阵
        val emptyMatrix = FloatArray(9)
        this.imageMatrix.getValues(emptyMatrix)

        // 分别从矩阵中获取X和Y的缩放系数
        val scaleX = emptyMatrix[0]
        val scaleY = emptyMatrix[4]

        // 计算Image在屏幕上实际绘制的宽高
        val realWidth = (imgWidth * scaleX).toInt()
        val realHeight = (imgHeight * scaleY).toInt()

        // 将ImageView的高度重新设置为ImageView中实际绘制的Image的高度，这样上下的留白就没有了，图片也可以完整显示了，不必
        // 刻意设置scaleType,使用默认的FIT_CENTER就好。
        val layoutParams: ViewGroup.LayoutParams = this.layoutParams
        layoutParams.height = realHeight
        this.layoutParams = layoutParams
    }
}