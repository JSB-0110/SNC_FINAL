package ajou.subchill.web;

import ajou.subchill.service.PartyStartService;
import ajou.subchill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home()
    {return "home"; }

    @GetMapping("/user/new")
    public String join()
    {return "join"; }

    @PostMapping(value = "/user/new")
    public String register(@RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber,
                           @RequestParam("email") String email, @RequestParam("userId") String userId, @RequestParam("password") String password)
    {
        userService.회원가입(name, phoneNumber, email, userId, password);
        // 홈에서 필요한 모든 정보가 redirect를 통해 이동함
        return "redirect:/";
    }

    //party1 link
    @GetMapping("/party/start")
    public String party1() {
        return "party1"; }


    //party2.html link
    @GetMapping("/party/start/role")
    public String party2 () {
        return "party2";
    }

    //party2-leader.html link
    @GetMapping("/party/start/role/leader")
    public String party2_leader()
    {return "party2-leader"; }

    //party2-member.html link
    @GetMapping("/party/start/role/memder")
    public String party2_member()
    {return "party2-member"; }

    //partycheck1.html link
    @GetMapping("/party/check")
    public String partycheck()
    {return "partycheck1"; }
}
