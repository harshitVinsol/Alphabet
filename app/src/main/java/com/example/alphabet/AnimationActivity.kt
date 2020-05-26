package com.example.alphabet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.top_bar_layout.*
import java.util.ArrayList

class AnimationActivity : AppCompatActivity() {

    private var alphaLayoutManager: GridLayoutManager? = null
    private lateinit var adapter: AlphabetAdapter
    private var animationSpeed = 200L
    private var verticalSpacing = 0
    private var horizontalSpacing = 0
    private var alphaList = ('A'..'Z').toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        if (savedInstanceState != null) {
            alphaList = savedInstanceState.getSerializable(ALPHABET_LIST) as MutableList<Char>
        }

        animationSpeed = intent.getLongExtra(ANIMATION_SPEED, 200L)
        verticalSpacing = intent.getIntExtra(VERTICAL_SPACING, 0)
        horizontalSpacing = intent.getIntExtra(HORIZONTAL_SPACING, 0)

        alphabet_recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(
                    verticalSpacing / 2,
                    horizontalSpacing / 2,
                    verticalSpacing / 2,
                    horizontalSpacing / 2
                )
            }
        })
        val screenWidth = resources.displayMetrics.widthPixels

        alphaLayoutManager = GridLayoutManager(this, GRID_SIZE)
        alphabet_recyclerview.layoutManager = alphaLayoutManager

        adapter = AlphabetAdapter(
            alphaList,
            screenWidth,
            animationSpeed,
            verticalSpacing,
            horizontalSpacing
        ) { position, holder: ViewHolder ->
            flipAnimation(holder, position)
        }
        alphabet_recyclerview.adapter = adapter

        button_configure.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java)
            intent.putExtra(ANIMATION_SPEED, animationSpeed)
            intent.putExtra(VERTICAL_SPACING, verticalSpacing)
            intent.putExtra(HORIZONTAL_SPACING, horizontalSpacing)
            startActivity(intent)
        }
    }

    /*
    Function for the flip animation of the view clicked
     */
    private fun flipAnimation(holder: ViewHolder, position: Int) {
        val flipAnimator = ObjectAnimator.ofFloat(holder.itemView, View.ROTATION_Y, -180f, 0f)
        flipAnimator.duration = FLIP_ANIMATION_DURATION
        flipAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                holder.itemView.isVisible = false
                if (position == alphaList.size - 1) {
                    alphaList.removeAt(position)
                }
                adapter.setAlphaList(alphaList)
                alphabet_recyclerview.adapter = adapter
            }
        })
        flipAnimator.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ALPHABET_LIST, alphaList as ArrayList<out Parcelable>)
    }

    companion object {
        private const val ALPHABET_LIST = "alphabet_list"
        internal const val GRID_SIZE = 4
        internal const val ANIMATION_SPEED = "animation_speed"
        internal const val VERTICAL_SPACING = "vertical_spacing"
        internal const val HORIZONTAL_SPACING = "horizontal_spacing"
        internal const val FLIP_ANIMATION_DURATION = 400L
    }
}
