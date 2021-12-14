package ajou.subchill.model.party;

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

@IdClass(PARTYROLESID.class)
public class PARTYROLES implements Serializable {

    // SQL 에서 자동생성되도록 돕는 어노테이션
    private String PARTY_ROLE; //파티 역할
    @Id
    private String PARTY_NAME; //파티 이름, PK

    @Id
    private String user_id; //유저 id, PK
    private String nickname; //파티 내부 별명

    public PARTYROLES(PARTYROLES partyroles) {
    }
}