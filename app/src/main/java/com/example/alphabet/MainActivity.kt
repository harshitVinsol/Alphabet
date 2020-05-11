package com.example.alphabet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var alphaLayoutManager: GridLayoutManager? = null
    private lateinit var adapter: AlpabetAdapter
    private var alphaList = mutableListOf<Char>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alphaLayoutManager = GridLayoutManager(this, 4)
        aphabet_recyclerview.layoutManager = alphaLayoutManager
        alphaList = ('A'..'Z').toMutableList()
        adapter = AlpabetAdapter(alphaList) { character: Char, position: Int, holder: ViewHolder ->
            val animator = ObjectAnimator.ofFloat(holder.itemView, View.ROTATION_Y, -180f, 0f)
            animator.duration = 500
            animator.start()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    //holder.itemView.isClickable = true
                    aphabet_recyclerview.isEnabled = true
                    alphaList.removeAt(position)
                    adapter.setAlphaList(alphaList)
                    aphabet_recyclerview.adapter = adapter
                }

                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    aphabet_recyclerview.isEnabled = false
                    //holder.itemView.isClickable = false
                }
            })
        }
        aphabet_recyclerview.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val ALPHABET_LIST = "alphabet_list"
    }
}
