## Members

<div align="center">
<table style="font-weight : bold">
  <tr>
    <td align="center">
      <a href="[https://github.com/KimHance](https://github.com/KimHance)">
        <img alt="김현수" src="https://avatars.githubusercontent.com/KimHance" width="80" />
      </a>
    </td>
    <td align="center">
      <a href="[https://github.com/dudwls901](https://github.com/dudwls901)">
        <img alt="김영진" src="https://avatars.githubusercontent.com/dudwls901" width="80" />
      </a>
    </td>
    <td align="center">
      <a href="[https://github.com/YellowC-137](https://github.com/YellowC-137)">
        <img alt="황준성" src="https://avatars.githubusercontent.com/YellowC-137" width="80" />
      </a>
    </td>
    <td align="center">
      <a href="[https://github.com/hoyahozz](https://github.com/hoyahozz)">
        <img alt="김정호" src="https://avatars.githubusercontent.com/hoyahozz" width="80" />
      </a>
    </td>
</tr>

<tr>
<td align="center">김현수</td>
<td align="center">김영진</td>
<td align="center">황준성</td>
<td align="center">김정호</td>
</tr>
</table>
</div>

## Summary

- 영화진흥위원회 API를 사용하여 Box Office 목록, 영화 상세 정보를 표시한다.
- 영화 상세 정보의 영화 영문 이름을 이용하여 OMDB API를 통해 영화 포스터 정보를 표시한다.
- 해당 영화에 대한 리뷰를 별명, 작성일자, 내용, 사진 등을 Database에 저장하고 수정, 조회, 삭제 등이 가능하다.

## 기술 스택

<img width="630" alt="image" src="https://user-images.githubusercontent.com/85336456/194576300-378b65a2-b631-40f8-b147-8c69bbb5fe3f.png">

## 기술 선택 이유

- Hilt
    - Koin은 런타임에 Service Locating을 통해 인스턴스를 동적으로 주입하기 때문에 Crash가 발생할 수 있는 반면 Hilt는 컴파일 시점에 의존성을 주입하기 때문에 컴파일 시점에서 에러를 검출할 수 있다.
    - Dagger2 기반으로 만들어진 Hilt는 런타임 성능, 확장성 및 Android Studio 지원 등의 이점을 누리며 표준화된 Dagger2 사용법 제시, 보일러플레이트 코드 감소, 설정 간소화 등의 장점이 있다.
- Coroutines
    - 경량 쓰레드라고 불리는 Coroutines은 Thread와 Rx방식에 비해 suspend라는 개념의 구현으로 CPU 자원을 더 효율적으로 사용한다.
    - 비동기 로직을 동기 코드처럼 쉽게 짤 수 있다.
- Flow
    - LiveData는 비동기 데이터 스트림을 지원하지 않고, Clean Architecture 관점의 Domain Layer에서 사용하기 적합하지 않은 한계가 있다.
    - 이에 대한 대응으로 Data, Domain Layer에선 Flow, Presentation Layer에선 LiveData를 사용하여 해결할 수 있었고, SharedFlow, StateFlow의 등장으로 Presentation Layer에서도 LiveData를 Flow로 대체하여 프로젝트 전체에서 Flow만 사용 가능하다.
    - StateFlow를 이용하여 RxJava와 유사한 동작을 수행할 수 있다.
- Navigation
    - 화면 전환에 대한 표준화된 Animation 리소스를 적용하여 쉽게 애니메이션을 적용할 수 있다.
    - 적은 코드로 Fragment 스택 관리를 할 수 있다.
    - 가시화된 Graph를 통해 화면 이동을 쉽게 관리할 수 있다.
    - SafeArgs를 이용해 Fragment 간의 데이터 전달은 type-safe하게 전달할 수 있다.
- Timber
    - 기존 Log에서 필수로 작성해야 했던 TAG가 Optional하다.
    - 간단한 설정으로 Release에서의 로그 출력을 방지할 수 있다.
- Glide
    - 일반적으로 이미지 로드 성능이 가장 좋다.
    - 사용 편의성이 좋다.
    - Picasso에 비해 캐시 다운로드 속도가 빠르다.
    - 기본 Bitmap포맷을 이용할 경우 Picasso에 비해 메모리 용량을 적게 사용한다.
- Retrofit2, OkHttp3
    - HttpUrlConnection을 직접 연결하지 않아도 된다.
    - 일반적으로 AsyncTask, Volley에 비해 성능이 좋다.
    - Gson 등의 컨버터를 이용하여 JSON을 쉽게 역직렬화할 수 있다.
    - 어노테이션과 OkHttp3의 Interceptor 등을 이용하여 쉽게 네트워킹 로직을 구현할 수 있다.
