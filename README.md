#n4upgrade
==========
[nforge4](https://github.com/nforge/nforge4) upgrade java plugin

git을 사용해 설치한 nforge4의 업그레이드를 진행하는 플러그인


제한사항 (Known limitation)
--------------

nforge4를 git으로 설치한 경우에만 사용가능. 
binary 파일로 설치한 경우는 사용할 수 없습니다.(개발 예정)


사용 방법 (Usage)
---------------

nforge4를 설치한 폴더에 [n4upgrade.jar](https://github.com/nforge/n4upgrade/blob/master/n4upgrade.jar)를 다운받는다.

쉘이나 cmd 창에서 다운 받은 폴더로 이동 후 다음 명령어로 실행

    java -jar n4upgrade.jar

특정 버전으로 업그레이드를 원할 경우 parameter로 입력가능

    java -jar n4upgrade.jar 2.0


특징
----

* 순수 java만 사용하여 구현하였다.
* jgit을 이용하여 GIT repository를 다루었다.
* nforge4의 GIT tag 정보를 바탕으로 버전을 구별한다. 


업그레이드 순서도(upgrade flowchart)
------------

<img src="https://raw.github.com/nforge/n4upgrade/master/doc/flowchart_n4upgrade.png" width="450">

* 원격서버(git://github.com/nforge/nforege4)의 tag를 'git fetch --tag' 명령으로 받아와 버전 정보를 확인한다. 

* upgrade를 진행할 경우 'git merge' 명령어로 최신 버전으로 merge 한다.


* upgrade 정보만 확인하고 upgrade를 하지 않는 경우 fetch를 통해 받아온 새로운 tag 정보는 삭제한다. 



추가 개발사항
--------------

**nforge4를 binary 파일로 설치한 경우**
    
> * binary 파일은 war 파일로 배포예정
> * 기존 war 파일을 백업하고 최신버전의 war 파일로 덮어씌운다.
> * nforge4의 저장소와 DB 관련 파일은 migration 해주어야 한다.

   

