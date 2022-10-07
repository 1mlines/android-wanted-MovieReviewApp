package com.preonboarding.moviereview.presentation.detailmovie

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.network.model.kobis.Actor
import com.preonboarding.moviereview.databinding.ItemDetailmovieReviewBinding
import com.preonboarding.moviereview.domain.model.ReviewVo
import com.preonboarding.moviereview.presentation.review.ReviewActivity
import java.text.SimpleDateFormat

class ReviewListAdapter (val context: Context, var reviews: MutableLiveData<List<ReviewVo>>, var movieCd : String) : RecyclerView.Adapter<ReviewListAdapter.VH>() {

    val t_dateFormat = SimpleDateFormat("yyyy-MM-dd")

    inner class VH( itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemDetailmovieReviewBinding = ItemDetailmovieReviewBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_detailmovie_review, parent , false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val review = if ( reviews.value == null){
             ReviewVo(" 리뷰를 달아주세요. ","",movieCd,"","", Timestamp.now(),0f)
        } else {
            reviews.value!![position]
        }

        holder.binding.textviewReviewName.text = review.name

        var time = review.time?.toDate()
        holder.binding.textviewReviewDate.text = t_dateFormat.format(time)
        holder.binding.textviewReviewContent.text = review.contents
        // holder.binding.img.s = review.image



//        holder.itemView.setOnClickListener{
//            val intent: Intent = Intent(context, ReviewActivity::class.java)
//            intent.putExtra("movieCd",movieCd) // 영화 고유번호
//            Log.i(" 아이템 뷰 클릭 ", "아이템뷰 클릭ㄱㄱㄱ 여긴 인텐ㅇ트트틑ㅌ" )
//            context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int = reviews.value!!.size

}



