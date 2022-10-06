package com.preonboarding.data.repositoryimpl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.preonboarding.di.DispatcherModule
import com.preonboarding.domain.model.Review
import com.preonboarding.domain.repository.FirebaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val fbRDB: FirebaseDatabase,
    @DispatcherModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : FirebaseRepository {

    override suspend fun uploadReview(title: String, review: Review) {
        val reviewContent = review.toMapContent()
        fbRDB.getReference(title)
            .child(review.nickname)
            .setValue(reviewContent)
            .addOnFailureListener {
                throw it
            }
    }

    override fun getReviewList(title: String) = callbackFlow<List<Review>> {
        fbRDB.getReference(title)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.children.map {
                        Timber.d("getREVIEW")
                        Review(
                            it.key.toString(),
                            it.child("password").getValue().toString(),
                            it.child("rating").getValue().toString().toFloat(),
                            it.child("content").getValue().toString(),
                            it.child("imageUri").getValue().toString(),
                            it.child("date").getValue().toString(),
                        )
                    })
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel()
                }
            })
        awaitClose()
    }.flowOn(dispatcherIO)

    override suspend fun deleteReview(title: String, review: Review) {
        fbRDB.getReference(title).child(review.nickname).removeValue()
            .addOnFailureListener {
                throw it
            }
    }
}