package com.juantobon20.countrytest.views.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.databinding.LayoutHeaderDetailBinding
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class HeaderDetailLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding : LayoutHeaderDetailBinding = LayoutHeaderDetailBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.HeaderDetailLayout) {
            binding.lblTitle.text = getText(R.styleable.HeaderDetailLayout_headerTitle)
        }
    }

    fun startSkeleton() {
        binding.lblTitle.loadSkeleton(length = 10)
        binding.lblDetail.loadSkeleton(length = 20)
    }

    fun stopSkeleton() {
        binding.lblTitle.hideSkeleton()
        binding.lblDetail.hideSkeleton()
    }

    fun setDetailText(text: String) {
        binding.lblDetail.text = text
    }
}