package com.shoxrux.covid19.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoxrux.covid19.databinding.PreventItemBinding
import com.shoxrux.covid19.models.Article

class PreventAdapter (
    var list: List<Article>, var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PreventAdapter.Vh>() {

    inner class Vh(var malumotItemBinding:PreventItemBinding) :
        RecyclerView.ViewHolder(malumotItemBinding.root) {


        fun onBind(malumotlar: Article, position: Int) {
            Glide.with(malumotItemBinding.root).load(malumotlar.urlToImage).into(malumotItemBinding.rasm);
            malumotItemBinding.titlee.text = malumotlar.title
            malumotItemBinding.descr.text = malumotlar.description

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(PreventItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
        //dsdsdsdsdsdsdfdf
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemClick(malumotlar: Article)
    }


}