package ajou.subchill.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // nativeQuery = true : SQL
    // nativeQuery = false(default) : JPQL
    // ?1은 ?의 첫번째 파라미터를 보라는 뜻

    @Query(value = "SELECT * FROM user WHERE email=?1",nativeQuery = true)
    User 이메일중복체크(String email);


    @Modifying // update/delete의 경우 @Modifying 붙여줘야됨
    @Query(value = "INSERT INTO user(name, phoneNumber, email, userId, password) VALUES(:name, :phoneNumber, :email, :userId, :password)",nativeQuery = true)
    int 회원정보저장(@Param("name") String name,
               @Param("phoneNumber") String phoneNumber,
               @Param("email") String email,
               @Param("userId") String userId,
               @Param("password") String password);
}