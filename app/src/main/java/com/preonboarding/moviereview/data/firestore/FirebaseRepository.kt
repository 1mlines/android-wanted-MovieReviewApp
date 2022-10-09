package com.preonboarding.moviereview.data.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.preonboarding.moviereview.domain.model.ReviewVo

class FirebaseRepository {

    val TAG = "FIREBASE_REPOSITORY"
    private var firestoreDB = FirebaseFirestore.getInstance()

    // save address to firebase
    fun saveReviewItem(review: ReviewVo): Task<Void> {
        //var
        var documentReference = firestoreDB.collection("reviews").document(review.movieCd)
            .collection(review.movieCd).document()
        return documentReference.set(review)
    }

    fun getSavedReview(movieCd: String): CollectionReference = firestoreDB.collection("reviews").document(movieCd).collection(movieCd)

    //TODO 시간상 나중에.
//    fun deleteReview(review: ReviewVO): Task<Void> {
//        val documentReference =  firestoreDB.collection("reviews").document(review.movieCd).collection(review.movieCd)
//            .document().
//        return documentReference.delete()
//    }

}