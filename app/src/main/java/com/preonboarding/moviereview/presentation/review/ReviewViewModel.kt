package com.preonboarding.moviereview.presentation.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.preonboarding.moviereview.data.firestore.FirebaseRepository
import com.preonboarding.moviereview.domain.model.ReviewVo
import kotlinx.coroutines.launch


class ReviewViewModel : ViewModel() {

    private val TAG = "FIRESTORE_VIEW_MODEL"
    private val firebaseRepository = FirebaseRepository()
    private var _savedReviews: MutableLiveData<List<ReviewVo>> = MutableLiveData()
    val savedReviews: LiveData<List<ReviewVo>> = _savedReviews

    lateinit var etName: MutableLiveData<String>
    lateinit var etPassword: MutableLiveData<String>
    lateinit var spinnerStar: MutableLiveData<String>
    lateinit var etContents: MutableLiveData<String>

    // save review to firebase
    fun saveReviewToFirebase(review: ReviewVo) {
        firebaseRepository.saveReviewItem(review).addOnFailureListener {
            Log.e(TAG, "Failed to save Address!")
        }
    }

    fun getReviewList(movieCd: String) {
        viewModelScope.launch {
            var reviewList: CollectionReference = firebaseRepository.getSavedReview(movieCd)

            reviewList.addSnapshotListener(EventListener<QuerySnapshot> { value, error ->
                if (error != null) {
                    Log.e(TAG, "Listen failed  error :  .$error")
                    return@EventListener
                }
                if (value != null) {
                    val savedReviewList: ArrayList<ReviewVo> = arrayListOf()
                    for (doc in value) {
                        val reviewItem = doc.toObject(ReviewVo::class.java)
                        savedReviewList.add(reviewItem)
                    }
                    _savedReviews.value = savedReviewList
                }
            })
        }
    }
}