- Firebase-Realtime Database
    - 단순한 데이터 모델, 간단한 쿼리, 대규모 업데이트가 아닌 소규모 업데이트를 자주 사용할 땐 FireStore가 아닌 Realtime Database 사용을 고려할 수 있다.
- Paging3
    - Recyclerview의 Scroll Event를 이용하여 직접 구현하는 것에 비해 편리한 장점이 많다.
        - 페이징된 데이터의 캐싱
        - 요청 중복 제거 기능 제공
        - RecyclerView 어댑터가 자동으로 데이터를 요청(PagingDataAdapter)
        - Coroutine, Flow 지원
        - 새로고침, 재시도, 오류 처리 지원

# 김영진

## 맡은 역할

- 모듈 분리
- Library Dependency 셋팅 (kotlin - dsl)
- Base 설계 (Clean Architecture, MVVM Design Pattern)
- Hilt Module 셋팅

### 모듈 분리

- Clean Architecture 레이어 별로 모듈 분리
- 빌드 시간 단축
- 모듈마다 필요한 Dependency만 사용
- 좀 더 뚜렷한 관심사 분리
- app module
    - Application, google-services.json 등 관리
    - Hilt Module 구성
- data module
    - Data(Realtime Database, Server)와 상호작용하는 코드들로 구성
- Domain
    - Data와 Presentation을 연결하고 두 계층간의 Model 변경,UseCase를 정의하는 코드들로 구성
- Presentation
    - UI 데이터를 관리하고 State에 따라 화면을 구성

### Library Dependency 셋팅(kotlin - dsl)

- groovy → kts
    - 코드의 자동 완성
    - 한 파일 내에서 Dependency 통합 관리
        - 각 모듈에서 필요한 Dependency 변수 참조
    - 오류 코드 강조 표시
    - 소스코드와 동일한 언어 사용

```kotlin
// buildSrc/Versions.kt
object Versions{
		const val HILT = "2.42"
		const val RETROFIT = "2.9.0"
}

// buildSrc/Dependencies.kt
object Hilt {
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val HILT_CORE = "com.google.dagger:hilt-core:${Versions.HILT}"
}

object Retrofit {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
}

// data/build.gradle.kts
dependencies {
    implementation(project(":domain"))

		//Retrofit
    implementation(Retrofit.RETROFIT)
    implementation(Retrofit.CONVERTER_GSON)

		//hilt
    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)
}
```

