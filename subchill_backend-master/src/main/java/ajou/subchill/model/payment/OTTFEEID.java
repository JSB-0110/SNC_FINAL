package ajou.subchill.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTTFEEID implements Serializable {
    private String SERVICE_NAME; //구독 서비스 이름, PK
    private String SUBSCRIBE_PAYMENT; //구독 요금제, PK
}