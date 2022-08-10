package com.hongri.kotlin.chapter4.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hongri.kotlin.R
import com.hongri.kotlin.chapter4.recycler.bean.Fruit
import kotlinx.android.synthetic.main.rv_item.view.*

/**
 * @author：hongri
 * @date：8/10/22
 * @description：
 */
class FruitAdapter(private val fruitList: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val place: TextView = view.findViewById(R.id.place)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(parent.context, "you clicked layout ${fruit.name}", Toast.LENGTH_LONG)
                .show()
        }

        viewHolder.itemView.name.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(parent.context, "you clicked name ${fruit.name}", Toast.LENGTH_LONG)
                .show()
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: FruitAdapter.ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.name.text = fruit.name
        holder.place.text = fruit.place
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}