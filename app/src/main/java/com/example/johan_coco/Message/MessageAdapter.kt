package com.example.johan_coco.Message

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.johan_coco.R

class MessageAdapter(private val context: Context, private val dataSource: List<MessageModel>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.item_message, parent, false)
        
        val tvName = rowView.findViewById<TextView>(R.id.tvName)
        val tvMessage = rowView.findViewById<TextView>(R.id.tvMessage)
        val ivUser = rowView.findViewById<ImageView>(R.id.ivUser)
        
        val message = dataSource[position]
        
        tvName.text = message.name
        tvMessage.text = message.message
        
        // Membersihkan request sebelumnya untuk mencegah gambar tertukar saat scroll
        Glide.with(context).clear(ivUser)

        if (message.image.startsWith("http")) {
            Glide.with(context)
                .asBitmap() // Memaksa Glide memuat sebagai Bitmap (menghindari error decoder video)
                .load(message.image)
                .placeholder(R.drawable.user) 
                .error(R.drawable.user)
                .circleCrop() 
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Simpan di cache agar tidak load ulang terus menerus
                .into(ivUser)
        } else {
            Glide.with(context)
                .asBitmap()
                .load(R.drawable.user)
                .circleCrop()
                .into(ivUser)
        }
        
        return rowView
    }
}