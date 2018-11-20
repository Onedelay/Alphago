# ![](/screenshots/pic2word_ic.png)Pic2Word

![](/screenshots/pic2word_workflow.png)

<br>

## # 프로젝트 소개

- 개발기간 : 2018.01~2018.05
- 팀원 : 3명
- 역할 : 팀장, 앱개발
- 개발언어
  - 클라이언트 : Android(Java), 
  - 서버 : Node.js, Python
- Git : <https://github.com/Onedelay/Alphago.git>
- Play 스토어 : https://play.google.com/store/apps/details?id=com.alphago.alphago
- 시연영상 : https://youtu.be/LJ9Xgg2PLbs
- 인천대학교 컴퓨터공학부 졸업작품 발표대회 동상 수상

<br>

## # 주요기능

- [**이게 뭐예요?**](#2-sendimageactivity) - 서버로 사진을 전송하여 이미지속 물체의 단어를 추론하는 기능입니다.
- [**나의 카드북**](#4-cardbookactivity) - 인식 기능을 통해 저장한 단어들을 카테고리별로 분류해주고, 카드 형태로 단어학습을 진행할 수 있습니다.
- **게임할래요** - 저장된 이미지를 기반으로 다양한 방식으로 단어 학습을 진행할 수 있습니다.
- [**이만큼했어요**](#5-collectionactivity) - 카테고리별 학업 성취도를 한눈에 볼 수 있습니다.
- [**다국어 기능**](#6-다국어-기능) - 영어, 일본어, 중국어 학습을 지원합니다.

<br><br>

## # SDK 버전 및 라이브러리

- minSdkVersion : 24
- targetSdkVersion : 27

```java
implementation 'com.android.support:appcompat-v7:27.1.1'
implementation 'com.android.support:design:27.1.1'
implementation 'com.android.support.constraint:constraint-layout:1.0.2'
implementation 'com.budiyev.android:circular-progress-bar:1.1.8'
    
implementation 'com.squareup.picasso:picasso:2.5.2'

implementation 'com.github.jkwiecien:EasyImage:2.0.4'
implementation 'com.theartofdev.edmodo:android-image-cropper:2.6.0'

implementation 'com.squareup.retrofit2:retrofit:2.3.0'
implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    
testImplementation 'junit:junit:4.12'

```

<br>

<br>

## # 개발한 기능 설명

### 1. StartActivity

<p align="center">
    <img src="/screenshots/pic2word_start.png"></p>

- 스플래시 화면으로, 앱 사용에 필요한 권한 체크 후 서버로부터 카테고리별 기본 이미지를 다운로드 받습니다.

  - 기본 이미지가 저장 되어있지 않을 경우, 서버로부터 압축 파일을 받아 압축을 해제하고 SQLite DB 에 저장합니다.
  - SharedPreference 에 기본 이미지 다운로드 여부를 저장하고, 저장되어있을 경우에는 2초 뒤 메인화면으로 전환되도록 하였습니다.

  ```java
  // 화면 전환 제어 메서드
  // 기본 이미지를 다운받는 시간(time)동안 보여지거나, 최소 2초 후에 전환
  private void controlStartActivity(long time) {
          if (time < 2000L) {
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      startActivity(new Intent(StartActivity.this, MainActivity.class));
                      finish();
                  }
              }, 2000 - time);
          } else {
              startActivity(new Intent(StartActivity.this, MainActivity.class));
              finish();
          }
      }
  
  // 압축해제 메서드
  private void unzip(String zipFilePath, String destDir)
  // DB 저장 메서드
  private boolean writeResponseBodyToDist(ResponseBody body)
  ```

<br>

### 2. SendImageActivity

<p align="center">
    <img src="/screenshots/pic2word_send.png"></p>

- 인식하고자 하는 이미지를 편집하여 서버에 전송합니다.

  - 이미지 전송 후 응답 결과는 현재 액티비티에서 받아 인텐트에 저장하여 다음 액티비티로 전달합니다.

  ```java
  // 메인스레드의 UI 작업을 위해 비동기 메서드 사용
  cropImageView.setImageUriAsync(Uri.fromFile(imageFile));
  cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
              @Override
              public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                  // Bitmap 변환 후 서버에 요청 (POST) -- 생략
                  AlphagoServer.getInstance().sendImage(getBaseContext(), imageFile, new Callback<ResponseImageLabel>() {
                      @Override
                      public void onResponse(@NonNull Call<ResponseImageLabel> call, @NonNull Response<ResponseImageLabel> response) {
                          // 성공적으로 응답받을 경우 인식 결과 화면으로 이동 -- 생략
                      }
  
                      @Override
                      public void onFailure(Call<ResponseImageLabel> call, Throwable t) {
                          // 실패
                      }
                  });
              }
          });
  ```

<br>

### 3. ImageRecognitionActivity

<p aline="center">
    <img src="/screenshots/pic2word_recog.png"></p>

- 서버로부터 받은 결과를 (외국어-한국어) 세트로 볼 수 있고, 발음을 들을 수 있습니다.
- '저장하기' 버튼을 누를 경우 SQLite DB 에 저장됩니다.
- '틀렸어요' 버튼을 누를 경우 사용자의 이미지를 서버로 전송하고, 번역된 결과를 받아 데이터가 갱신됩니다.
  - 인식 결과가 없을 경우(none) 저장 버튼을 누를 수 없습니다.

<br>

### 4. CardBookActivity

<p align="center">
	<img src="/screenshots/pic2word_card.png"></p>

- 내부 SQLite DB 에 저장된 사진들을 카테고리별로 분류하여 보여줍니다.
  - Recyclerview 를 사용하여 GridLayout 을 구성했습니다.
  - 대표 이미지는 가장 최근에 저장한 이미지로 변경됩니다.
- 오른쪽 상단의 책 아이콘을 누르면 카드 형태로 학습을 진행할 수 있습니다.
  - 카테고리 학습을 진행할 경우 버튼의 아이콘이 변경되며, 카테고리를 선택한 후 다시 누르면 학습이 진행됩니다.
  - selector를 이용해 테두리를 표시하여 선택한 카테고리를 구분해줍니다.
  - 학습할 카테고리 선택 중에 back 버튼을 누르면 카테고리 선택이 취소되도록 onBackPress 메서드를 오버라이드하였습니다.

<br>

### 5. CollectionActivity

<p align="center">
	<img src="/screenshots/pic2word_collect.png"></p>

- 이미지 인식 기능으로 저장한 사진들이 자동으로 등록됩니다.
  - 앱 최초 실행시 다운받는 기본 이미지는 반영되지 않습니다.
  - 수집되지 않은 항목일 경우, 회색 이미지와 한국어로 표시하였습니다.
  - 수집된 항목의 경우 각 언어 설정에 맞추어 표시됩니다.
- 카테고리별로 진행상황을 파악할 수 있습니다.

<br>

### 6. 다국어 기능

<p align="center">
    <img src="/screenshots/pic2word_multi.png"></p>

- 메인화면에서 언어 설정(영어, 중국어, 일본어)을 변경할 수 있습니다.

- SharedPreference 에 언어 설정을 저장하고, 설정된 값으로 구분하여 언어를 다르게 표시합니다.

- 이미지 인식 결과에 모든 언어의 데이터가 포함되어있기 때문에, 영어로 이미지 인식을 기능을 이용했더라도 설정만 변경하면 다른 언어도 동일하게 학습할 수 있습니다.

<br>

### 7. 기타 기능

<p align="center">
    <img src="/screenshots/pic2word_help.png"></p>

- 인터넷이 연결되어있지 않을 경우, 이미지 인식 기능(이게 뭐예요?)을 사용할 수 없습니다.
- 메인화면 상단의 물음표 버튼을 누르면 앱 사용 가이드를 볼 수 있습니다.