package com.nativepractice.nmp_project_uas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.nativepractice.nmp_project_uas.databinding.CardCerbungsBinding
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class CerbungAdapter(val cerbungs:ArrayList<Cerbung>, val context: Context):RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>() {
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
            txtAuthor.text=cerbungs[position].username
//            txtDescription.text=cerbungs[position].description

            buttonRead.setOnClickListener{
                val activity : AppCompatActivity = context as AppCompatActivity
                val detail = Bundle()
                detail.putString("index_cerbung",cerbungs[position].id.toString())
                detail.putString("judul_cerbung",cerbungs[position].judul)
                detail.putString("description_cerbung",cerbungs[position].description)
                detail.putString("num_likes_cerbung",cerbungs[position].num_likes.toString())
                detail.putString("user_id_cerbung",cerbungs[position].user_id.toString())
                detail.putString("image_url_cerbung",cerbungs[position].image_url)
                detail.putString("genre_id_cerbung",cerbungs[position].genre_id.toString())
                detail.putString("username_cerbung",cerbungs[position].username)

                val loc = CerbungReadFragment()
                loc.arguments = detail
                activity.supportFragmentManager.beginTransaction().replace(com.google.android.material.R.id.container,loc).addToBackStack(null).commit()
            }
        }
    }
}