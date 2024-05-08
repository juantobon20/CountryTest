package com.juantobon20.countrytest.views.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.databinding.LayoutRecyclerViewWithTitleBinding
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton


class RecyclerViewWithTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutRecyclerViewWithTitleBinding =
        LayoutRecyclerViewWithTitleBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.RecyclerViewWithTitle) {
            binding.lblTitle.text = getText(R.styleable.RecyclerViewWithTitle_recyclerTitle)
            val typeLayoutManager = getInt(R.styleable.RecyclerViewWithTitle_typeLayoutManager, 0)
            val orientation = getInt(R.styleable.RecyclerViewWithTitle_orientation, LinearLayout.VERTICAL)
            val spanCount = getInt(R.styleable.RecyclerViewWithTitle_spanCount, 2)
            val layoutManager = when (typeLayoutManager) {
                1 -> GridLayoutManager(context, spanCount)
                else -> LinearLayoutManager(context, orientation, false)
            }
            binding.recyclerView.layoutManager = layoutManager
        }
    }

    fun setAdapter(adapter: Adapter<*>) {
        binding.recyclerView.adapter = adapter
    }
    fun startSkeleton(itemLayout: Int) {
        binding.lblTitle.loadSkeleton(length = 50)
        binding.recyclerView.loadSkeleton(itemLayout) {
            itemCount(10)
        }
    }

    fun stopSkeleton() {
        binding.lblTitle.hideSkeleton()
        binding.recyclerView.hideSkeleton()
    }
}