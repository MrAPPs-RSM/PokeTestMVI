package com.mirkopruiti.android_team_test.util

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mirkopruiti.android_team_test.R

/**
 * Created by Mirko Pruiti on 03/11/21.
 *
 * @author Mirko Pruiti
 */

fun ImageView.setImageWithColorBackground(url:String?,viewBackground:View){
    Glide.with(this.context).asBitmap()
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                resource ?: return false
                val p: Palette = Palette.from(resource).generate()
                p.dominantSwatch?.rgb?.let { color ->
                    when(viewBackground){
                        is CardView -> viewBackground.setCardBackgroundColor(color)
                        else -> viewBackground.setBackgroundColor(color)
                    }


                }
                return false
            }

        }).into(this)
}

fun ImageView.setUrlImage(url:String?){
    Glide.with(this.context).asBitmap()
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)

}