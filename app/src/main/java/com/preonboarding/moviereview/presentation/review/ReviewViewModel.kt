package com.preonboarding.moviereview.presentation.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.preonboarding.moviereview.data.firestore.FirebaseRepository
import com.preonboarding.moviereview.domain.model.ReviewVo
import com.preonboarding.moviereview.domain.usecase.ReviewListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class ReviewViewModel : ViewModel(){

    private val TAG = "FIRESTORE_VIEW_MODEL"
    private val firebaseRepository = FirebaseRepository()
    private var _savedReviews : MutableLiveData<List<ReviewVo>> = MutableLiveData()
    val savedReviews : LiveData<List<ReviewVo>> = _savedReviews

    lateinit var etName : MutableLiveData<String>
    lateinit var etPassword : MutableLiveData<String>
    lateinit var spinnerStar : MutableLiveData<String>
    lateinit var etContents : MutableLiveData<String>

    // save review to firebase
    fun saveReviewToFirebase(review: ReviewVo){
        firebaseRepository.saveReviewItem(review).addOnFailureListener {
            Log.i(TAG,"Failed to save Address!")
        }
    }

    fun getReviewList(movieCd : String){
        viewModelScope.launch {
            var reviewList : CollectionReference = firebaseRepository.getSavedReview(movieCd)

            reviewList.addSnapshotListener(EventListener<QuerySnapshot>{ value, error ->
                if(error != null){
                    Log.i(TAG, "Listen failed  error :  .$error")
                    return@EventListener
                }
                if(value != null){
                    val savedReviewList :  ArrayList<ReviewVo> = arrayListOf()
                    for ( doc in value ){
                        val reviewItem = doc.toObject(ReviewVo::class.java)
                        savedReviewList.add(reviewItem)
                    }
                    _savedReviews.value = savedReviewList
                }
            })
        }
    }


    fun btnClick() {
        var data = ReviewActivity.movieCd?.let {
            ReviewActivity.star?.let { star ->
                ReviewVo(
                    etContents.toString(),
                    "예시",
                    it,
                    etName.toString(),
                    etPassword.toString(),
                    Timestamp(Date()),
                    star.toFloat()
                )
            }
        }
        Log.i("ddddddddddd","zmflflfdnwelifnwileked")
        if (data != null) {
            saveReviewToFirebase(data)
        }
    }


//    // get realtime updates from firebase regarding saved review
//    @JvmName("getSavedReviews1")
//    fun getSavedReviews(): MutableLiveData<List<ReviewVo>> {
//        ReviewActivity.movieCd?.let {
//            firebaseRepository.getSavedReview(it).addSnapshotListener(EventListener<QuerySnapshot>{ value, error ->
//                if ( error != null  ){
//                    Log.i(TAG, "Listen failed  error :  .$error")
//                    return@EventListener
//                }
//                value?.let {
//                    var savedReviewList : MutableList<ReviewVo> = mutableListOf()
//                    for (doc in value) {
//                        var reviewItem = doc.toObject(ReviewVo::class.java)
//                        savedReviewList.add(reviewItem)
//                    }
//                    _savedReviews.value = savedReviewList
//                }
//            })
//        }
//        return _savedReviews
//    }

    //TODO delete an review from firebase
    // delete
//    fun deleteReview(review: ReviewVO){
//        firebaseRepository.deleteReview(review).addOnFailureListener {
//            Log.e(TAG,"Failed to delete Address")
//        }
}