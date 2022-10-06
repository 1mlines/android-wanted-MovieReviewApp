# 원티드 프리온보딩 안드로이드
# 1팀 MovieReviewApp

## 팀원

<div align="center">
  <table style="font-weight : bold">
      <tr>
          <td align="center">
              <a href="https://github.com/tjrkdgnl">                 
                  <img alt="tjrkdgnl" src="https://avatars.githubusercontent.com/tjrkdgnl" width="80" />            
              </a>
          </td>
          <td align="center">
              <a href="https://github.com/gyurim">                 
                  <img alt="gyurim" src="https://avatars.githubusercontent.com/gyurim" width="80" />            
              </a>
          </td>
          <td align="center">
              <a href="https://github.com/014967 ">                 
                  <img alt="lsy524" src="https://avatars.githubusercontent.com/014967 " width="80" />            
              </a>
          </td>
          <td align="center">
              <a href="https://github.com/gksgpd97 ">                 
                  <img alt="hoyahozz" src="https://avatars.githubusercontent.com/gksgpd97 " width="80" />            
              </a>
          </td>
          <td align="center">
              <a href="https://github.com/INAH-PAK ">                 
                  <img alt="hoyahozz" src="https://avatars.githubusercontent.com/INAH-PAK " width="80" />            
              </a>
          </td>
      </tr>
      <tr>
          <td align="center">서강휘</td>
          <td align="center">박규림</td>
          <td align="center">김현국</td>
          <td align="center">한혜원</td>
          <td align="center"> 박인아</td>
      </tr>
  </table>
</div>

# 일일 박스 오피스 Screen - 김현국

## 구현
<img src="https://user-images.githubusercontent.com/62296097/194360182-702faf1f-3580-47fa-a0bb-dee531aa1cbd.gif" width="300" height="600" />

## 데이터 로드 로직
```kotlin
private fun getBoxOfficeList(key: String, date: String, code: String) {
        viewModelScope.launch {
            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(key, date, code)
            if (result?.boxOfficeResult != null) {
                // 첫번째로 영화 정보 가져오기 
                _dailyMovieBoxList.value = result.boxOfficeResult.dailyBoxOfficeList.map {
                    it.asModel()
                }
                
                result.boxOfficeResult.dailyBoxOfficeList.forEachIndexed { index, data ->
                    launch {
                        //launch를 이용하여 병렬처리
                        val response = movieInfosUseCase.getMovieInfo(
                            key = "d78dfcf081301ea1719d6d8d36756527",
                            movieCd = data.movieCd
                        )
                        // 영화의 영어이름을 가져옵니다. 
                        if (response != null) {
                            val poster = posterInfoUseCase.getPosterInfo(
                                response.movieInfoResult.movieInfo.movieNmEn,
                                key = "fa8d1585"
                            )

                            _dailyMovieBoxList.value = _dailyMovieBoxList.value.map { boxOffice ->
                                if (boxOffice.movieCd == data.movieCd) {
                                    // map으로 접근하는 movieCd와 위의 for문의 index가 같다면,
                                    // 포스터 정보를 업데이트 해주고, 
                                    //이미지 로드가 되었다는 flag인 isReady 를 true로 변경합니다.
                                    boxOffice.copy(
                                        moviePoster = poster?.Poster ?: "",
                                        isReady = true
                                    )
                                } else {
                                    boxOffice
                                }
                            }
                        }
                    }
                }
            }
        }
    }
```

## 데이터 분기처리
```kotlin
// In LazyColumn == RecyclerView
stickyHeader {}
if (data.isNotEmpty()) {
    when (index) {
        data.lastIndex -> {
         //BoxOfficeItem Composable
        }
        0 -> {
         //BoxOfficeItem Composable
        }
        else -> {
         //BoxOfficeItem Composable
        }
}
else{
    items(
        items = DUMMY_LIST,
        key = {
            it.movieCd
        }
        ) {
            DummyBoxOfficeItem()
        }
}
```

LazyColumn에 content Padding을 줄 수 있었지만, stickHeader에서도 padding이 생겨서 index에 따른 분기처리와

데이터가 없다면 Dummy 컴포저블 리스트가 보여지도록 하였습니다.

## 아이템 클릭처리
```kotlin
Row(
    modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            if (boxOffice.isReady) {
                onClick(boxOffice)
            }
    },
    //
```
<p>viewModel에서 이미지를 로드를 했을때 이미지가 없는 것을 확인을 했거나, url이 들어왔을 경우 
isReady 를 true를 해줌으로써,</p>
<p>
이미지가 있는 상태로 intent를 날려줄 수 있도록 처리했습니다. 
</p>
indication은 아이템 클릭시의 effect를 보여주는데, 이미지가 로드가 되지 않았을때

클릭이 되는 effect가 발생한다면, 오류로 판단될 수도 있을 것 같아서 클릭 effect를 제거하였습니다.

## 이미지 순위 표시
<img src="https://user-images.githubusercontent.com/62296097/194359726-a35763c9-8601-44c7-9429-b060ef32eb7b.jpeg">

```kotlin
// 코드 생략
SubcomposeAsyncImage() // 이미지 컴포저블
Box(
    modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter).background(
    brush = Brush.verticalGradient( // 이미지의 그라디언트 처리
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
                //
            )
        }
        if (boxOffice.rankType == "NEW") {
            Box(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(
                    text = boxOffice.rankType,
                    //
                )
            }
        }
    }
```
포스터의 배경이 흰색이여도 순위가 잘보여지게 하기 위해서, 이미지 컴포저블에 그라디언트를 겹쳐서 표현했습니다.

또한 영화가 새롭게 진입한 영화라면, New Text가 보여지도록 구현하였습니다.

## Convention

### Branch Convention

``` issue-<issue Number>/<branch name>  ```

- e.g) ``` issue-#1/Base Architecture ```


### Commit convention

``` [prefix]: <commit content> ```

- e.g) ``` feat: DAO 개발완료 ```

- e.g) ``` fix: room crash 수정 ```

- e.g) ``` refactor: MVVM 아키텍처 구조 리팩토링 ```

### Issue Convention
``` [prefix] 작업할 내용 ```

- e.g) ``` [feat] base architecture 생성 ```
- e.g) ``` [fix] room crash 수정 ```
- e.g) ``` [refactor] Sensor구조 일부 수정 ```

- 브랜치를 생성하기 전, github issue를 생성해주세요.
- branch 명의 issue number는 해당 issue Number로 지정합니다.

### PR Convention
``` [Issue-#number] PR 내용 ```

- e.g) ``` [Issue-#7] Timer 추가 ``` 

