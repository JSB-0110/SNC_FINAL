package ajou.subchill.model.party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PARTYWAIT {

    // SQL 에서 자동생성되도록 돕는 어노테이션
    @Id
    private String WAIT_PARTY; //파티 이름, PK
    private String SERVICE_NAME; //구독 서비스 이름
    private Integer WAIT_MONTHS; //구독 개월 수
    private Integer WAIT_CAPACITY; //파티 인원 수
    private String SUBSCRIBE_PAYMENT; //구독 요금제

    private Integer GATHERED_CAPACITY; //현재 모인 인원 수
    private Integer LEFT_CAPACITY; //파티 생성까지 남은 인원 수
    private String WAIT_SHARE_ID; //공유 아이디
    private String WAIT_SHARE_PW; //공유 비밀번호

}