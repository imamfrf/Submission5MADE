package com.imamfrf.dicoding.submission5made.helper

import android.database.Cursor
import com.imamfrf.dicoding.submission5made.db.DatabaseContract.MovieColumns.Companion.BACKDROP
import com.imamfrf.dicoding.submission5made.db.DatabaseContract.MovieColumns.Companion.DESCRIPTION
import com.imamfrf.dicoding.submission5made.db.DatabaseContract.MovieColumns.Companion.ID
import com.imamfrf.dicoding.submission5made.db.DatabaseContract.MovieColumns.Companion.POSTER
import com.imamfrf.dicoding.submission5made.db.DatabaseContract.MovieColumns.Companion.RELEASE_DATE
import com.imamfrf.dicoding.submission5made.db.DatabaseContract.MovieColumns.Companion.SCORE
import com.imamfrf.dicoding.submission5made.db.DatabaseContract.MovieColumns.Companion.TITLE
import com.imamfrf.dicoding.submission5made.model.Movie


class MappingHelper {

    fun mapCursorToArrayList(moviesCursor: Cursor): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()
        while (moviesCursor.moveToNext()) {
            val id = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(ID))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE))
            val releaseDate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE_DATE))
            val score = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(SCORE))
            val description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DESCRIPTION))
            val poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER))
            val backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BACKDROP))
            moviesList.add(Movie(id, title, releaseDate, score, description, poster, backdrop))
        }
        return moviesList
    }
}