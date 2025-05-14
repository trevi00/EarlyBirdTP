* 2025 05-09 CheckUser 
  * CheckLogic
    * HashSet을 이용하여 DateLogic 객체를사용.
    * LocalDate를 이용하여 금일 출석여부 확인
    * LocalTime을 이용하여 특정 시간 출석불가능하게 만듬.
    * CheckLogic - countMonth를 이용해서 한달동안 출석을 몇번했는지 count로 체크.
  * CheckPrinter
    * Set<LocalDate> 에서 불러온 연,월,일 출력.
  * User
    * User명, 유저 포인트 생성. 
    * print, checklogic final 변수사용 (객체 일관성을 위해 한번만 사용하기 위함. )
* 2025-05-14 
  * Bird 객체, Check User 객체. UI인 EarlyBird병합.