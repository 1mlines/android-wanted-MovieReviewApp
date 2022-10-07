# 원티드 프리온보딩 안드로이드
- [원티드 프리온보딩 안드로이드](#원티드-프리온보딩-안드로이드)
  - [1. Project Introduction](#1-project-introduction)
  - [2. People](#2-people)
  - [3. Architecture](#3-architecture)
  - [4. Feature & Screen](#4-feature--screen)
    - [1. 프로젝트 세팅 및 카메라 갤러리 - 임수진](#1-프로젝트-세팅-및-카메라-갤러리---임수진)
    - [2. 첫번째 화면 - 권혁준](#2-첫번째-화면---권혁준)
    - [3. 두번째 화면 - 이서윤](#3-두번째-화면---이서윤)
    - [4. 세번째 화면 - 이현섭](#4-세번째-화면---이현섭)
    - [5. 영화 검색, 피그마 UI 디자인 - 이재성](#5-영화-검색-피그마-ui-디자인---이재성)
  - [****5. Technology Stack****](#5-technology-stack)
    - [기술스택 비교 분석](#기술스택-비교-분석)
      - [1. MVVM vs MVC & MVP](#1-mvvm-vs-mvc--mvp)
      - [2. StateFlow & SharedFlow vs LiveData](#2-stateflow--sharedflow-vs-livedata)
      - [3. Hilt vs Dagger & Koin](#3-hilt-vs-dagger--koin)
      - [4. Serialization vs Gson & Moshi](#4-serialization-vs-gson--moshi)
      - [5. Navigation vs FragmentManager Transaction](#5-navigation-vs-fragmentmanager-transaction)
  - [****6. Convention****](#6-convention)
    - [**1. Git Convention**](#1-git-convention)
    - [2. Commit Convention](#2-commit-convention)
    - [3. Coding Convention](#3-coding-convention)
  - [7. **How to run**](#7-how-to-run)

<br>

## 1. Project Introduction

[3주차 과제 링크](https://whispering-freedom-0c9.notion.site/81b8c1132008406e9edc62a4320616d7)

<img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white"> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white">

> 원티드 프리온보딩 3차 기업 과제
>

> 영화진흥위원회 Open API, OMDb API, Firebase를 이용한 영화 리뷰 어플리케이션 구현
>

> 박스오피스 목록, 영화 상세 정보, 평점 리뷰 표시, 평점 리뷰 작성 기능 구현
>



---

## 2. People

| 권혁준 | 이서윤 | 이재성 | 이현섭 | 임수진 |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| 첫번째 화면 (일간 박스 오피스) | 두번째 화면 (영화 상세 정보 및 리뷰) | 추가기능 (영화 검색) | 세번째 화면 (리뷰 작성) | 프로젝트 세팅 및 세번째 화면 (권한 + 카메라/갤러리) |

---

## 3. Architecture

> Clean Architecture + MVVM Pattern

<img width="400" alt="image" src="https://user-images.githubusercontent.com/85485290/193095723-50969ba7-19f1-46d7-8c91-c76cc3747f8b.png">

```
🔖
.
├── data
│   ├── api
│   ├── local
│   │   ├── repository
│   │   │   └── gallery
│   │   └── source
│   │       └── gallery
│   ├── paging
│   └── remote
│       ├── model
│       ├── repository
│       └── source
├── di
├── domain
│   ├── mapper
│   ├── model
│   ├── repository
│   │   ├── local
│   │   │   └── gallery
│   │   └── remote
│   └── usecase
└── presentation
    ├── common
    │   ├── adapter
    │   ├── base
    │   ├── const
    │   ├── customview
    │   └── extension
    └── ui
        ├── custom
        │   └── dialog
        ├── detail
        ├── home
        ├── main
        ├── review
        └── search

```
---

## 4. Feature & Screen

### 1. 프로젝트 세팅 및 카메라 갤러리 - 임수진

#### 1) Multiple API

- 오류 상황
<img width="600" alt="image" src="https://user-images.githubusercontent.com/85485290/194419038-08c9cc15-8c85-465b-80fd-3ba2eb64df7f.png">


- annotation 정의

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitKobis

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitOmdb
```

- RetrofitModule.kt

```kotlin
    // KOBIS API
    @Provides
    @Singleton
    @RetrofitKobis
    fun providesKobisApi(@RetrofitKobis retrofit: Retrofit): KobisMovieApi =
        retrofit.create(KobisMovieApi::class.java)

    // OMDB API
    @Provides
    @Singleton
    @RetrofitOmdb
    fun providesOMDbApi(@RetrofitOmdb retrofit: Retrofit): OmdbMovieApi =
        retrofit.create(OmdbMovieApi::class.java)

```

- RemoteRepositoryImpl
```kotlin
@Singleton
class RemoteRepositoryImpl @Inject constructor(
    @RetrofitKobis private val kobisMovieApi: KobisMovieApi,
    @RetrofitOmdb private val omdbMovieApi: OmdbMovieApi,
) : RemoteRepository {
```

---

| <img width="300" src="https://user-images.githubusercontent.com/85485290/194422556-f5bfb3be-351b-4952-866f-4841b2eee4fe.png" /> | <img width="300" src="https://user-images.githubusercontent.com/85485290/194422686-b70223a7-de82-4a66-a32f-7f4c4cbdcedb.png" /> | <img width="300" src="https://user-images.githubusercontent.com/85485290/194422771-417795ea-a991-4f3e-9020-5c7c5c351fb0.png" /> | <img width="300" src="https://user-images.githubusercontent.com/85485290/194423706-7f831343-39f6-4413-b6c2-d8ceb053116b.png" /> | <img width="300" src="https://user-images.githubusercontent.com/85485290/194422893-6bac5104-f1ac-4a4a-a477-820b6148cd1b.png" /> |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| 갤러리 권한체크 | 갤러리 띄우기 | 카메라 권한체크 | 카메라 촬영 | 이미지 선택 후 띄우기 |

#### 2) Custom Gallery

- ContentResolver로 갤러리 이미지 가져오기
- GalleryDataSourceImpl
```kotlin
            while(cursor.moveToNext() && cursor.position < PAGE_SIZE * pageNumber){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(IMAGE_ID))
                val filepath = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_NAME))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_DATE_ADDED))

                // 사진 경로 Uri 가져오기
                val uri = ContentUris.withAppendedId(IMAGE_URI, id)

                if(cursor.position >= (pageNumber - 1) * PAGE_SIZE) {
                    imageList.add(
                        GalleryImage(
                            id = id,
                            name = name,
                            filePath = filepath,
                            date = date,
                            imgUri = uri,
                            type = ItemType.IMAGE
                        )
                    )
                }
            }
```

- GalleryPagingSource
```kotlin
class GalleryPagingSource @Inject constructor(
    private val galleryDataSource: GalleryDataSource
): PagingSource<Int, GalleryImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryImage> {
        return try {
            val pageNumber = params.key ?: START_PAGE // curKey

            if(pageNumber != START_PAGE) delay(DELAY_MILLIS)

            val imageList = galleryDataSource.getAllImages(pageNumber = pageNumber)

            LoadResult.Page(
                data = imageList,
                prevKey = if (pageNumber == START_PAGE) null else pageNumber - 1,
                nextKey = if (imageList.isEmpty()) null else pageNumber + (params.loadSize / PAGE_SIZE),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    
    ...
    
 }
```

- GalleryRepositoryImpl
```kotlin
class GalleryRepositoryImpl @Inject constructor(
    private val galleryDataSource: GalleryDataSource
) : GalleryRepository {

    override fun getAllImages(): Flow<PagingData<GalleryImage>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            GalleryPagingSource(
                galleryDataSource = galleryDataSource
            )
        }.flow
    }
    
    ...
    
}
```

- GalleryViewModel & GalleryDialogFragment

- 페이징 데이터 Caching
```kotlin

    // ViewMdoel
    fun getAllImages(): Flow<PagingData<GalleryImage>> {
        return getGalleryImageUseCase.invoke().cachedIn(viewModelScope)
    }
    
    // Fragment
    lifecycleScope.launchWhenStarted {
        galleryViewModel.getAllImages().collect {
            galleryPagingAdapter.submitData(it)
        }
    }

```

#### 3) Camera Intent

- open camera

```kotlin
    private fun openCamera() {
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        getResult.launch(intent)
    }

```

- bitmap to uri
```kotlin
      getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data?.extras?.get("data")
                
                if (data != null) {
                    val uri = getImageUri(
                        requireContext(),
                        data as Bitmap
                    )

                    galleryViewModel.setCameraImage(uri = uri)
                }
            }
        }
```

---

### 2. 첫번째 화면 - 권혁준

일간 박스 오피스에 대한 정보를 담고 있는 페이지입니다. 현재 순위, 전날과 비교했을 때 순위의 변동 여부, 누적 관계 수, 개봉한 날짜 등을 확인할 수 있습니다. 

```kotlin
private fun observeGetMovieList() {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            homeViewModel.checkHomeState.collectLatest { state ->
                when(state) {
                    is HomeState.Loading -> {
                        binding.rvList.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is HomeState.Failure -> {
                        binding.rvList.isVisible = false
                        binding.progressBar.isVisible = false
                    }
                    is HomeState.Success -> {
                        binding.rvList.isVisible = true
                        binding.progressBar.isVisible = false
                        val data = state.data
                        val boxOfficeResult = data.boxOfficeResult
                        val dailyBoxOfficeList = boxOfficeResult.dailyBoxOfficeList
                        homeAdapter.submitList(dailyBoxOfficeList)
                    }
                    is HomeState.Empty -> {
                    }
                }
            }
        }
    }
}
```
stateFlow : 상태 관리를 용이하게 할 수 있게 되었습니다. 화면이 나오지 않는 경우 progressBar를 사용해서 로딩 중인 것을 알 수 있게끔 구현하였습니다. 

```kotlin
private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<BoxOfficeMovie>(){
            override fun areItemsTheSame(oldItem: BoxOfficeMovie, newItem: BoxOfficeMovie): Boolean {
                return oldItem.movieNm == newItem.movieNm
            }
            override fun areContentsTheSame(oldItem: BoxOfficeMovie, newItem: BoxOfficeMovie): Boolean {
                return oldItem == newItem
            }
        }
```
listAdapter : DiffUtil 이라는 유틸리티 클래스를 사용해서 리스트 간의 차이를 비교한 후, 변경된 아이템만 UI에 업데이트 해줄 수 있습니다. 


[dailyMovie.webm](https://user-images.githubusercontent.com/70066242/194413092-36d679df-26bf-4512-99f9-da707dd082ab.webm)

---

### 3. 두번째 화면 - 이서윤
<img src="https://user-images.githubusercontent.com/110798031/194440337-17d9383b-0821-4e47-b933-6118f87c3145.jpg">
---

### 4. 세번째 화면 - 이현섭

---
| case | 원래 db | 리뷰 작성 | 바뀐 db | 바뀐 stroage |
| --- | --- | --- | --- | --- |
| 화면 | ![image](https://user-images.githubusercontent.com/35682233/194368886-12e88dfd-002b-426c-823d-8babc333258a.png) | ![KakaoTalk_20221007_013736112](https://user-images.githubusercontent.com/35682233/194369884-9d94e0a1-7315-497f-b837-15c1815fc909.jpg) | ![image](https://user-images.githubusercontent.com/35682233/194370237-2747deb1-cecc-486d-97f4-72b924064977.png) | ![image](https://user-images.githubusercontent.com/35682233/194370273-0eb9fef0-0d16-47a5-9d6c-ed089e13f6ba.png) |
## 리뷰가져오기

```kotlin
private fun findReview(){
        database = FirebaseDatabase.getInstance().reference
        val ref = database.database.getReferenceFromUrl(FIRE_BASE_URL)
        // child 안에 무비 id 가져와야한다.

        ref.child("1").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.value==null){//리뷰가 없을때

                    }
                    else{                        //리뷰가 있을때
                        detailViewModel.searchReviewMovieList(1)//리뷰 가져오기

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            }
        )
    }
```

realtime 데이터베이스를 가져와서 해당 영화의 리뷰의 정보를 가져온다.

스냅샷을 통해 비엇는지 안비엇는지 확인한다.

```kotlin
fun searchReviewMovieList(movieId : Int) {
        viewModelScope.launch {
            remoteRepository.searchReviewInfo(
                movieId = movieId
            )
                .collect {
                    Timber.tag("ReviewModel").e(it.toString())
                }
        }
    }
```

```kotlin
interface FireBaseApi {

    @GET("{movieId}.json")
    suspend fun searchReviewInfo(
        @Path("movieId") movieId: Int,
    ): Map<String, Review>
}
```

다음 코드와 같이 네트워크 통신처럼 데이터를 가져온다.

아쉬운점→어차피 스냅샷으로 판단하는데 굳이 또 네트워크 통신을 할 필요는 없는거 같다.

## 리뷰 작성

이미지 업로드

```kotlin
private fun uploadPhoto(uri: Uri, successHandler: (String) -> Unit, errorHandler: () -> Unit) {
        val fileName = "${System.currentTimeMillis()}.png" // 이미지 파일 이름
        fbStorage.reference.child("article/photo")
            .child(fileName) // storage 에 article/photo/fileName 경로로 저장
            .putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // 업로드 성공시
                    fbStorage.reference.child("article/photo").child(fileName).downloadUrl
                        .addOnSuccessListener { uri ->
                            // 다운로드 성공시
                            successHandler(uri.toString()) // successHandler 실행
                        }
                        .addOnFailureListener {
                            // 다운로드 실패시
                            errorHandler() // errorHandler 실행
                        }
                } else {
                    // 업로드 실패시
                    errorHandler()
                }
            }
    }
```

먼저 storage에 이미지를 저장시킨다. 업로드 완료후 해당 이미지 storage링크를 realtime db에 업로드 한다.

```kotlin
private fun uploadArticle(
        nickname: String,
        content: String,
        password: Int,
        imageUrl: String,
        rate: Float,
    ) {
        val model = Review(
            content = content,
            imageUrl = imageUrl,
            nickName = nickname,
            password = password,
            star = rate)

        //child에서 detail에서 넘어올때 영화id를 같이 넘겨주세요
        val ref = database.database.getReferenceFromUrl(FIRE_BASE_URL).child("1").push()
        ref.setValue(model)
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            getString(R.string.review_sucess_snack_bar_text),
            Snackbar.LENGTH_SHORT)
            .show()
        navigateUp()
    }
```

db에 업로드 할때 번호로 인덱스를 주면 같이 업로드할 경우 똑같은 번호가 있을 수 있으므로

push()를 통해 시간에 따른 임이의 string값을 인덱스로 준다.


### 5. 영화 검색, 피그마 UI 디자인 - 이재성
* 영화 제목 query에 대한 Instant Search를 위해 Flow debounce API 활용
* 검색 결과 페이징을 통한 무한 스크롤 구현

<br>

* TODO
  * 검색 결과 UiState 처리 
  * 영화 검색 결과 상세정보 연동 작업 (순위를 띄울 수 없기 때문에 별도 상세 페이지 추가 예정)
  * UI 수정 작업
  
#### Instant Search
``` kotlin
// ViewExtension.kt
fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query
}
```
``` kotlin
// SearchFragment.kt
private fun observeSearchStateFlow() {
    viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            binding.svSearch.getQueryTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    if (query.isEmpty()) {
                        viewModel.searchMovie("")
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    viewModel.searchMovie(query)
                }
                .collectLatest {
                    pagingAdapter.submitData(it)
                }
        }
    }
}
```

* `debounce(timeout: Long)`
  * `SearchView#getQueryTextChangeStateFlow`를 통해 이벤트가 발생하면 `timeout`만큼 기다렸다가 가장 최신의 value를 DownStream Flow로 전달한다.
  
* `filter { }`
  * Upstream Flow로부터 전달받는 query 중에서 불필요한 네트워크 호출을 피하기 위해 빈 문자열 query는 필터링한다.
  
* `distinctUntilChanged()`
  * 동일한 query에 대한 반복 호출을 방지한다.

* `flatMapLatest { }`
  * `viewModel#searchMovie`에 대한 가장 최신의 결과만 반환하고 이전 데이터와 같은 나머지 데이터는 무시한다.
  
<br>

#### Paging
* data/api
``` kotlin
// KobisMovieApi.kt
@GET("movie/searchMovieList.json")
suspend fun getMovieList(
    @Query("key") key: String = KOBIS_API_KEY,
    @Query("movieNm") movieKrName: String = "",
    @Query("curPage") page: String
): MovieListResponse
```

* data/source
``` kotlin
// MovieListDataSource.kt
interface MovieListDataSource {

    suspend fun getMovieList(movieName: String, page: String): MovieListResponse
}

// MovieListDataSourceImpl.kt
@Singleton
class MovieListDataSourceImpl @Inject constructor(
    @RetrofitKobis private val api: KobisMovieApi
) : MovieListDataSource {

    override suspend fun getMovieList(movieName: String, page: String): MovieListResponse {
        return api.getMovieList(movieKrName = movieName, page = page)
    }
}
```

* data/pagingsource
```kotlin
class MovieSearchResultPagingSource(
    private val dataSource: MovieListDataSource,
    private val movieName: String
) : PagingSource<Int, MovieSearchInfo>() {
    override fun getRefreshKey(state: PagingState<Int, MovieSearchInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearchInfo> {
        val page = params.key ?: STARTING_PAGE

        if (page != STARTING_PAGE) delay(100L)

        return try {
            val searchResult =
                dataSource.getMovieList(movieName = movieName, page = page.toString())
                    .mapToMovieSearchInfo()

            LoadResult.Page(
                data = searchResult,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = if (searchResult.isEmpty()) null else page + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }


    companion object {
        private const val STARTING_PAGE = 1
    }
}
```
* `PagingSource#getRefreshKey`를 통해 데이터 로드시 사용될 Key 값을 가져옴
* `PagingSource#load`를 통해 api에 접근하여 prevKey와 nextKey에 맞게 데이터를 반환

* repository
``` kotlin
// domain/MovieRepository.kt
interface MovieRepository {
    fun getMovieListByMovieName(movieName: String): Flow<PagingData<MovieSearchInfo>>
}

// data/MovieRepositoryImpl.kt
class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieListDataSource
) : MovieRepository {

    override fun getMovieListByMovieName(movieName: String) = Pager(
        config = PagingConfig(
            pageSize = ITEM_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = ITEM_PER_PAGE
        ),
        pagingSourceFactory = { MovieSearchResultPagingSource(dataSource, movieName) }
    ).flow


    companion object {
        private const val ITEM_PER_PAGE = 10
    }
}
```

* Usecase
``` kotlin
class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieName: String) =
        movieRepository.getMovieListByMovieName(movieName).flowOn(Dispatchers.Default)
}
```

* ViewModel
``` kotlin
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    ...

    fun searchMovie(movieNameQuery: String) =
        searchMovieUseCase.invoke(movieNameQuery).cachedIn(viewModelScope)

    ...
}
```

#### Figma 레이아웃 디자인
[Figma 링크](https://www.figma.com/file/7JgePu0qKgx3LcN14sOMT7/%EC%9B%90%ED%8B%B0%EB%93%9C-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-3%EC%A3%BC%EC%B0%A8-%EA%B3%BC%EC%A0%9C?node-id=25%3A2)
#### 결과

https://user-images.githubusercontent.com/51078673/194398262-33ad7e2e-2635-4287-8a7f-b451a24fd510.mov


---

## ****5. Technology Stack****

<img src="https://user-images.githubusercontent.com/51078673/194324436-4ceefc87-d19a-44ff-a22b-10583bce6587.png">


<br>

### 기술스택 비교 분석
#### 1. MVVM vs MVC & MVP
# MVVM

### 구조

> **Model**
> 
- MVC, MVP와 동일하다.
- DB 사용이나 Retrofit을 통한 백엔드 API 호출 등을 주로 수행한다.
- 데이터, 비즈니스 로직, 서비스 클라이언트 등으로 구성
- 실제적 데이터

> **View**
> 
- 기본적인 것들은 MVC, MVP와 동일 / Activity/Fragment가 View에 포함
- ViewModel을 관찰하여 상태 변화가 관찰되면 UI 갱신
- Data Binding을 위해 gradle과 xml을 수정
- 이를 통해 View는 ViewModel에 의해 Model과 유연한 binding이 가능

> **ViewModel**
> 
- 기본적으로 View에 종속되지 않는다.
- Model을 래핑하고 View에 필요한 Observable data를 준비한다.
- View가 Model에 Event를 전달할 수 있도록 Hook(BindingAdapter)을 준비한다.

### 장점

- Command Pattern과 Data Binding을 사용해 View와 ViewModel 사이의 의존성도 없앴다.
- 각각의 부분은 독립적이기 때문에 모듈화하여 개발할 수 있다.

### **단점**

- ViewModel의 설계가 어렵다.
- View가 변수와 표현식 모두에 Binding될 수 있으므로 갈수록 presentation logic이 늘어나 XML이 방대해진다. 이를 방지하려면 항상 ViewModel에서 직접 값을 가져오는 것이 좋다.

## MVC

### 구조

> **Model**
> 
- 어플리케이션에서 사용되는 data, data를 처리한다.
- View에 의존적이지 않기 때문에 재사용 가능.

> **View**
> 
- 사용자에게 보여지는 UI를 나타낸다.
- Model로부터 data를 받아 사용자에게 보여준다.

> **Controller**
> 
- 사용자의 입력을 받고 처리한다.
- 주로 Activity나 Fragment로 표현한다.
- Model의 data 변화에 따라 View를 선택한다.

### 장점

- 가장 널리 사용되는 패턴 (보편적으로 많이 사용)
- 가장 단순

### **단점**

- View와 Model사이의 의존성이 높다.
- Controller가 Android API에 종속되어 테스트가 어렵다.
- View를 변경하면 Controller도 변경해야 한다.
- 많은 코드들이 Controller에 집중되면 성능이 저하되고 유지보수가 어려워진다.

# MVP

### 구조

> **Model**
> 
- 어플리케이션에서 사용되는 데이터, 데이터를 처리하는 부분

> **View**
> 
- 사용자에게 보여지는 UI 부분
- 기본적인 것들은 MVC와 동일하나, Activity/Fragment가 View에 포함된다.

> **Presenter**
> 
- View에서 요청한 정보를 Model을 가공하여 View에 전달(View와 Model의 다리)
- Controller와의 차이점은 Interface라는 점이다.

### **동작**

- 사용자의 Action이 View에 들어온다.
- View는 데이터를 Presenter에 요청
- Presenter는 Model에 데이터 요청
- Model은 Presenter에서 요청 받은 데이터 응답
- Presenter는 View에게 데이터 응답
- View는 Presenter가 응답한 데이터를 이용해 화면을 나타낸다.

### **특징**

- Presenter는 View, Model의 인스턴스를 가지고 있어 둘을 연결한다.
- Presenter와 View는 1:1
- 단순 Interface이기 때문에 테스트가 용이하고 모듈화/유연성 문제가 해결되었다.

### 장점

- View와 Model의 의존성이 없다.(Presenter를 통해 데이터를 전달 받기 때문)

### **단점**

- View와 Presenter 사이 의존성이 높다.
- 어플리케이션이 복잡해 질수록 View와 Presenter 사이 의존성이 강해진다.
- Controller와 같이 코드가 집중되면 성능이 저하되고 유지보수가 어려워진다.
#### 2. StateFlow & SharedFlow vs LiveData
* ㅁㅁㅁㅁㅁ
#### 3. Hilt vs Dagger & Koin
- 의존성 주입(di)?

주입은 클래스 외부에서 객체를 생성하여 해당 객체를 클래스 내부에 주입하는 것입니다. 이를 통해서 클래스 간에 의존도를 낮춰줄 수 있습니다. 의존도가 낮아지게 된다면, 특정 클래스가 변경이 되어도 다른 클래스가 변경의 영향을 적게 받게 됩니다.

- 그렇다면 di를 사용함으로써 얻을 수 있는 이점은 무엇이있을까요?

1. Unit Test가 용이해진다.
2. 코드의 재활용성을 높여준다.
3. 객체 간의 의존성(종속성)을 줄이거나 없엘 수 있다.
4. 객체 간의 결합도이 낮추면서 유연한 코드를 작성할 수 있다.


- hilt / dagger / koin 비교
  - koin : Kotlin DSL로 만들어졌으며, hilt / dagger / koin 중 가장 러닝커브가 낮다고 평가되고 있습니다. 그렇지만 koin은 런타임에 의존성 주입이 진행되기 때문에 App의 성능이 저하된다는 단점이 있습니다. 그렇기에 규모가 큰 프로젝트에서는 koin을 쓰지 않는 것을 권장하고 있습니다. 
  - dagger : 가장 높은 러닝커브를 가지고 있습니다. 컴파일 시 의존성 주입이 시작됩니다. 그 결과 문제가 발생할 경우 컴파일 시점에 에러를 발생하기에 상대적으로 높은 안정성을 갖습니다. 그렇지만 가장 높은 러닝커브를 가지고 있으며, 이로 인하여 개발 환경 셋팅하는 것이 굉장히 어렵습니다.  
  - hilt : dagger를 기반으로 만들어진 DI framework. dagger의 장점을 가져오면서, 사용자가 좀 더 사용하기 편하게 개량되었습니다.

- 왜 hilt를 선택? 

위에서 언급한 것처럼 hilt 사용을 통해 dagger보다 낮은 러닝커브를 가지고 있으며, koin과 다르게 컴파일 시 의존성 주입이 시작되기에 좀 더 높은 안정성을 가질 수 있습니다. 

#### 4. Serialization vs Gson & Moshi

서버 통신 시 데이터 간 직렬화/역직렬화가 필요하다.

- Why Serialization? ✨
  - data class의 default value를 무시하지 않는다.
  - non-null value 임에도 불구하고, 서버에서 null을 줬을 때 default value를 지정할 수 있다.
  - list 형식의 데이터도 default argument로 처리 가능하다.
  - 성능적으로 코틀린 친화적이다. (feat: gson의 reflection)


Ex) Gson vs Moshi vs Serialization 비교

- Entity
```kotlin
data class Entitiy(
  val status: String = "entity",
  val data: Data = Data()
)

data calss Data(
  val id: Long = 1000,
  val list: List<String> = listOf()
)
```

- Response 1
```
{
  status : null,
  data : {
    "id" : System.currentTimeMs
  }
}
```

- Result 1
```kotliln
// Gson
Entity(
  status=null,
  data=Data(id=12918943, list=[])
)

// Moshi
Entity(
  status=,
  data=Data(id=12918943, list=[])
)

// Gson
Entity(
  status="entity",
  data=Data(id=12918943, list=[])
)

```

- Response 2
```
{
  status : "success",
  data : {
    "id" : System.currentTimeMs,
    "list" : null
  }
}
```

- Result 2
```kotliln
// Gson
Entity(
  status="success",
  data=Data(id=12918943, list=null)
)

// Moshi
com.squareup.moshi.JsonDataException:
Non-null value 'list' was null at $.data.list

// Gson
Entity(
  status="success",
  data=Data(id=12918943, list=[])
)

```


#### 5. Navigation vs FragmentManager Transaction
* ㅁㅁㅁㅁㅁ

---

## ****6. Convention****

### **1. Git Convention**

[Git Convention](https://www.notion.so/a1dc40cca82c4e4ca3fb3f97f25cd562)

### 2. Commit Convention

[Commit Convention](https://www.notion.so/82e40ee38c0b4d249951cbf808b9394d)

### 3. Coding Convention

[Coding Convention](https://www.notion.so/1df208ab2a594dc0ad76633a7f84637c)

---

## 7. **How to run**

1. Clone this repository

```
git clone https://github.com/sujin-kk/android-wanted-MovieReviewApp.git
```

2. Type in your terminal

```
git checkout main
```

3. Run this project in Android Studio
