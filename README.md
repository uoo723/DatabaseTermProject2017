# 식당 POS 시스템

JDBC와 Oracle Database를 활용한 식당 POS 시스템 구축

## Prerequisite

Java (jdk 1.8)  
Oracle 11g Database Express Edition  
Network connection  

## Run

* For Unix-like platforms

```bash
:$ ./gradlew run
```

* For Windows

```cmd
> gradlew.bat run
```

첫 실행시 의존성 라이브러리 및 파일 다운로드로 시간이 다소 걸림  
기본적으로 프로그램 최초 실행시 자동으로 테이블을 생성하기 때문에 수동으로 스크립트를 실행하지 않아도 됨  

## 프로그램 설명

* Database 로그인  
<img src="screenshots/DBLoginView.png?raw=true" width="50%">
Oracle Database에 접속하기 위한 계정정보를 입력한다  

* Main View  
<img src="screenshots/MainView.png?raw=true" width="50%">

## License

* **RxSwing**  
  https://github.com/ReactiveX/RxSwing  
  Apache License 2.0  
  
* **rxjava2-jdbc**  
  https://github.com/davidmoten/rxjava2-jdbc  
  Apache License 2.0  

* **Dagger2**  
  https://github.com/google/dagger  
  Copyright 2012 The Dagger Authors  
  Apache License 2.0  
  
* **Rxjava2**  
  https://github.com/ReactiveX/RxJava
  Copyright (c) 2016-present, RxJava Contributors  
  Apache License 2.0  
  
* **jdbc**  
  http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html  
  OTN License  
  
* **log4j2**  
  https://github.com/apache/logging-log4j2  
  Apache License, version 2.0  
  
* **LGoodDatePicker**  
  https://github.com/LGoodDatePicker/LGoodDatePicker  
  Copyright (c) 2016 LGoodDatePicker  
  The MIT License (MIT)  
  
