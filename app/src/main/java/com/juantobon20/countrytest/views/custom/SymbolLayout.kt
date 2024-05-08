package com.juantobon20.countrytest.views.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.databinding.LayoutSymbolBinding
import com.juantobon20.countrytest.utils.loadImageFromUrl

class SymbolLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutSymbolBinding = LayoutSymbolBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.SymbolsLayout) {
            binding.lblTitle.text = getText(R.styleable.SymbolsLayout_title)

            val scaleTypeIndex : Int = getInt(R.styleable.SymbolsLayout_android_scaleType, -1)
            if (scaleTypeIndex > -1) {
                binding.image.scaleType = ImageView.ScaleType.entries.toTypedArray()[scaleTypeIndex]
            }
        }
    }

    fun getImageFromUrl(url: String?) {
        binding.image.loadImageFromUrl(url)
    }

}