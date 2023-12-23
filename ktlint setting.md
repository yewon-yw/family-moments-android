# 커밋 전 ktlint 강제 적용하기

## 1. ktlint 설치
```bash
curl -sSLO https://github.com/pinterest/ktlint/releases/download/1.1.0/ktlint && chmod a+x ktlint && sudo mv ktlint /usr/local/bin/
```
## 2. gradle 에 ktlint plugin 추가
project 수준의 gradle 에 추가하기
```gradle
plugins {
	id("org.jlleitschuh.gradle.ktlint") version "$최신 버전"
}
```
## 3. 깃 pre-commit 훅 설정
Git pre-commit 훅이 설정되고, ktlint를 사용하여 코드 스타일을 검사하는 작업을 수행할
```bash
./gradlew addKtlintCheckGitPreCommitHook
```
## 4. 커밋하기 전 깃 훅 체크하도록 설정
```bash
ktlint installGitPreCommitHook
```
## 5. 루트 디렉토리에 .editorconfig 파일 생성
해당 파일을 바탕으로 ktlint 를 검사하게 됩니다.
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
## 6. 커밋 하기
ktlint 를 위반하는 코드가 있으면 커밋이 불가

**ktlint -F** 를 통해 ktlint 적용하기
