package com.nativepractice.nmp_project_uas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.nativepractice.nmp_project_uas.databinding.CardCerbungsBinding
import com.nativepractice.nmp_project_uas.databinding.CardReadCerbungsBinding

class ParagrafAdapter (val paragrafs:ArrayList<Paragraf>, val id:Int): RecyclerView.Adapter<ParagrafAdapter.ParagrafViewHolder>(){

    class ParagrafViewHolder(val binding: CardReadCerbungsBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParagrafViewHolder {
        val binding = CardReadCerbungsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ParagrafViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParagrafViewHolder, position: Int) {
        with(holder.binding){

            txtAuthor.text=paragrafs[position].teller.toString()
            txtParagrafRead.text=paragrafs[position].paragraf

        }
    }

    override fun getItemCount(): Int {
        return paragrafs.size
    }

}