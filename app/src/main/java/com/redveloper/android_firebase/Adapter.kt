package com.redveloper.android_firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_barang.view.*

class Adapter(val items: ArrayList<Model>, val listener: AdapterView) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_barang, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position],listener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        lateinit var listener : AdapterView
        fun binding(model: Model, listener: AdapterView) {
            this.listener = listener

            itemView.tv_nama.setText(model.nama)
            itemView.tv_warna.setText(model.warna)

            itemView.layout_item.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v?.id){
                R.id.layout_item -> listener.onItemClick(adapterPosition)
            }
        }
    }

    interface AdapterView{
        fun onItemClick(position: Int)
    }
}