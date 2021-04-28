package com.challenge.spacex.ui.main.fragments.widgets

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.challenge.spacex.R
import com.challenge.spacex.databinding.MainLaunchDetailsCardBinding

class LineDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: MainLaunchDetailsCardBinding

    init {
        initializeView(context)

        attrs?.let(::applyAttributes)
    }

    private fun initializeView(context: Context) {
        binding = MainLaunchDetailsCardBinding.inflate(LayoutInflater.from(context), this)
    }

    private fun applyAttributes(attributes: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.LineDetailsView)

        typedArray.setTextIfSet(binding.widgetLaunchDetailsTitle, R.styleable.LineDetailsView_title)
        typedArray.setTextIfSet(binding.widgetLaunchDetailsValue, R.styleable.LineDetailsView_value)

        typedArray.recycle()
    }

    fun setTitle(text: String) {
        binding.widgetLaunchDetailsTitle.text = text
    }
    fun setValue(text: String) {
        binding.widgetLaunchDetailsValue.text = text
    }

    private fun TypedArray.setTextIfSet(textView: TextView, textViewTextRes: Int) =
        getText(textViewTextRes)?.let(textView::setText)
}
