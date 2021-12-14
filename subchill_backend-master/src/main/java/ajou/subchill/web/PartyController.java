package ajou.subchill.web;

import ajou.subchill.model.party.PartyInfosRepository;
import ajou.subchill.model.party.PartyRolesRepository;
import ajou.subchill.service.PartyStartService;
import ajou.subchill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class PartyController {

    private final UserService userService;
    private final PartyStartService party1Service;
    private final PartyInfosRepository partyinfosRepository;
    private final PartyRolesRepository partyrolesRepository;

    @RequestMapping(value = "/party/start")
    public ModelAndView PartyNewregister(@RequestParam("nickname") String nickname,
                                         @RequestParam("SERVICE_NAME") String SERVICE_NAME,
                                         @RequestParam("SUBSCRIBE_PAYMENT") String SUBSCRIBE_PAYMENT,
                                         @RequestParam("WAIT_MONTHS") Integer WAIT_MONTHS, ModelAndView mov) {
        ModelAndView mav = new ModelAndView();
        //party2로 이동
        mav.addObject("nickname", nickname);
        mav.addObject("SERVICE_NAME", SERVICE_NAME);
        mav.addObject("SUBSCRIBE_PAYMENT", SUBSCRIBE_PAYMENT);
        mav.addObject("WAIT_MONTHS", WAIT_MONTHS);

        Long LN = party1Service.countmembers(SERVICE_NAME, SUBSCRIBE_PAYMENT, WAIT_MONTHS, "파티장");
        Long PN = party1Service.countmembers(SERVICE_NAME, SUBSCRIBE_PAYMENT, WAIT_MONTHS, "파티원");

        mav.addObject("leadernumber", LN); //파티장 숫자
        mav.addObject("partynumber", PN); //파티원 숫자

        mav.setViewName("party2");
        return mav;
    }

    @RequestMapping(value = "/party/start/role", params = "파티장=파티장")
    public ModelAndView PartyRoletoLeader(ModelAndView mav) {
        mav.setViewName("party2-leader1");
        return mav;
    }

    @RequestMapping(value = "/party/start/role")
    public ModelAndView PartyRoletoMember(ModelAndView mav) {
        mav.setViewName("party2-member1");
        return mav;
    }

    @PostMapping(value = "/party/start/role/leader")
    public ModelAndView PartyLeaderregister( @RequestParam("파티장") String leader, @RequestParam("WAIT_CAPACITY") Integer WAIT_CAPACITY,
                                            @RequestParam("WAIT_SHARE_ID") String WAIT_SHARE_ID,
                                            @RequestParam("WAIT_SHARE_PW") String WAIT_SHARE_PW, ModelAndView mav, Authentication authentication) {
        mav.addObject("ROLE", "파티장");
        mav.addObject("WAIT_CAPACITY", WAIT_CAPACITY);
        mav.addObject("WAIT_SHARE_ID", WAIT_SHARE_ID);
        mav.addObject("WAIT_SHARE_PW", WAIT_SHARE_PW);

        Integer MN = (Integer) mav.getModel().get("partynumber");
        Integer PN = party1Service.checkWaitList(mav,"파티장");
        String user_id = "id";
        //authentication.getName(); //유저 id, PK


        if(PN == -1){//파티 생성 대기
            //파티 없음
            //파티 생성하기
            party1Service.makePARTY(mav, "파티장","W", authentication);
            //맴버 wait에 저장
            party1Service.makeMEMWAIT(mav, "파티장", authentication);
            //capacity update
            party1Service.updateCapacity(mav, MN+1, "G");
            party1Service.updateCapacity(mav, WAIT_CAPACITY-1-MN, "L");
        }
        else{
            //partyroles에 정보저장
            party1Service.Matching(mav, "파티장", authentication);
            //delete wait member
            //파티생성 완료
        }

        mav.setViewName("partycheck");
        return mav;
    }

    @PostMapping(value = "/party/start/role/member")
    public ModelAndView PartyMemberregister(ModelAndView mav,Authentication authentication){
        int PN = party1Service.checkWaitList(mav,"파티원");
        Integer MN = (Integer) mav.getModel().get("partynumber");
        Integer WC = (Integer) mav.getModel().get("WAIT_CAPACITY");

        if(PN != 1) {
            // 파티장이 아예 없는 경우 //member만 저장
            // 파티 생성 대기 // 파티 있으면 capacity up
           // party1Service.makeMEMWAIT(mav, "파티원",uthentication authentication);
            //대기중 문구?
            //memberwait하고 capacity update
            party1Service.getWAITPARTY(mav);
            party1Service.makeMEMWAIT(mav, "파티원", authentication);
            party1Service.updateCapacity(mav, MN+1, "G");
            party1Service.updateCapacity(mav, WC-1-PN, "L");
        }
        else{
            //파티장 있어도 실패할 수 있음
            //capacity update
            //leftcapacty가 0일 경우 파티 생성.
            //아닐경우 memberwait하고 capacity update
            //delete wait party
            //delete waitmember
           // party1Service.Matching(mav, "파티원");
            //파티생성 완료
            party1Service.Matching(mav, "파티원", authentication);
        }
        mav.setViewName("partycheck");
        return mav;
    }

    @PostMapping(value = "/party/check")
    public String CheckPartyregister(Authentication authentication, Model model) {
        String user_id = "id";
        //authentication.getName(); //유저 id, PK
        String partyname = partyrolesRepository.파티이름(user_id);
        model.addAttribute("roleList", party1Service.searchPosts1(partyname));
        model.addAttribute("infoList", party1Service.searchPosts2(partyname));
        return "partycheck1.html";
    }
}
