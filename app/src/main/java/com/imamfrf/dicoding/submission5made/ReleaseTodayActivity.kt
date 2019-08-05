package com.imamfrf.dicoding.submission5made

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imamfrf.dicoding.submission5made.model.Movie
import kotlinx.android.synthetic.main.activity_release_today.*

class ReleaseTodayActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_today)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.movies.observe(this, getMovies)
        mainViewModel.setMoviesTVShows("movie_today")

        supportActionBar?.title = getString(R.string.today_release)

        progressBar_release_today.visibility = View.VISIBLE

    }

    private val getMovies =
        Observer<ArrayList<Movie>> { movies ->
            if (movies != null) {
                mAdapter = MovieAdapter(movies, applicationContext,  object : MovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int){
                        val intent = Intent(applicationContext, DetailActivity::class.java)
                        intent.putExtra(DetailActivity().EXTRA_MOVIE_ID, movies[position].id)
                        intent.putExtra(DetailActivity().EXTRA_TYPE, "movie")
                        startActivity(intent)
                    }
                })

                recyclerV_release_today.apply {
                    adapter = mAdapter

                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                progressBar_release_today.visibility = View.GONE

            }
        }
}
