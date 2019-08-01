package com.imamfrf.dicoding.submission5made.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imamfrf.dicoding.submission5made.DetailActivity
import com.imamfrf.dicoding.submission5made.MovieAdapter
import com.imamfrf.dicoding.submission5made.R
import com.imamfrf.dicoding.submission5made.db.MovieHelper
import com.imamfrf.dicoding.submission5made.model.Movie
import kotlinx.android.synthetic.main.fragment_favorite_movie.*


class FavoriteMovieFragment : Fragment() {

    lateinit var movieHelper: MovieHelper
    lateinit var favoriteMovies: ArrayList<Movie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieHelper = MovieHelper.getInstance(context)
        movieHelper.open()

        favoriteMovies = movieHelper.getAllMovie()

        recyclerV_favorite_movie.apply {
            adapter = MovieAdapter(
                favoriteMovies,
                context,
                object : MovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("extra_type", "movie_favorite")
                        intent.putExtra("fav_movie", favoriteMovies[position])
                        startActivity(intent)
                    }
                })

            val reverseLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,
                true)
            reverseLinearLayoutManager.stackFromEnd = true
            layoutManager = reverseLinearLayoutManager

        }
        progressBar_favorite_movie.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        favoriteMovies.clear()
        favoriteMovies.addAll(movieHelper.getAllMovie())
        recyclerV_favorite_movie.adapter?.notifyDataSetChanged()
    }
}
