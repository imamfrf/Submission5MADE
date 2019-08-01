package com.imamfrf.dicoding.submission5made.home


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imamfrf.dicoding.submission5made.DetailActivity
import com.imamfrf.dicoding.submission5made.MainViewModel
import com.imamfrf.dicoding.submission5made.MovieAdapter
import com.imamfrf.dicoding.submission5made.R
import com.imamfrf.dicoding.submission5made.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity as FragmentActivity).get(MainViewModel::class.java)
        mainViewModel.movies.observe(this, getMovies)
        mainViewModel.setMoviesTVShows("movie")

        progressBar_movie.visibility = View.VISIBLE

    }

    private val getMovies =
        Observer<ArrayList<Movie>> { movies ->
            if (movies != null) {
                mAdapter = MovieAdapter(movies, context as Context,  object : MovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int){
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity().EXTRA_MOVIE_ID, movies[position].id)
                        intent.putExtra(DetailActivity().EXTRA_TYPE, "movie")
                        startActivity(intent)
                    }
                })

                recyclerV_movie.apply {
                    adapter = mAdapter

                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                progressBar_movie.visibility = View.GONE

            }
        }


}
