package com.example.flixster_clone

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"

class MovieAdapter(private val context: Context, private val movies: List<Movie>, private val setView: String)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // Expensive operation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    //Cheap simply bind data to existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            if (setView == "portrait") {
                //dontanimate() prevents image loading to placeholder size
                //Glide.with(context).load(movie.posterImageUrl).placeholder(R.drawable.ic_baseline_hourglass_top_24).dontAnimate().into(ivPoster)
                Glide.with(context).load(movie.posterImageUrl).into(ivPoster)
            } else if (setView == "landscape") {
                //Glide.with(context).load(movie.backdropImageUrl).placeholder(R.drawable.ic_baseline_hourglass_top_24).dontAnimate().into(ivPoster)
                Glide.with(context).load(movie.backdropImageUrl).into(ivPoster)
            }

        }

        override fun onClick(p0: View?) {
            //get notified of movie that was clicked
            val movie =  movies[adapterPosition]
            //Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
            //use intent system to navigate to new activity
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("MOVIE_EXTRA", movie)
            context.startActivity(intent)
        }
    }
}
