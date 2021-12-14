package ajou.subchill.model.party;

import ajou.subchill.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PartyInfosRepository extends JpaRepository<PARTYINFOS, Integer> {
    // nativeQuery = true : SQL
    // nativeQuery = false(default) : JPQL
    // ?1은 ?의 첫번째 파라미터를 보라는 뜻

    //새로운 대기자 추가
    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO PARTYINFOS(PARTY_NAME, SERVICE_NAME,CAPACITY,SUBSCRIBE_PAYMENT, START_DATE, PAY_DATE, END_DATE, SHARE_ID, SHARE_PW)" +
            "VALUES(:PARTY_NAME, :SERVICE_NAME, :CAPACITY, :SUBSCRIBE_PAYMENT, :START_DATE, :PAY_DATE, :END_DATE, :SHARE_ID, :SHARE_ID, :SHARE_PW)", nativeQuery = true)
    int savePartyInfos(
            @Param("PARTY_NAME") String PARTY_NAME,
            @Param("SERVICE_NAME") String SERVICE_NAME,
            @Param("CAPACITY") Integer CAPACITY,
            @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
            @Param("START_DATE") LocalDate START_DATE,
            @Param("PAY_DATE") Integer PAY_DATE,
            @Param("END_DATE") LocalDate END_DATE,
            @Param("SHARE_ID") String SHARE_ID,
            @Param("SHARE_PW") String SHARE_PW);


/*    //WAIT_PARTY; //파티 이름, PK
    private String SERVICE_NAME; //구독 서비스 이름
    private Integer WAIT_MONTHS; //구독 개월 수
    private Integer WAIT_CAPACITY; //파티 인원 수
    private String SUBSCRIBE_PAYMENT; //구독 요금제

    private Integer GATHERED_CAPACITY; //현재 모인 인원 수
    private Integer LEFT_CAPACITY; //파티 생성까지 남은 인원 수
    private String WAIT_SHARE_ID; //공유 아이디
    private String WAIT_SHARE_PW; //공유 비밀번호

    */
    //파티정보 삭제
    @Modifying //DELETE
    @Query(value = "DELETE * FROM PARTYINFOS WHERE PARTY_NAME=?1", nativeQuery = true)
    PARTYINFOS 파티정보삭제(String PARTY_NAME);

    //insert문 수정 필요
    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO PARTYINFOS (NUM, TITLE, CONTENTS) SELECT WAIT_PARTY, SERVICE_NAME,  FROM PARTYWAIT " +
            "WHERE SERVICE_NAME = :SERVICE_NAME AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS ",nativeQuery = true)
    String insertWAITintoINFOS(@Param("SERVICE_NAME") String SERVICE_NAME,
                        @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                        @Param("WAIT_MONTHS") Integer WAIT_MONTHS);




    @Query(value = "SELECT s FROM PARTYINFOS s WHERE s.PARTY_NAME LIKE %:keyword% ")
    List<PARTYINFOS> findAllSearch(String keyword);
}
