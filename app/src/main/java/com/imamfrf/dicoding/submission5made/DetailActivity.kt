package com.imamfrf.dicoding.submission5made

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.imamfrf.dicoding.submission5made.db.MovieHelper
import com.imamfrf.dicoding.submission5made.db.TVShowHelper
import com.imamfrf.dicoding.submission5made.model.Movie
import com.imamfrf.dicoding.submission5made.model.TVShow
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    lateinit var movie: Movie
    lateinit var tvShow: TVShow
    lateinit var movieHelper: MovieHelper
    lateinit var tvShowHelper: TVShowHelper
    lateinit var type: String
    var isFavorite = false
    var isLoaded = false

    val EXTRA_TYPE = "extra_type"
    val EXTRA_MOVIE_ID = "extra_movie_id"
    val EXTRA_TV_SHOW_ID = "extra_tv_show_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar_detail.visibility = View.VISIBLE
        type = intent.getStringExtra(EXTRA_TYPE)

        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()

        tvShowHelper = TVShowHelper.getInstance(applicationContext)
        tvShowHelper.open()


        val params = RequestParams()
        params.put("api_key", BuildConfig.TMDB_API_KEY)
        params.put("language", "en-US")
        val client = AsyncHttpClient()

        //get parcelable object from intent
        when (type) {
            "movie" -> {
                val id = intent.getStringExtra(EXTRA_MOVIE_ID)
                val url = BuildConfig.URL_DETAIL_MOVIE+"/$id"

                Log.d("TES123", "id = " + id)
                client.get(url, params, object : JsonHttpResponseHandler() {

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                        try {
                            movie = Movie(response as JSONObject)
                            tv_release_date_title_detail.text = getString(R.string.release_date)
                            tv_score_title.text = getString(R.string.user_score)
                            tv_description_title_detail.text = getString(R.string.overview)

                            tv_title_detail.text = movie.title
                            tv_release_date_detail.text = movie.releaseDate
                            tv_score_value_detail.text = movie.score
                            tv_description_detail.text = movie.description

                            Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + movie.poster)
                                .into(img_poster_detail)

                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }

                        isLoaded = true
                        progressBar_detail.visibility = View.GONE
                        isFavorite = isFavorited()
                        invalidateOptionsMenu()
                    }

                    override fun onFailure( statusCode: Int,
                                            headers: Array<out Header>?,
                                            throwable: Throwable?,
                                            errorResponse: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, errorResponse?.getString("status_message"), Toast.LENGTH_SHORT).show()
                    }

                })
            }
            "tv" -> {
                val id = intent.getStringExtra(EXTRA_TV_SHOW_ID)
                val url = BuildConfig.URL_DETAIL_TV_SHOW+"/$id"

                client.get(url, params, object : JsonHttpResponseHandler() {

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                        try {
                            tvShow = TVShow(response as JSONObject)
                            tv_release_date_title_detail.text = getString(R.string.first_air_date)
                            tv_score_title.text = getString(R.string.user_score)
                            tv_description_title_detail.text = getString(R.string.overview)

                            tv_title_detail.text = tvShow.title
                            tv_release_date_detail.text = tvShow.releaseDate
                            tv_score_value_detail.text = tvShow.score
                            tv_description_detail.text = tvShow.description

                            Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + tvShow.poster)
                                .into(img_poster_detail)

                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }

                        progressBar_detail.visibility = View.GONE

                        isLoaded = true
                        progressBar_detail.visibility = View.GONE
                        isFavorite = isFavorited()
                        invalidateOptionsMenu()

                    }

                    override fun onFailure(statusCode: Int,
                                           headers: Array<out Header>?,
                                           throwable: Throwable?,
                                           errorResponse: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, errorResponse?.getString("status_message"), Toast.LENGTH_SHORT)

                    }

                })
            }
            "movie_favorite" -> {
                movie = intent.getParcelableExtra<Movie>("fav_movie")
                tv_release_date_title_detail.text = getString(R.string.release_date)
                tv_score_title.text = getString(R.string.user_score)
                tv_description_title_detail.text = getString(R.string.overview)

                tv_title_detail.text = movie.title
                tv_release_date_detail.text = movie.releaseDate
                tv_score_value_detail.text = movie.score
                tv_description_detail.text = movie.description
                Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + movie.poster)
                    .into(img_poster_detail)
                progressBar_detail.visibility = View.GONE

                isLoaded = true
                progressBar_detail.visibility = View.GONE
                isFavorite = isFavorited()
                invalidateOptionsMenu()

            }
            "tv_show_favorite" -> {
                tvShow = intent.getParcelableExtra<TVShow>("fav_tv_show")
                tv_release_date_title_detail.text = getString(R.string.first_air_date)
                tv_score_title.text = getString(R.string.user_score)
                tv_description_title_detail.text = getString(R.string.overview)

                tv_title_detail.text = tvShow.title
                tv_release_date_detail.text = tvShow.releaseDate
                tv_score_value_detail.text = tvShow.score
                tv_description_detail.text = tvShow.description
                Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + tvShow.poster)
                    .into(img_poster_detail)
                progressBar_detail.visibility = View.GONE

                isLoaded = true
                progressBar_detail.visibility = View.GONE
                isFavorite = isFavorited()
                invalidateOptionsMenu()

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_add_to_favorite -> {
                if (isFavorite)
                {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
                    if (type == "movie" || type == "movie_favorite"){
                        val result = movieHelper.deleteMovie(movie.id)
                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.remove_movie_from_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }
                    else if (type == "tv" || type == "tv_show_favorite"){
                        val result = tvShowHelper.deleteTVShow(tvShow.id)
                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.remove_tv_from_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }



                }
                else{
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)

                    if (type == "movie" || type == "movie_favorite"){
                        val result = movieHelper.insertMovie(movie)

                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.add_movie_to_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }
                    else if (type == "tv" || type == "tv_show_favorite"){
                        val result = tvShowHelper.insertTVShow(tvShow)

                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.add_tv_show_to_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }

                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun isFavorited(): Boolean{
        if (type == "movie" || type == "movie_favorite"){
            if (movieHelper.isMovieFavorited(movie.id)){
                return true
            }
        }
        else if(type == "tv" || type == "tv_show_favorite"){
            if (tvShowHelper.isTvShowFavorited(tvShow.id)){
                return true
            }
        }

        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (isLoaded){
            menu?.getItem(0)?.isVisible = true

        }
        if (isFavorite)
        {
            //menu?.getItem(0)?.isVisible = true
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        }
        else{
            //menu?.getItem(0)?.isVisible = true
            menu?.getItem(0)?.icon = ContextCompat
                .getDrawable(this, R.drawable.ic_favorite_border_white_24dp)

        }
        return super.onPrepareOptionsMenu(menu)
    }

}
