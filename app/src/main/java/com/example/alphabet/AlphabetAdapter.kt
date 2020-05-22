package com.example.alphabet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alphabet.MainActivity.Companion.GRID_SIZE
import kotlinx.android.synthetic.main.activity_main.*

/*
Class AddressAdapter to be used in RecyclerView for showing addresses having list of Address and context of Activity called from
 */
class AlphabetAdapter(
    private var alphabetList: MutableList<Char>,
    private var screenWidth: Int,
    private val callback: (Char, Int, ViewHolder) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    fun setAlphaList(list: MutableList<Char>, position: Int) {
        alphabetList = list
        notifyDataSetChanged()
        //alphabetList.removeAt(position)
    }

    /*
    function to attach a layout to the ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.alphabet_item, parent, false)
        return ViewHolder(v)
    }

    /*
    function to return size of addressList
     */
    override fun getItemCount(): Int {
        return alphabetList.size
    }

    /*
    function to bind each element of the RecyclerView
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.post {
            val width = holder.itemView.measuredWidth
            val height = holder.itemView.measuredHeight
            Log.i("@harsh", "width: ${width} height: ${height}")
            val alphabet = alphabetList[position]
            holder.textAlpha.text = alphabet.toString()
            holder.itemView.setOnClickListener {
                callback.invoke(alphabetList[position], position, holder)
                positionClicked = position
                delay = 0L
                //alphabetList.removeAt(position)
            }
            if (position > positionClicked + 1) {
                if (position % GRID_SIZE != 0) {
                    delay += 200
                    slideLeftAnimation(holder, delay, width)
                } else {
                    delay += 200
                    diagonalAnimation(holder, delay, height, width)
                }
            }
            //alphabetList.removeAt(position)
        }
    }

    private fun diagonalAnimation(holder: ViewHolder, delay: Long, height: Int, width: Int) {
        val y = -(height.toFloat())
        val x = screenWidth.toFloat() - (width.toFloat() + 48f)
        Log.i("@harsh", "x: $x y: $y")
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_Y, y)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_X, x)
        val animateXY = AnimatorSet()
        animateXY.duration = 200
        animateXY.playTogether(listOf(animatorX, animatorY))
        animateXY.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                animateXY.startDelay = delay
            }
        })
        animateXY.start()
    }

    private fun slideLeftAnimation(holder: ViewHolder, delay: Long, width: Int) {
        val x = -(width.toFloat())
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_X, x)
        animatorX.duration = 200
        animatorX.startDelay = delay
        animatorX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                animatorX.startDelay = delay
            }
        })
        animatorX.start()
    }

    companion object {
        private var positionClicked: Int = 26
        private var delay = 0L
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textAlpha: TextView = itemView.findViewById(R.id.alphabet_char)
}
