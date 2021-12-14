package ajou.subchill.model.party;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PARTYINFOS {
    // SQL 에서 자동생성되도록 돕는 어노테이션
    @Id
    private String PARTY_NAME; //파티 이름, PK
    private String SERVICE_NAME; //구독 서비스 이름
    private Integer CAPACITY; //파티 인원 수
    private String SUBSCRIBE_PAYMENT; //구독 요금제

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate START_DATE; //파티 시작일
    private Integer PAY_DATE; //요금 결제일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate END_DATE; //파티 종료 날짜

    private String SHARE_ID; //공유 아이디
    private String SHARE_PW; //공유 비밀번호

    public PARTYINFOS(PARTYINFOS partyinfos) {
    }
}