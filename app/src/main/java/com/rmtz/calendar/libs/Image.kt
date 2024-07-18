package com.rmtz.calendar.libs

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat

class Image {
    companion object {
        fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {
            val db = ContextCompat.getDrawable(context, drawable)
            val bit = Bitmap.createBitmap(
                db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bit)
            db.setBounds(0, 0, canvas.width, canvas.height)
            db.draw(canvas)
            return bit
        }
    }
}