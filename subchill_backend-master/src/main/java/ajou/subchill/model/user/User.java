package ajou.subchill.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // @Column(name = "user_id")
    // SQL 에서 자동생성되도록 돕는 어노테이션
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//시스템상에서 자동으로 생성되는 id

    private String name;
    private String phoneNumber; //핸드폰번호
    private String email;//이메일
    private String userId;//사용자가 직접 등록한 아이디
    private String password; //비밀번호
}