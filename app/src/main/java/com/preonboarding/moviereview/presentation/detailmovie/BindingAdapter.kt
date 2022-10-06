package com.preonboarding.moviereview.presentation.detailmovie

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.network.model.kobis.Audit
import com.preonboarding.moviereview.data.network.model.kobis.Genre

object BindingAdapter {
    @BindingAdapter("setPoster")
    @JvmStatic
    fun setPoster(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions().fitCenter())
            .into(imageView)
    }

    @BindingAdapter("setRankInten")
    fun setRankInten(textView: TextView, rankInten: String) {
        val rankIntenToInt = rankInten.toInt()
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

    @BindingAdapter("setRankOldAndNew")
    fun setRankOldAndNew(textView: TextView, rankOldAndNew: String) {
        when (rankOldAndNew) {
            "OLD" -> textView.text = ""
            "NEW" -> textView.text = "NEW"
        }
    }

    @BindingAdapter("setGrade")
    fun setGrade(imageView: ImageView, audits: List<Audit>) {
        when (audits[0].watchGradeNm) {
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

    @BindingAdapter("genres", "showTm")
    fun setGenreAndTime(textView: TextView, genres: List<Genre>, showTm: String) {
        var genreToString = genres.joinToString("/")
        textView.text = "$genreToString | ${showTm}분"
    }

    @BindingAdapter("prdtYear", "openDt")
    fun setDate(textView: TextView, prdtYear: String, openDt: String) {
        var sb = StringBuffer(openDt)
        sb.insert(4, ".")
        sb.insert(7, ".")
        textView.text = "${prdtYear}년 제작 | $sb 개봉"
    }

}