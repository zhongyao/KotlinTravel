package com.hongri.kotlin.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.layout_title_bar.view.*

/**
 * @author：hongri
 * @date：8/9/22
 * @description：自定义布局
 */
class TitleLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this)
        back.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }
        more.setOnClickListener {
            Toast.makeText(context, "更多", Toast.LENGTH_LONG).show()
        }
    }
}