### Hilt Module 셋팅

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*
    * Retrofit
    * */

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KobisRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OMDBRetrofit

		@Provides
    @Singleton
    @KobisRetrofit
    fun provideKobisRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_KOBIS)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @OMDBRetrofit
    fun provideOMDBRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_OMDB)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

		/*
    * HttpClient
    * */

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /*
    * Converter
    * */

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /*
    * Interceptor
    * */

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

		/*
    * Api
    * */

    @Provides
    @Singleton
    fun provideBoxOfficeApiService(
        @KobisRetrofit retrofit: Retrofit,
    ): BoxOfficeService {
        return retrofit.create(BoxOfficeService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieInfoApiService(
        @KobisRetrofit retrofit: Retrofit,
    ): MovieInfoService {
        return retrofit.create(MovieInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideOMDBApiService(
        @OMDBRetrofit retrofit: Retrofit,
    ): PosterService {
        return retrofit.create(PosterService::class.java)
    }

}
```

## 아쉬운점

핵심 기능에 기여하지 못한 점은 아쉬웠으나, 요구사항, 팀원들과 협의한 내용 등을 바탕으로 아키텍처를 설계하고 기능 개발을 위한 Base를 구축하는 과정도 재밌었습니다. 

Gradle 관리를 처음 kts로 해봤는데, 이후에 재사용할 수 있는 템플릿으로 사용하기 위해 신경쓰면서 작업했던 부분이 만족스러웠습니다.

# 김정호

## 맡은역할

![Screenshot_1665153187 중간](https://user-images.githubusercontent.com/85336456/194578743-eb31ee9b-4a21-4c64-8773-db4d8c2bfc1f.jpeg)

- 첫 번째 페이지 구현
    - 순차적 API 호출
    - 네비게이션 구현

### 순차적으로 API 호출

```kotlin
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    @DispatcherModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : MovieRepository {
    override suspend fun getBoxOfficeList(): List<Movie> {

        val result = ArrayList<Movie>()

        withContext(dispatcherIO) {
            val responseOfficeListJob = async { movieRemoteDataSource.getBoxOfficeList() }

            val officeList: List<DailyBoxOffice>

            when (val responseOfficeList = responseOfficeListJob.await()) {
                is Result.Success -> {
                    officeList = responseOfficeList.data.boxOfficeResult.dailyBoxOfficeList
                }
                is Result.Error -> {
                    return@withContext
                }
            }

            for (boxOffice in officeList) {
                launch {
                    val tmp = Movie.EMPTY.copy()

                    val responseMovieDetailJob =
                        async { movieRemoteDataSource.getMovieInfo(boxOffice.movieCd) }

                    when (val responseMovieDetail = responseMovieDetailJob.await()) {
                        is Result.Success -> {
                            val detailInfo = responseMovieDetail.data.movieInfoResult.movieInfo

                            tmp.apply {
                                rank = boxOffice.rank.toInt()
                                rankInten = boxOffice.rankInten.toInt()
                                rankOldAndNew = boxOffice.rankOldAndNew
                                name = boxOffice.movieNm
                                enName = detailInfo.movieNmEn
                                openDt = boxOffice.openDt
                                audiAcc = boxOffice.audiAcc
                                prdtYear = detailInfo.prdtYear
                                showTm = detailInfo.showTm
                                genreNm = detailInfo.genres.map { it.genreNm }
                                directorNm = detailInfo.directors.map { it.peopleNm }
                                peopleNm = detailInfo.actors.map { it.peopleNm }
                                watchGradeNm = detailInfo.audits.map { it.watchGradeNm }
                            }

                            val responsePosterJob =
                                async {
                                    movieRemoteDataSource.getPoster(
                                        detailInfo.movieNmEn,
                                        detailInfo.prdtYear
                                    )
                                }

                            when (val responsePoster = responsePosterJob.await()) {
                                is Result.Success -> {
                                    tmp.apply {
                                        var poster = responsePoster.data.Poster

                                        if (poster == "N/A") {
                                            poster = ""
                                        }

                                        posterUrl = poster
                                        plot = responsePoster.data.Plot
                                    }
                                }
                                is Result.Error -> {}
                            }
                        }
                        is Result.Error -> {}
                    }
                    result.add(tmp)
                }
            }
        }

        return result.sortedBy { it.rank }
    }
}
```

## 아쉬운점

- 코루틴을 제대로 알고 사용하는건지 의문이 들었던 프로젝트였습니다.
- 금주부터 너무 바빠져서 프로젝트에 투자할 시간이 없어서 아쉬웠습니다.

# 황준성

- 맡은 부분
    - 두번째 화면
        - 영화 상세 정보 표현
        - 평점 리뷰 표시
        - 메세지 공유 기능
- 기여한 점
    - 영화 상세 정보 표현 및 UI 설정
    
    navigation의 navArgs 를 통해서 첫번째 페이지로부터 데이터를 받습니다.
    
    화면 구성에 필요한 데이터들을 선언해주고 이를 UI와 엮어줍니다.
    
![Screen 02 중간](https://user-images.githubusercontent.com/85336456/194576902-999c54d9-1bfc-44df-b6c7-31e113a588f1.jpeg)

![Screen 02-02 중간](https://user-images.githubusercontent.com/85336456/194576917-cdffdd33-97d8-4d9b-b096-7fcb342a1507.jpeg)

    
  - 평점 리뷰 표시

  ListAdapter를 이용해서 FireBase를 통해 저장된 Review 데이터를 받아서 이를 RecyclerView에 표현해줍니다. RecyclerView의 아이템 (닉네임,수정,삭제 버튼) 을 클릭시 자세히보기, 수정, 삭제 다이얼로그가 나타나도록 리스너를 설정해줍니다.
    
  리스트 어댑터
    
  ```kotlin
  class DetailReviewAdapter() : ListAdapter<Review, DetailReviewAdapter.ReviewViewHolder>(diffUtil) {

      interface DeleteItemClick {
          fun onClick(view: View, position: Int, pw: String)
      }

      interface EditItemClick {
          fun onClick(view: View, position: Int, nickname: String, pw: String)
      }

      var deleteItemClick: DeleteItemClick? = null
      var editItemClick: EditItemClick? = null

      inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
          RecyclerView.ViewHolder(binding.root) {
          fun bind(items: Review) {
              binding.tvNickName.text = items.nickname
              binding.tvContent.text = items.content
              binding.rating.rating = items.rating
              Glide.with(binding.ivReviewImg.context).load(items.imageUri).error(R.drawable.no_img)
                  .into(binding.ivReviewImg)
              binding.tvReviewDate.text = items.date

              binding.ivEdit.setOnClickListener {
                  var nickname = items.nickname
                  var pw = items.password
                  editItemClick?.onClick(it, adapterPosition, nickname, pw)
              }
              binding.ivDelete.setOnClickListener {
                  var pw = items.password
                  deleteItemClick?.onClick(it, adapterPosition, pw)

              }

              //수정 validation 확인
              binding.ivEdit.invalidate()
              binding.ivEdit.visibility = View.INVISIBLE

          }
      }

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
          return ReviewViewHolder(
              ItemReviewBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              )
          )
      }

      override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
          holder.bind(currentList[position])

      }

      companion object {
          val diffUtil = object : DiffUtil.ItemCallback<Review>() {
              override fun areItemsTheSame(
                  oldItem: Review,
                  newItem: Review
              ): Boolean {
                  return oldItem.hashCode() == newItem.hashCode()
              }

              override fun areContentsTheSame(
                  oldItem: Review,
                  newItem: Review
              ): Boolean {
                  return oldItem == newItem
              }

          }
      }

  }

  ```
    
  DetailFragment내에서 Listener를 override 해줍니다.

  ```kotlin
  //리뷰 자세히 보기, 닉네임 클릭
  reviewAdapter.nickNameClick = object : DetailReviewAdapter.NickNameClick {
      override fun onClick(view: View, position: Int) {
          detailViewModel.editState = EditState(false, false)
          detailViewModel.changeUiState(ReviewUiState.Reading)
          Toast.makeText(requireContext(), "ALICE", Toast.LENGTH_SHORT).show()
      }
  }

  //리뷰 수정
  reviewAdapter.editItemClick = object : DetailReviewAdapter.EditItemClick {
      override fun onClick(view: View, position: Int) {
          detailViewModel.editState = EditState(true, true)
          showValidationDialog(MODE.MODIFY)
      }
  }
  //리뷰 삭제
  reviewAdapter.deleteItemClick = object : DetailReviewAdapter.DeleteItemClick {
      override fun onClick(view: View, position: Int) {
          showValidationDialog(MODE.DELETE)
      }
  }

  //리뷰 추가
  btnAddReview.setOnClickListener{
  detailViewModel.editState = EditState(true, false)
      detailViewModel.changeUiState(ReviewUiState.Review)
  }
  ```

- 메세지 공유 기능

ACTION_PICK 인텐트를 이용해 전화번호부에서 번호를 얻고, 이후 받은 영화 데이터에서 영화 제목과 포스터 이미지 URL을 다시 ACTION_SEND 인텐트로 MMS 문자를 보내줍니다.
    
![메세지공유](https://user-images.githubusercontent.com/85336456/194577476-31e5d32e-c48d-49ae-b17d-c076327b5e5f.png)

- 아쉬운 점
    - 좀 더 구현하고 싶은게 많았는데 시간이 너무 부족해서 아쉬웠습니다.
    - Flow와 코루틴에대한 이해도 부족이 좀 많이 체감되었던 과제였습니다.

# 김현수

## 맡은역할

- Firebase RDB 접근( 값 삭제, 등록, 받아오기)구현
- 리뷰 관리

### Firebase RDB 접근 관리

```kotlin
override suspend fun uploadReview(title: String,review: Review) {
valreviewContent =review.toMapContent()
    fbRDB.getReference(title)
        .child(review.nickname)
        .setValue(reviewContent)
        .addOnFailureListener{
					throw it
				}
}

