# A. 커밋 전 ktlint 강제 적용하기

### 1. 루트 디렉토리에 .editorconfig 파일 생성
해당 파일을 바탕으로 ktlint 검사 진행
```editorconfig
root = true

[*]
charset = utf-8
end_of_line = lf
indent_size = 4
indent_style = space
trim_trailing_whitespace = true
insert_final_newline = true
max_line_length = 120
tab_width = 4

[*.{kt,kts}]
ktlint_function_naming_ignore_when_annotated_with=Composable
```

### 2. ktlint 설치
```bash
curl -sSLO https://github.com/pinterest/ktlint/releases/download/1.1.0/ktlint && chmod a+x ktlint && sudo mv ktlint /usr/local/bin/
```
### 3. gradle 에 ktlint plugin 추가
project 수준의 gradle 에 추가하기
```gradle
plugins {
	id("org.jlleitschuh.gradle.ktlint") version "$최신 버전"
}
```
### 4. 깃 pre-commit 훅 설정
Git pre-commit 훅이 설정되고, 커밋 전 ktlint를 사용하여 코드 스타일을 검사하는 작업을 수행할
```bash
./gradlew addKtlintCheckGitPreCommitHook
```
### 5. 커밋하기 전 깃 훅 체크하도록 설정
```bash
ktlint installGitPreCommitHook
```

### 6. 커밋 하기
ktlint 를 위반하는 코드가 있으면 커밋이 불가

**```ktlint -F```** 를 통해 ktlint 적용 후 커밋하기

<br/>

# B. editorConfig 규칙으로 자동 포맷 정렬하기
### 1. 루트 디렉토리에 .editorconfig 파일 생성
- A-1. 방법과 동일
### 2. IDE 설정
- **Android Studio Menu -> Settings > Editor > Code Style > Enable EditorConfig Support 체크** : .editorconfig 파일의 규칙대로 code style 설정됨
- **```command + option + L```** : 설정된 code style 기반으로 코드 자동 포맷팅 수행됨
