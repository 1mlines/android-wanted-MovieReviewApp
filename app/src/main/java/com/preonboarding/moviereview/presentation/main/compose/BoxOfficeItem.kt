package com.preonboarding.moviereview.boxoffice.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.domain.model.BoxOffice

/**
 * @Created by 김현국 2022/10/05
 */

@Composable
fun BoxOfficeItem(
    modifier: Modifier = Modifier,
    boxOffice: BoxOffice,
    onClick: (BoxOffice) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = modifier.padding(start = 5.dp, end = 5.dp).shadow(
            elevation = 3.dp,
            shape = RoundedCornerShape(5.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (boxOffice.isReady) {
                        onClick(boxOffice)
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(100.dp).fillMaxHeight()
            ) {
                if (boxOffice.moviePoster != "") {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(100.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        model = boxOffice.moviePoster,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    ) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading) {
                            Box(
                                modifier = Modifier.width(100.dp).fillMaxHeight().placeholder(
                                    true,
                                    highlight = PlaceholderHighlight.shimmer()
                                )
                            )
                        } else if (state is AsyncImagePainter.State.Success) {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter).background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0x00E4E8F5),
                                Color(0xFF788098)
                            )
                        ),
                        shape = RoundedCornerShape(5.dp)
                    )
                ) {
                    Box(modifier = Modifier.padding(start = 3.dp).align(Alignment.BottomStart)) {
                        Text(
                            text = boxOffice.ranking,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    if (boxOffice.rankType == "NEW") {
                        Box(
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text(
                                text = boxOffice.rankType,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "영화 제목 : ${boxOffice.movieName}"
                )
                Text(
                    modifier = Modifier,
                    text = "개봉일 : ${boxOffice.movieOpen}"
                )
                Text(text = "관객 수 : ${boxOffice.audiAcc}")
            }

            if (boxOffice.rankInten.toInt() < 0) {
                // 이미지 추가
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.width(24.dp).height(24.dp),
                        painter = painterResource(id = R.drawable.ic_down),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(text = boxOffice.rankInten)
                }
            } else if (boxOffice.rankInten.toInt() == 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.width(24.dp).height(24.dp),
                        painter = painterResource(id = R.drawable.ic_equal),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.width(24.dp).height(24.dp),
                        painter = painterResource(id = R.drawable.ic_up),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(text = boxOffice.rankInten)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBoxOfficeItem() {
    MaterialTheme {
        BoxOfficeItem(
            boxOffice = BoxOffice.DUMMY
        ) {
        }
    }
}
