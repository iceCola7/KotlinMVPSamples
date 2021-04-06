package com.cxz.kotlin.baselibs.ext

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

/**
 * 高效加载大型位图
 * https://developer.android.google.cn/topic/performance/graphics/load-bitmap
 * imageView.setImageBitmap(decodeSampledBitmapFromResource(resources, R.id.myimage, 100, 100))
 */
fun decodeSampledBitmapFromResource(
    res: Resources,
    resId: Int,
    reqWidth: Int,
    reqHeight: Int
): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, this)

        // Calculate inSampleSize
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false

        BitmapFactory.decodeResource(res, resId, this)
    }
}

private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}
