package ajou.subchill.model.party;

import ajou.subchill.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface PartyRolesRepository extends JpaRepository<PARTYROLES, PARTYROLESID> {
    // nativeQuery = true : SQL
    // nativeQuery = false(default) : JPQL
    // ?1은 ?의 첫번째 파라미터를 보라는 뜻
    List<PARTYROLES> findAll();

    //새로운 대기자 추가
    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO PARTYROLES(PARTY_ROLE, PARTY_NAME, user_id, nickname)" +
            "VALUES(:PARTY_ROLE, :PARTY_NAME, :user_id, :nickname)", nativeQuery = true)
    int savePartyRoles(
            @Param("PARTY_ROLE") String PARTY_ROLE,
            @Param("PARTY_NAME") String PARTY_NAME,
            @Param("user_id") String user_id,
            @Param("nickname") String nickname);

    //파티역할정보 삭제
    @Modifying //DELETE
    @Query(value = "DELETE * FROM PARTYROLES WHERE PARYT_NAME=?1 AND user_id=?2",nativeQuery = true)
    PARTYROLES 파티역할정보삭제(String PARYT_NAME, String user_id);

    @Transactional
    @Query(value = "SELECT PARTY_NAME FROM PARTYROLES WHERE user_id = :user_id", nativeQuery = true)
    String 파티이름(String user_id);

    @Query(value = "SELECT s FROM PARTYROLES s WHERE s.PARTY_NAME LIKE %:keyword% ")
    List<PARTYROLES> findAllSearch(String keyword);
}