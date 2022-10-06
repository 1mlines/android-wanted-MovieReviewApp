package com.preonboarding.moviereview.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.preonboarding.moviereview.boxoffice.compose.BoxOfficeItem
import com.preonboarding.moviereview.boxoffice.compose.DummyBoxOfficeItem
import com.preonboarding.moviereview.domain.model.BoxOffice.Companion.DUMMY_LIST
import com.preonboarding.moviereview.presentation.detailmovie.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val data by viewModel.dailyMovieBoxList.collectAsState()

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color.Magenta,
                                            Color.Red
                                        )
                                    )
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = "일일 박스 오피스",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    if (data.isNotEmpty()) {
                        itemsIndexed(
                            items = data,
                            key = { _, boxOffice ->
                                boxOffice.ranking
                            }
                        ) { index, boxOffice ->
                            when (index) {
                                data.lastIndex -> {
                                    BoxOfficeItem(boxOffice = boxOffice) {
                                        val intent = Intent(
                                            applicationContext,
                                            DetailMovieActivity::class.java
                                        )
                                        val json = Json.encodeToString(it)
                                        intent.putExtra("data", json)
                                        startActivity(intent)
                                    }
                                }
                                0 -> {
                                    BoxOfficeItem(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp), boxOffice = boxOffice) {
                                        val intent = Intent(
                                            applicationContext,
                                            DetailMovieActivity::class.java
                                        )
                                        val json = Json.encodeToString(it)
                                        intent.putExtra("data", json)
                                        startActivity(intent)
                                    }
                                }
                                else -> {
                                    BoxOfficeItem(modifier = Modifier.padding(bottom = 10.dp), boxOffice = boxOffice) {
                                        val intent = Intent(
                                            applicationContext,
                                            DetailMovieActivity::class.java
                                        )
                                        val json = Json.encodeToString(it)
                                        intent.putExtra("data", json)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    } else {
                        items(
                            items = DUMMY_LIST,
                            key = {
                                it.movieCd
                            }
                        ) {
                            DummyBoxOfficeItem()
                        }
                    }
                }
            }
        }
    }
}
