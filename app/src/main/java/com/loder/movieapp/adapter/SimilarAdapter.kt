package com.loder.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loder.movieapp.R
import com.loder.movieapp.data.model.Result
import com.loder.movieapp.databinding.ItemViewPagerSimilarBinding
import com.loder.movieapp.util.Constants
import com.squareup.picasso.Picasso

class SimilarAdapter(private val movieList: List<Result>) : RecyclerView.Adapter<SimilarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewPagerSimilarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(Constants.IMAGE_PATH + movieList.get(position).posterPath)
            .placeholder(R.drawable.user_placeholder)
            .resize(300, 300).onlyScaleDown()
            .into(holder.binding.imagePagerSimilar)

//        holder.binding.cardPagerSimilar.setOnClickListener { it ->
//            val idMovie = movieList.get(position).id.toString()
//            val intent = Intent(it.context, MovieDetailActivity::class.java)
//            intent.putExtra("idMovie", idMovie)
//            it.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(val binding: ItemViewPagerSimilarBinding) : RecyclerView.ViewHolder(binding.root)
}
