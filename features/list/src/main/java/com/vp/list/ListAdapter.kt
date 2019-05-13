package com.vp.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.vp.list.model.ListItem

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var listItems: MutableList<ListItem>? = null
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val listItem = listItems?.get(position)

        if (listItem?.poster != null && NO_IMAGE != listItem.poster) {
            val density = holder.image.resources.displayMetrics.density
            GlideApp
                    .with(holder.image)
                    .load(listItem.poster)
                    .override((300 * density).toInt(), (600 * density).toInt())
                    .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder)
        }
    }

    override fun getItemCount(): Int {
        return listItems!!.size
    }

    fun setItems(listItems: MutableList<ListItem>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    fun clearItems() {
        listItems?.clear()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        if (onItemClickListener != null) {
            this.onItemClickListener = onItemClickListener
        } else {
            this.onItemClickListener = null
        }
    }

     inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var image: ImageView

        init {
            itemView.setOnClickListener(this)
            image = itemView.findViewById(R.id.poster)
        }

        override fun onClick(v: View) {
            onItemClickListener?.onItemClick(listItems?.get(adapterPosition)?.imdbID)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(imdbID: String?)
    }

    companion object {
        private val NO_IMAGE = "N/A"
    }
}
