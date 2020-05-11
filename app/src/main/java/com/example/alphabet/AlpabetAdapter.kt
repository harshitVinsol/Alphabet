package com.example.alphabet

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
Class AddressAdapter to be used in RecyclerView for showing addresses having list of Address and context of Activity called from
 */
class AlpabetAdapter(
    var alphabetList: MutableList<Char>,
    private val callback: (Char, Int, ViewHolder) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    fun setAlphaList(list: MutableList<Char>) {
        alphabetList = list
        notifyDataSetChanged()
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
        val alpha = alphabetList[position]
        holder.textAlpha.text = alpha.toString()
        holder.itemView.setOnClickListener {
            callback.invoke(alphabetList[position], position, holder)
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textAlpha = itemView.findViewById<TextView>(R.id.aphabet_char)
}

private fun rotater(view: View) {
    val animator = ObjectAnimator.ofFloat(view, View.ROTATION_Y, -360f, 0f)
    animator.duration = 1000
    animator.start()
}