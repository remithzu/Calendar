package com.rmtz.calendar.libs

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat

class Image {
    companion object {
        fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {

            // on below line we are getting drawable
            val db = ContextCompat.getDrawable(context, drawable)

            // in below line we are creating our bitmap and initializing it.
            val bit = Bitmap.createBitmap(
                db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
            )

            // on below line we are
            // creating a variable for canvas.
            val canvas = android.graphics.Canvas(bit)

            // on below line we are setting bounds for our bitmap.
            db.setBounds(0, 0, canvas.width, canvas.height)

            // on below line we are simply
            // calling draw to draw our canvas.
            db.draw(canvas)

            // on below line we are
            // returning our bitmap.
            return bit
        }
    }
}