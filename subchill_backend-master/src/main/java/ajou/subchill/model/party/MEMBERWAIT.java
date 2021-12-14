package ajou.subchill.model.party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@IdClass(MEMBERWAITID.class)
public class MEMBERWAIT implements Serializable {

    // SQL 에서 자동생성되도록 돕는 어노테이션
    @Id
    private String SERVICE_NAME; //구독 서비스 이름, PK
    private Integer WAIT_MONTHS; //구독 개월 수
    private String SUBSCRIBE_PAYMENT; //구독 요금제
    private String WAIT_PARTY_ROLE; //파티 역할

    @Id
    private String user_id; //유저 id, PK
    private String nickname; //파티 내부 별명



    //getter setter
    public String getSERVICE_NAME() {
        return SERVICE_NAME;
    }

    public void setSERVICE_NAME(String SERVICE_NAME) {
        this.SERVICE_NAME = SERVICE_NAME;
    }

    public Integer getWAIT_MONTHS() {
        return this.WAIT_MONTHS;
    }

    public void setWAIT_MONTHS(Integer WAIT_MONTHS) {
        this.WAIT_MONTHS = WAIT_MONTHS;
    }

    public String getSUBSCRIBE_PAYMENT() {
        return SUBSCRIBE_PAYMENT;
    }

    public void setSUBSCRIBE_PAYMENT(String SUBSCRIBE_PAYMENT) {
        this.SUBSCRIBE_PAYMENT = SUBSCRIBE_PAYMENT;
    }

    public String getWAIT_PARTY_ROLE() {
        return WAIT_PARTY_ROLE;
    }

    public void setWAIT_PARTY_ROLE(String WAIT_PARTY_ROLE) {
        this.WAIT_PARTY_ROLE = WAIT_PARTY_ROLE;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}