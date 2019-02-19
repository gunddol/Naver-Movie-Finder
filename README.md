# 영화정보 검색 어플리케이션
## 설명
- 네이버 검색 API를 활용하여 영화정보를 검색하는 어플리케이션 제작
- 사용자로부터 검색어를 입력받아 검색결과 목록을 표시

# 구현
## 필수 구현사항

- EditText를 통해 검색어를 입력받아 `검색`버튼으로 영화 검색
- [네이버 검색 API](https://developers.naver.com/docs/search/movie/)를 활용하여 검색어에 해당하는 결과 받아오기
- 검색결과를 RecyclerView에 표시하기
- 각 영화정보에는 아래 정보가 모두 표시
: 썸네일 이미지, 제목, 평점, 연도, 감독, 출연배우
- 목록에서 영화 선택시 해당 영화 정보 link페이지로 이동

# API 정보
- https://developers.naver.com/docs/search/movie/

### 검색
`https://openapi.naver.com/v1/search/movie.json`
#### Header
- X-Naver-Client-Id (발급받은 앱의 Client id)
- X-Naver-Client-Secret (발급받은 앱의 Secret key)
#### Params
- Query (검색어)
