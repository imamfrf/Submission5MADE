package com.imamfrf.dicoding.submission5made

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imamfrf.dicoding.submission5made.model.Movie
import com.imamfrf.dicoding.submission5made.model.TVShow
import kotlinx.android.synthetic.main.activity_search_result.*
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast


class SearchResultActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvShowAdapter: TVShowAdapter
    private lateinit var query: String
    val EXTRA_QUERY = "extra_query"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

            query = intent.getStringExtra(EXTRA_QUERY)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val arrayAdapter = ArrayAdapter<String>(
            applicationContext, android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.search_filter_array)
        )

        spinner_search_result.adapter = arrayAdapter

        spinner_search_result.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                progressBar_search_result.visibility = View.VISIBLE
                tv_not_found_search_result.visibility = View.GONE

                if (spinner_search_result.selectedItemPosition == 0) {
                    searchMovie()
                }
                else if (spinner_search_result.selectedItemPosition == 1) {
                    searchTVShow()
                }
            }
        }

    }

    fun searchMovie() {
        mainViewModel.movies.observe(this@SearchResultActivity, getMovies)
        mainViewModel.searchMoviesTVShows("movie", query)
        tv_not_found_search_result.visibility = View.GONE

    }

    fun searchTVShow() {
        mainViewModel.tvShows.observe(this@SearchResultActivity, getTVShows)
        mainViewModel.searchMoviesTVShows("tv", query)
        tv_not_found_search_result.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchManager != null) {
            val searchView = (menu?.findItem(R.id.action_search_main)?.actionView) as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = resources.getString(R.string.search_hint)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    this@SearchResultActivity.query = query.toString()

                    progressBar_search_result.visibility = View.VISIBLE

                    Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                    if (spinner_search_result.selectedItemPosition == 0) {
                        searchMovie()
                    }
                    else if (spinner_search_result.selectedItemPosition == 1) {
                        searchTVShow()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_settings){
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    private val getMovies =
        Observer<ArrayList<Movie>> { movies ->
            if (movies != null) {
                movieAdapter = MovieAdapter(movies, applicationContext, object : MovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(applicationContext, DetailActivity::class.java)
                        intent.putExtra(DetailActivity().EXTRA_MOVIE_ID, movies[position].id)
                        intent.putExtra(DetailActivity().EXTRA_TYPE, "movie")
                        startActivity(intent)
                    }
                })


                recyclerV_search_result.apply {
                    adapter = movieAdapter

                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                progressBar_search_result.visibility = View.GONE
            } else {
                movieAdapter.listItems.clear()
                progressBar_search_result.visibility = View.GONE
                tv_not_found_search_result.visibility = View.VISIBLE
            }
        }

    private val getTVShows =
        Observer<ArrayList<TVShow>> { tvShows ->
            if (tvShows != null) {
                tvShowAdapter = TVShowAdapter(tvShows, applicationContext, object : TVShowAdapter.OnItemClicked {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(applicationContext, DetailActivity::class.java)
                        intent.putExtra(DetailActivity().EXTRA_TV_SHOW_ID, tvShows[position].id)
                        intent.putExtra(DetailActivity().EXTRA_TYPE, "tv")
                        startActivity(intent)
                    }
                })

                recyclerV_search_result.apply {
                    adapter = tvShowAdapter

                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                progressBar_search_result.visibility = View.GONE

            } else {
                tvShowAdapter.listItems.clear()
                progressBar_search_result.visibility = View.GONE
                tv_not_found_search_result.visibility = View.VISIBLE
            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("query", query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState?.getString("query").toString()

    }
}
