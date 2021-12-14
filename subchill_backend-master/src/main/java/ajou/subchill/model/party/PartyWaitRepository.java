package ajou.subchill.model.party;

import ajou.subchill.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Repository
public interface PartyWaitRepository extends JpaRepository<PARTYWAIT, Integer> {
    // nativeQuery = true : SQL
    // nativeQuery = false(default) : JPQL
    // ?1은 ?의 첫번째 파라미터를 보라는 뜻

    List<PARTYWAIT> findAll();
    //대기자 목록입력들어오면 서치후 겹치는 목록 잇으면 파티원 남은 숫자 갱신필요.

    //새로운 대기자 추가
    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO PARTYWAIT(WAIT_PARTY, SERVICE_NAME,WAIT_MONTHS,WAIT_CAPACITY, SUBSCRIBE_PAYMENT,"+
                    "GATHERED_CAPACITY, LEFT_CAPACITY, WAIT_SHARE_ID, WAIT_SHARE_PW)" +
            "VALUES(:WAIT_PARTY, :SERVICE_NAME, :WAIT_MONTHS, :WAIT_CAPACITY, :SUBSCRIBE_PAYMENT," +
                    " :GATHERED_CAPACITY, :LEFT_CAPACITY, :WAIT_SHARE_ID, :WAIT_SHARE_PW)",nativeQuery = true)
    int saveWaitParty(
            @Param("WAIT_PARTY") String WAIT_PARTY,
            @Param("SERVICE_NAME") String SERVICE_NAME,
            @Param("WAIT_MONTHS") Integer WAIT_MONTHS,
            @Param("WAIT_CAPACITY") Integer WAIT_CAPACITY,
            @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,

            @Param("GATHERED_CAPACITY") Integer GATHERED_CAPACITY,
            @Param("LEFT_CAPACITY") Integer LEFT_CAPACITY,
            @Param("WAIT_SHARE_ID") String WAIT_SHARE_ID,
            @Param("WAIT_SHARE_PW") String WAIT_SHARE_PW);

    //대기파티정보 삭제
    @Modifying //DELETE
    @Query(value = "DELETE * FROM PARTYWAIT WHERE WAIT_PARTY=?1",nativeQuery = true)
    PARTYWAIT 대기파티정보삭제(String WAIT_PARTY);

    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "SELECT LEFT_CAPACITY FROM PARTYWAIT WHERE SERVICE_NAME = :SERVICE_NAME " +
            "AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS ",nativeQuery = true)
    int checkCAPACITY( @Param("SERVICE_NAME") String SERVICE_NAME,
                 @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                 @Param("WAIT_MONTHS") Integer WAIT_MONTHS);

    //wait party 모든정보를 partyinfo에 넘겨주기
    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO PARTYINFOS (NUM, TITLE, CONTENTS) SELECT WAIT_PARTY, SERVICE_NAME,  FROM PARTYWAIT " +
            "WHERE SERVICE_NAME = :SERVICE_NAME AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS ",nativeQuery = true)
    String getPARTYWAIT( @Param("SERVICE_NAME") String SERVICE_NAME,
                       @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                       @Param("WAIT_MONTHS") Integer WAIT_MONTHS);



    @Modifying
    @Query(value = "UPDATE PARTYWAIT SET GATHERED_CAPACITY =:GATHERED_CAPACITY  WHERE SERVICE_NAME = :SERVICE_NAME " +
            "AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS ",nativeQuery = true)
    int updateGC( @Param("GATHERED_CAPACITY") Integer GATHERED_CAPACITY, @Param("SERVICE_NAME") String SERVICE_NAME,
                 @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                 @Param("WAIT_MONTHS") Integer WAIT_MONTHS);

    @Modifying
    @Query(value = "UPDATE PARTYWAIT SET LEFT_CAPACITY =:LEFT_CAPACITY  WHERE SERVICE_NAME = :SERVICE_NAME " +
            "AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS ",nativeQuery = true)
    int updateLC( @Param("LEFT_CAPACITY") Integer GATHERED_CAPACITY, @Param("SERVICE_NAME") String SERVICE_NAME,
                  @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                  @Param("WAIT_MONTHS") Integer WAIT_MONTHS);

    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "DELETE FROM PARTYWAIT WHERE SERVICE_NAME = :SERVICE_NAME " +
            "AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS ",nativeQuery = true)
    int 파티대기종료( @Param("SERVICE_NAME") String SERVICE_NAME,
              @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
              @Param("WAIT_MONTHS") Integer WAIT_MONTHS);
}