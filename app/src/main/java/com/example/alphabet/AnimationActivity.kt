package com.example.alphabet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.top_bar_layout.*
import java.io.Serializable
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var alphaLayoutManager: GridLayoutManager? = null
    private lateinit var adapter: AlphabetAdapter
    private var alphaList = mutableListOf<Char>()
    private var animationSpeed = 200L
    private var verticalSpacing = 16
    private var horizontalSpacing = 16
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            alphaList = savedInstanceState.getSerializable(ALPHABET_LIST) as MutableList<Char>
        }
        val width = resources.displayMetrics.widthPixels
        Log.i("harsh", "Screen width: $width")
        alphaLayoutManager = GridLayoutManager(this, GRID_SIZE)
        alphabet_recyclerview.layoutManager = alphaLayoutManager
        alphabet_recyclerview.itemAnimator = DefaultItemAnimator()
        alphaList = ('A'..'Z').toMutableList()
        adapter = AlphabetAdapter(
            alphaList,
            width
        ) { character: Char, position: Int, holder: ViewHolder ->
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

    private fun flipAnimation(holder: ViewHolder, position: Int) {
        val flipAnimator = ObjectAnimator.ofFloat(holder.itemView, View.ROTATION_Y, -180f, 0f)
        flipAnimator.duration = 500
        flipAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                alphaList.removeAt(position)
                adapter.setAlphaList(alphaList, position)
                alphabet_recyclerview.adapter = adapter
                //alphaList.removeAt(position)
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
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
    }
}
