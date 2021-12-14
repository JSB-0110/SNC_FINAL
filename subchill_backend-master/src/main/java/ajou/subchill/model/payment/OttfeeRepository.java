package ajou.subchill.model.payment;

import ajou.subchill.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OttfeeRepository extends JpaRepository<OTTFEE, OTTFEEID> {
    // nativeQuery = true : SQL
    // nativeQuery = false(default) : JPQL
    // ?1은 ?의 첫번째 파라미터를 보라는 뜻

    @Query(value = "SELECT * FROM OTTFEE WHERE SERVICE_NAME=?1 AND SUBSCRIBE_PAYMENT=?2",nativeQuery = true)
    OTTFEE 구독서비스중복체크(String SERVICE_NAME, String SUBSCRIBE_PAYMENT);

    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO OTTFEE(SERVICE_NAME,SUBSCRIBE_PAYMENT,SHARABLE_PROFILES,PAYMENT_AMOUNT, MONTHS_PER_PAYMENT, PARTY_REGISTER)" +
            " VALUES(:SERVICE_NAME,:SUBSCRIBE_PAYMENT,:SHARABLE_PROFILES,:PAYMENT_AMOUNT, :MONTHS_PER_PAYMENT, :PARTY_REGISTER)",nativeQuery = true)
    int 구독서비스정보저장(@Param("SERVICE_NAME") String SERVICE_NAME,
                  @Param("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                  @Param("SHARABLE_PROFILES") Integer SHARABLE_PROFILES,
                  @Param("PAYMENT_AMOUNT") Integer PAYMENT_AMOUNT,
                  @Param("MONTHS_PER_PAYMENT") Integer MONTHS_PER_PAYMENT,
                  @Param("PARTY_REGISTER") Integer PARTY_REGISTER);

    //구독서비스 정보 삭제
    @Modifying //DELETE
    @Query(value = "DELETE * FROM OTTFEE WHERE SERVICE_NAME=?1 AND SUBSCRIBE_PAYMENT=?2",nativeQuery = true)
    OTTFEE 구독서비스정보삭제(String SERVICE_NAME, String SUBSCRIBE_PAYMENT);
}
