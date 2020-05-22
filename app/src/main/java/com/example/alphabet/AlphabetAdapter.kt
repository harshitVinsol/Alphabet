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
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.alphabet.MainActivity.Companion.GRID_SIZE
import kotlinx.android.synthetic.main.activity_main.*

/*
Class AddressAdapter to be used in RecyclerView for showing addresses having list of Address and context of Activity called from
 */
class AlphabetAdapter(
    private var alphabetList: MutableList<Char>,
    private var screenWidth: Int,
    private val callback: (Int, ViewHolder) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    fun setAlphaList(list: MutableList<Char>) {
        alphabetList = list
        alphabetList.removeAt(positionClicked)
        notifyItemRangeChanged(positionClicked +1 , alphabetList.size)
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
            holder.itemView.isVisible = (position != positionClicked)
            holder.itemView.setOnClickListener {
                callback.invoke(position, holder)
                positionClicked = position
                delay = 0L
            }
            if (position > positionClicked && position < alphabetList.size) {
                if (position % GRID_SIZE != 0) {
                    delay += 200
                    slideLeftAnimation(holder, delay, width, false)
                } else {
                    delay += 200
                    diagonalAnimation(holder, delay, height, width, false)
                }
            }
            else if(position == alphabetList.size){
                if (position % GRID_SIZE != 0) {
                    delay += 200
                    slideLeftAnimation(holder, delay, width, true)
                } else {
                    delay += 200
                    diagonalAnimation(holder, delay, height, width, true)
                }
            }
        }
    }

    private fun diagonalAnimation(holder: ViewHolder, delay: Long, height: Int, width: Int, lastElement: Boolean) {
        val y = -(height.toFloat())
        val x = screenWidth.toFloat() - (width.toFloat() + 48f)
        Log.i("@harsh", "x: $x y: $y")
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_Y, y)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_X, x)
        val animateXY = AnimatorSet()
        animateXY.duration = 200
        animateXY.startDelay = delay
        animateXY.playTogether(listOf(animatorX, animatorY))
        animateXY.start()

        animatorX.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if(lastElement){
                    alphabetList.removeAt(positionClicked)
                    //notifyDataSetChanged()
                }
            }
        })
    }

    private fun slideLeftAnimation(holder: ViewHolder, delay: Long, width: Int, lastElement: Boolean) {
        val x = -(width.toFloat())
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_X, x)
        animatorX.duration = 200
        animatorX.startDelay = delay
        animatorX.start()

        animatorX.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if(lastElement){
                    alphabetList.removeAt(positionClicked)
                    //notifyDataSetChanged()
                }
            }
        })
    }

    companion object {
        private var positionClicked: Int = 26
        private var delay = 0L
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textAlpha: TextView = itemView.findViewById(R.id.alphabet_char)
}
