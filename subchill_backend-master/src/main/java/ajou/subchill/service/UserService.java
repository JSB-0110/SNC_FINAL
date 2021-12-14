package ajou.subchill.service;

import ajou.subchill.model.user.User;
import ajou.subchill.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    // @Modifying : insert, update, delete과 DDL구문 사용시 표기
    // @Transactional : update, delete 사용 시 표기
    @Autowired
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public User 이메일중복체크(String email) {
        return userRepository.이메일중복체크(email);
    }


    @Transactional
    public int 회원가입(String name, String phoneNumber, String email, String userId, String password) {
        return userRepository.회원정보저장(name, phoneNumber, email, userId, password);
    }
}

