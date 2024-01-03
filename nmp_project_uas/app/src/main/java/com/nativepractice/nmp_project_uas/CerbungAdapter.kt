package com.nativepractice.nmp_project_uas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nativepractice.nmp_project_uas.databinding.CardCerbungsBinding
import com.squareup.picasso.Picasso

class CerbungAdapter(val cerbungs:ArrayList<Cerbung>):RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>() {
    class CerbungViewHolder(val binding: CardCerbungsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CardCerbungsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CerbungViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cerbungs.size
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        val url = cerbungs[position].image_url
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener{ picasso, uri ,exception -> exception.printStackTrace()}
        Picasso.get().load(url).into(holder.binding.imgCerbung)

        with(holder.binding){
            txtTitle.text= cerbungs[position].judul
            txtAuthor.text=cerbungs[position].user_id.toString()
            txtDescription.text=cerbungs[position].description

        }
    }
}