package com.preonboarding.moviereview.presentation.detailmovie

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.network.model.kobis.Audit
import com.preonboarding.moviereview.data.network.model.kobis.Genre
import com.preonboarding.moviereview.domain.model.Reviews

object BindingAdapter {
    @BindingAdapter("setPoster")
    @JvmStatic
    fun setPoster(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions().fitCenter())
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("setRankInten")
    fun setRankInten(textView: TextView, rankInten: String?) {
        val rankIntenToInt = (rankInten ?: "0").toInt()
        when {
            rankIntenToInt == 0 -> {
                textView.text = ""
            }
            rankIntenToInt < 0 -> {
                textView.text = "↓${-rankIntenToInt}"
            }
            rankIntenToInt > 0 -> {
                textView.text = "↑${rankIntenToInt}"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setRankOldAndNew")
    fun setRankOldAndNew(textView: TextView, rankOldAndNew: String?) {
        when (rankOldAndNew) {
            "OLD" -> textView.text = ""
            "NEW" -> textView.text = "NEW"
            else -> ""
        }
    }

    @JvmStatic
    @BindingAdapter("setGrade")
    fun setGrade(imageView: ImageView, audits: List<Audit>?) {
        var watchGradeNm = ""
        if (audits != null) {
            watchGradeNm = audits[0].watchGradeNm
        }
        when (watchGradeNm) {
            "전체관람가" -> {
                imageView.setImageResource(R.drawable.ic_detailmovie_all)
            }
            "12세이상관람가" -> {
                imageView.setImageResource(R.drawable.ic_detailmovie_12)
            }
            "15세이상관람가" -> {
                imageView.setImageResource(R.drawable.ic_detailmovie_15)
            }
            "청소년관람불가" -> {
                imageView.setImageResource(R.drawable.ic_detailmovie_18)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("genres", "showTm")
    fun setGenreAndTime(textView: TextView, genres: List<Genre>?, showTm: String?) {
        if (genres != null && showTm != null) {
            var genreToString = ""
            for (i in genres.indices) {
                Log.d("TAG", genres[i].genreNm)
                genreToString += genres[i].genreNm
                if (i != genres.size - 1) {
                    genreToString += "/"
                }
            }
            textView.text = "$genreToString | ${showTm}분"
        }
    }

    @JvmStatic
    @BindingAdapter("prdtYear", "openDt")
    fun setDate(textView: TextView, prdtYear: String?, openDt: String?) {
        if (openDt != null && prdtYear != null) {
            if (openDt != "") {
                var sb = StringBuffer(openDt)
                sb.insert(4, ".")
                sb.insert(7, ".")
                textView.text = "${prdtYear}년 제작 | $sb 개봉"
            } else {
                textView.text = "${prdtYear}년 제작"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setRatingText")
    fun setRatingText(textView: TextView, reviews: Reviews?) {
        if (reviews != null) {
            var rating = 0f
            for (review in reviews.reviews) {
                rating += review.star
            }
            textView.text = String.format("%.1f", (rating / reviews.reviews.size).toFloat())
        }
    }
}