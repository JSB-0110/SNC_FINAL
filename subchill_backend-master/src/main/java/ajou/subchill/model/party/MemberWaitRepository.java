package ajou.subchill.model.party;

import ajou.subchill.model.user.User;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.util.List;

@Repository
public interface MemberWaitRepository extends JpaRepository<MEMBERWAIT, MEMBERWAITID> {
    // nativeQuery = true : SQL
    // nativeQuery = false(default) : JPQL
    // ?1은 ?의 첫번째 파라미터를 보라는 뜻

    //새로운 대기자 추가
    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO MEMBERWAIT(SERVICE_NAME,WAIT_MONTHS,SUBSCRIBE_PAYMENT,WAIT_PARTY_ROLE,user_id,nickname)" +
            "VALUES(:SERVICE_NAME, :WAIT_MONTHS, :SUBSCRIBE_PAYMENT, :WAIT_PARTY_ROLE, :user_id, :nickname)",nativeQuery = true)
    int saveWaitMembers(
            @Param("SERVICE_NAME") String SERVICE_NAME,
            @Param("WAIT_MONTHS") Integer WAIT_MONTHS,
            @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
            @Param("WAIT_PARTY_ROLE") String WAIT_PARTY_ROLE,
            @Param("user_id") String user_id,
            @Param("nickname") String nickname);

    //대기자 삭제
    @Modifying //DELETE
    @Query(value = "DELETE * FROM MEMBERWAIT WHERE SERVICE_NAME= :SERVICE_NAME AND user_id= :user_id",nativeQuery = true)
    int 대기자정보삭제(@Param("SERVICE_NAME") String SERVICE_NAME,@Param("user_id") String user_id);



    @Query(value = "SELECT COUNT(*) FROM MEMBERWAIT WHERE SERVICE_NAME = :SERVICE_NAME " +
            "AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS " +
            "AND WAIT_PARTY_ROLE = :WAIT_PARTY_ROLE",nativeQuery = true)
    Long countmembers(@Param("SERVICE_NAME") String SERVICE_NAME,
                      @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                      @Param("WAIT_MONTHS") Integer WAIT_MONTHS,
                      @Param("WAIT_PARTY_ROLE") String WAIT_PARTY_ROLE);

    //service_name/subscribe_payment/wait_months 겹치는 친구들 목록 찾기
    //@Query(value = )

    //그 친구들 save하면 삭제
    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "DELETE FROM MEMBERWAIT WHERE SERVICE_NAME = :SERVICE_NAME " +
            "AND SUBSCRIBE_PAYMENT = :SUBSCRIBE_PAYMENT AND WAIT_MONTHS = :WAIT_MONTHS ",nativeQuery = true)
    int 맴버대기종료( @Param("SERVICE_NAME") String SERVICE_NAME,
                       @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                       @Param("WAIT_MONTHS") Integer WAIT_MONTHS);
}