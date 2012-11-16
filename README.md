#n4upgrade
==========
[nforge4](https://github.com/nforge/nforge4) upgrade java plugin

git을 사용해 설치한 nforge4의 업그레이드를 진행하는 플러그인


제한사항 (Known limitation)
--------------

git으로 설치한 경우에만 사용가능. 
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

*순수 java만 사용하여 구현하였다.
*jgit을 이용하여 GIT repository를 다루었다.


upgrade 과정
------------

upgrade는 nforge4의 GIT tag 정보를 바탕으로 한다. 

기존 설치한 git tag 버전과 remote서버(http://github.com/nforge/nforege4)의 tag 정보를 비교해서 
최신 버전이 존재할 경우 업그레이드를 진행할 것인지 물어보고 업그레이드를 진행한다. 

upgrade 과정은 git fetch와 git merge 명령으로 이루어진다. 

git fetch --tag 명령을 통해 최신 tag 정보를 확인한다.

upgrade 정보만 확인하고 upgrade를 하지 않는 경우 fetch를 통해 받아온 새로운 tag 정보는 삭제한다. 



nforge4를 binary 파일로 설치한 경우의 시나리오
-----------------------------------

최신 버전의 war 파일을 받는다. 