override suspend fun deleteReview(title: String, review: Review) {
        fbRDB.getReference(title).child(review.nickname).removeValue()
            .addOnFailureListener {
                throw it
            }
}
// 리뷰 업로드 및 삭제
```

```kotlin
override fun getReviewList(title: String) =callbackFlow<List<Review>>{
	fbRDB.getReference(title)
        .addValueEventListener(object:ValueEventListener{
					override fun onDataChange(snapshot: DataSnapshot) {
	          trySend(snapshot.children.map{
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
// 해당 영화 리스트 받아오기
```

업로드와 삭제에서 실패의 경우 throw 하여 ViewModel 의 runCathing의 onFailure블록에서의 에러 상태로 변경합니다.

리뷰 리스트의 경우 callbackFlow를 통해 해당 RDB 레퍼런스의 값이 수정 될 경우 리스트를 Review()타입으로 변환하여 내려줍니다.

### 리뷰관리

![Untitled](https://user-images.githubusercontent.com/85336456/194577817-3336dd1e-78b1-4d48-9428-74f103f5e2b1.png)

![Untitled (1)](https://user-images.githubusercontent.com/85336456/194577867-29430f21-f8ee-423a-940f-928fa564fcb4.png)

![Untitled (2)](https://user-images.githubusercontent.com/85336456/194577876-d4fc47d8-da1a-44af-be40-0d921f2194bc.png)


리뷰의 경우 다이얼로그를 상태별로 재사용 하였습니다.

```kotlin
sealed class ReviewUiState {

	data class Success(
		val mode:MODE
	) : ReviewUiState()

	data class Failure(
		val mode: MODE
	) : ReviewUiState()

	object Modify : ReviewUiState()

	object Reading : ReviewUiState()

	object Review : ReviewUiState()
}

enum class MODE{
	DELETE,REVIEW,MODIFY,VALIDATION
}수정, 읽기전용, 리뷰 
```

수정, 읽기전용, 리뷰 상태로 크게 구분하였으며 성공과 실패의 경우 또한 enum 타입의 MODE 별로 처리하였습니다.

읽기전용 상태의 경우 수정이 불가능하며 확인 버튼이 사라집니다.

리뷰 상태의 경우 모두 editable하며 별명과 비밀번호 6자리를 강제하였습니다.

수정의 경우 리뷰내용만 수정이 가능합니다.

```kotlin
classThrottleClickListener(private vallistener: (View) -> Unit) : View.OnClickListener{
	private varlastTime = 0L

	override fun onClick(v: View?) {
		val now = System.currentTimeMillis()
		if(now - lastTime < THROTTLE_CLICK_INTERVAL) {
			return
		}
    lastTime = now

		v?.let{
			listener(it)
		}
	}

	companion object{
		private const valTHROTTLE_CLICK_INTERVAL = 500L
  }
}
```

스로틀 클릭을 구현하여 RDB에 연속적으로 동일한 접근을 막아주었습니다.

```kotlin
private var_reviewList =MutableStateFlow<List<Review>>(emptyList())
val reviewList = _reviewList.asStateFlow()

private var getReviewListJob: Job? = null

fun setMovieList(title: String) {
	getReviewListJob?.cancel()
  getReviewListJob = viewModelScope.launch {
	  getReviewListUseCase(title).stateIn(
	    viewModelScope,
	    SharingStarted.Lazily,
      emptyList()
    ).collectLatest {
      _reviewList.emit(it)
    }
  }
}
```

flow로 내려주는 리스트를 StateFlow로 변환합니다. 이후 다른 영화의 제목이 들어오면 기존의 job을 취소하고 새로 job을 생성합니다.

```kotlin
private fun requestPermission() {
	val REQUEST_EXTERNAL_STORAGE = 1
	val PERMISSIONS_STORAGE =arrayOf<String>(
	  Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
	)

	val permission = ActivityCompat.checkSelfPermission(
	  requireActivity(),
    Manifest.permission.WRITE_EXTERNAL_STORAGE
	)

	if(permission != PackageManager.PERMISSION_GRANTED) { 
	  ActivityCompat.requestPermissions(
	    requireActivity(),
      PERMISSIONS_STORAGE,
      REQUEST_EXTERNAL_STORAGE
	  )
  }
}
```

갤러리에 접근하기 위해 Permission을 체크합니다.

```kotlin
ivMovieThumb.setOnClickListener{
	filterActivityLauncher.launch(
	  Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
  )
}

private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                it.data?.data?.let {
                    photoUri = it
                }
                reviewViewModel.uri = photoUri.toString()
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.cancel_album),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
```

앨범에서의 사진은 registerForAcitivtyResult를 통해 해당 사진의 uri를 가져와 이미지뷰와 바인딩합니다.

## 아쉬운점

- seald class를 통해 ui 상태를 관리해 보았는데 상태가 ui뿐 아니라 action에도 연관되어 있는 느낌이라 ui 상태의 개념에 조금 어긋난 것 같아서 아쉽습니다.
- job이 이전에 있으면 취소하고 새로 만드는 방식으로 리스트를 내려받았는데 제목 또한 flow로 만들어 서로 combine하여 하나의 job으로 관리했으면 하면 더 이상적이지 않았나 싶습니다.
