package com.preonboarding.moviereview.presentation.review

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.ActivityReviewBinding
import com.preonboarding.moviereview.domain.model.ReviewVo
import java.util.*

class ReviewActivity : AppCompatActivity() {

    var db : FirebaseFirestore? = null
    val firestore = Firebase.firestore
    val TAG = "파이어베아스"

    lateinit var reviewViewModel : ReviewViewModel

    lateinit var savedReviews :List<ReviewVo>
    var review :ReviewVo? = null

    var saveReview : ReviewVo ? = null
    companion object{
        var movieCd : String? = "20112207"
        var star : Int ? = null
    }


    lateinit var binding: ActivityReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review)


        val spinnerList = resources.getStringArray(R.array.spinner_star)

        val secondIntent = intent
        movieCd = secondIntent.getStringExtra("movieCd")

        db = FirebaseFirestore.getInstance()

        reviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        setSpinner()
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position != 0) star = Integer.parseInt(spinnerList[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@ReviewActivity, "아이템 서ㅏㄴ택해라", Toast.LENGTH_SHORT).show()
            }

        }

//        reviewViewModel.getSavedReviews().observe(this, androidx.lifecycle.Observer {
//            savedReviews = it
//        })

        binding.btnSubmit.setOnClickListener { clickSubmitBtn3() }

    }

    private fun setSpinner(){
        val spinnerList = resources.getStringArray(R.array.spinner_star)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerList)
        binding.spinner.adapter = adapter
    }

    // 리뷰 작성 Submit 버튼
    private fun clickSubmitBtn3()  {

        var r = movieCd?.let {
            ReviewVo(
                binding.etContects.text.toString(),
                "이미지 구현 예정",
                it,
                binding.etName.text.toString(),
                binding.etPw.text.toString(),
                Timestamp(Date()),
                10f
            )
        }

        if (r != null) {
            reviewViewModel.saveReviewToFirebase(r)
        }
        Log.i("ddddddd","시도시도시도도도ㅗ도ㅗ돋ㄷ")
    }


//
//    private fun clickSubmitBtn(){
//
//        Log.d(TAG, "ㅇㅇㅇ 버튼 클릭")
//
//        // 파베 데이터베이스에 저장
//        var name = binding.etName.text.toString()
//        var password = binding.etPw.text.toString()
//        //   var star : Int = Integer.parseInt(binding.etStar.text.toString())
//        var contents : String = binding.etContects.text.toString()
//        var time : String = System.currentTimeMillis().toString()
//
//        var review = ReviewVo(contents,"dd","영화1", "nam","password", Timestamp.now(),8)
//        // var review = ReviewVO(contents,"dd","영화1", name,password,time,star)
//
//        val user = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815
//        )
//        db!!.collection("users").document("movie1").set(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID:" + "성공!!!")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//        firestore.collection("users")
//            .add(review)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//    }
//
//    }
}