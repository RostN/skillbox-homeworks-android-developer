package com.example.hw2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.WindowId
import android.widget.LinearLayout
import com.example.hw2.databinding.CustomViewBinding

class CustomView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    val binding = CustomViewBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }
    fun setTextFirstLine(text:String){
        binding.firstTetLine.text=text
    }
    fun setTextSecondLine(text: String){
        binding.secondTextLine.text=text
    }
}
