package ajou.subchill.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OTTFEEID.class)
public class OTTFEE implements Serializable {

    // SQL 에서 자동생성되도록 돕는 어노테이션
    @Id
    private String SERVICE_NAME; //구독 서비스 이름, PK
    @Id
    private String SUBSCRIBE_PAYMENT; //구독 요금제, PK
    private Integer SHARABLE_PROFILES; //공유 가능 프로필 수
    private Integer PAYMENT_AMOUNT; //결제 금액
    private Integer MONTHS_PER_PAYMENT; //월별 결제 금액
    private Integer PARTY_REGISTER; //1인당 결제 금액
}