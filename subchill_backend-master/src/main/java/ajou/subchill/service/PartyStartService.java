package ajou.subchill.service;

import ajou.subchill.model.party.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyStartService {

    // @Modifying : insert, update, delete과 DDL구문 사용시 표기
    // @Transactional : update, delete 사용 시 표기
    @Autowired
    private final MemberWaitRepository memberwaitRepository;
    private final PartyWaitRepository partywaitRepository;
    private final PartyInfosRepository partyinfosRepository;
    private final PartyRolesRepository partyrolesRepository;

    //역할에 따른 대기자 수 count
    @Transactional
    public Long countmembers(String SN, String SP, Integer WM, String ROLE) {
        return memberwaitRepository.countmembers(SN, SP, WM, ROLE);
    }

    //party check 1: 파티 생성 가능성 확인하기
    @Transactional
    public int checkWaitList(ModelAndView mav, String ROLE){
        //가능성 확인
        int PN = (int) mav.getModel().get("leadernumber");
        int LN = (int) mav.getModel().get("membernumber");

        if((ROLE== "파티원" && PN == 1)||(ROLE =="파티장" && LN > 0)){//make party
            //파티장이 한명은 있어야 생성된다.
            //내가 파티원이고 파티장이 한명 있을때
            //내가 파티장이고 파티원이 한명 있을때
            return 1;
        }
        else{
            //파티 없음
            return -1;
        }
    }

    //PartyRoles DB 저장
    public int RoleSaving(ModelAndView mav, String ROLE, Authentication authentication){
        //파티원 정보저장
        String SN = (String) mav.getModel().get("SERVICE_NAME");
        String SP = (String) mav.getModel().get("SUBSCRIBE_PAYMENT");

        //****
        String user_id = "id";
                //authentication.getName();//유저 id, PK
        String nickname = (String) mav.getModel().get("nickname");

        return partyrolesRepository.savePartyRoles(ROLE, SN+user_id, user_id, nickname);
    }


    //match 2: 가능성 있는 아이들 모아서 한번 더 확인
    public void Matching( ModelAndView mav, String ROLE,Authentication authentication){
        String SN = (String) mav.getModel().get("SERVICE_NAME");
        String SP = (String) mav.getModel().get("SUBSCRIBE_PAYMENT");
        Integer WM = (Integer) mav.getModel().get("WAIT_MONTHS");

        Integer WC = (Integer) mav.getModel().get("WAIT_CAPACITY");
        Integer MN = (Integer) mav.getModel().get("partynumber");

        if(ROLE == "파티장"){
            if(WC<MN) {
                //파티 생성(저장할 파티
                makePARTY(mav, ROLE, "M", authentication);
                //파티원 정보 저장
                RoleSaving(mav, ROLE, authentication);
                //대기실에서 나머지 맴버 삭제.
                memberwaitRepository.맴버대기종료(SN,SP, WM);
            }
            else{
                //파티 생성( wait party
                makePARTY(mav, ROLE, "M", authentication);
                //capacity update
                //맴버 대기실로 보내기
                makeMEMWAIT(mav, ROLE, authentication);
            }
        }
        else if(ROLE == "파티원"){
            Integer lc = partywaitRepository.checkCAPACITY(SN, SP, WM);
            if( lc == 1){ //한자리 남음/ 너만 오면 완성
                //새로운 파티 생성
                //delete wait party
                partyinfosRepository.insertWAITintoINFOS(SN,SP,WM);
                partywaitRepository.파티대기종료(SN,SP,WM);
                //Party info에 정보 저장
                RoleSaving(mav, ROLE, authentication);
                //delete waitmember
                memberwaitRepository.맴버대기종료(SN,SP,WM);
            }
            else {
                makePARTY(mav,ROLE, "M", authentication);
                //memberwait하고 capacity update
                makeMEMWAIT(mav,ROLE, authentication);
                updateCapacity(mav, 1, "G");
                updateCapacity(mav, WC-1, "L");
            }
        }
    }

    //대기자 정보 저장
    @Transactional
    public int makeMEMWAIT(ModelAndView mav, String ROLE, Authentication authentication){
        String SN = (String) mav.getModel().get("SERVICE_NAME");
        String SP = (String) mav.getModel().get("SUBSCRIBE_PAYMENT");
        Integer WM = (Integer) mav.getModel().get("WAIT_MONTHS");
        String user_id = "id";
                //authentication.getName(); //유저 id, PK
        String nickname = (String) mav.getModel().get("nickname");
        return memberwaitRepository.saveWaitMembers(SN, WM, SP, ROLE, user_id, nickname);
    }

    //대기파티&생성파티 만들기
    @Transactional
    public int makePARTY( ModelAndView mav, String ROLE, String WorM, Authentication authentication){
        //파티 이름, PK = service name + 파티장 user_id
        String SN = (String) mav.getModel().get("SERVICE_NAME");
        Integer WM = (Integer) mav.getModel().get("WAIT_MONTHS");
        Integer WC = (Integer) mav.getModel().get("WAIT_CAPACITY");
        String SP = (String) mav.getModel().get("SUBSCRIBE_PAYMENT");

        String WID = (String) mav.getModel().get("WAIT_SHARE_ID");
        String WPW = (String) mav.getModel().get("WAIT_SHARE_PW");

        String user_id = "id";
                //authentication.getName();//유저 id, PK

        LocalDate currentDate = LocalDate.now(); // 컴퓨터의 현재 날짜 정보 2018-07-26
        LocalDate endDate = currentDate.plusMonths(WM);
        Integer payDate = currentDate.getDayOfMonth();

        if(WorM == "W"){//wait list
            return partywaitRepository.saveWaitParty(SN + user_id, SN, WM, WC, SP,
                1, WC-1, WID, WPW);
        }
        //id 없이 저장될 수 있으므로 후반에 waitparty에서 id 가져오고 삭제 시킴
        else if (WorM == "M"){//make party
            return partyinfosRepository.savePartyInfos(SN + user_id, SN, WC, SP,
                    currentDate, payDate, endDate, WID, WPW);
        }
        return -1;
    }

    //wait party 인원 업데이트
    public int updateCapacity(ModelAndView mav, Integer Capacity, String GorL) {
        String SN = (String) mav.getModel().get("SERVICE_NAME");
        Integer WM = (Integer) mav.getModel().get("WAIT_MONTHS");
        String SP = (String) mav.getModel().get("SUBSCRIBE_PAYMENT");

        if(GorL == "G"){
            return partywaitRepository.updateGC(Capacity, SN, SP, WM);
        }
        else if (GorL == "L"){
            return partywaitRepository.updateLC(Capacity, SN, SP, WM);
        }
        return 1;
    }

    //partycheck
    @Transactional
    public List<PARTYROLES> searchPosts1(String keyword) {
        return partyrolesRepository.findAllSearch(keyword).stream()
                .map(PARTYROLES::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PARTYINFOS> searchPosts2(String keyword) {
        return partyinfosRepository.findAllSearch(keyword).stream()
                .map(PARTYINFOS::new)
                .collect(Collectors.toList());
    }

    public int getWAITPARTY(ModelAndView mav) {
        String SN = (String) mav.getModel().get("SERVICE_NAME");
        Integer WM = (Integer) mav.getModel().get("WAIT_MONTHS");
        String SP = (String) mav.getModel().get("SUBSCRIBE_PAYMENT");
        int result =0;
        return result;
    }
}

