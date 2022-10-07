package com.preonboarding.moviereview.presentation.common.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.preonboarding.moviereview.data.remote.model.Actors
import com.preonboarding.moviereview.data.remote.model.Audits
import com.preonboarding.moviereview.data.remote.model.Directors
import com.preonboarding.moviereview.data.remote.model.Genres
import java.lang.StringBuilder


@BindingAdapter("genreText")
fun TextView.bindGenreText(genres: List<Genres>?) {
    val sb = StringBuilder()
    genres?.forEach {
        sb.append(it.genreNm)
        sb.append(" ")
    }
    this.text = sb.toString()
}

@BindingAdapter("auditText")
fun TextView.bindAuditsText(audits: List<Audits>?) {
    val sb = StringBuilder()
    audits?.forEach {
        sb.append(it.watchGradeNm)
        sb.append("\n")
    }
    this.text = sb.toString()
}

@BindingAdapter("directorsText")
fun TextView.bindDirectorsText(directors: List<Directors>?) {
    val sb = StringBuilder()
    directors?.forEach {
        sb.append(it.peopleNm)
        sb.append("\n")
    }
    this.text = sb.toString()
}

@BindingAdapter("actorsText")
fun TextView.bindActorsText(actors: List<Actors>?) {
    val sb = StringBuilder()
    actors?.forEach {
        sb.append(it.peopleNm)
        sb.append("\n")
        sb.append(it.cast)
        sb.append("\n\n")
    }
    this.text = sb.toString()
}
