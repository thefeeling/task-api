package me.daniel.taskapi.user.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.daniel.taskapi.user.dao.UserRepository;
import me.daniel.taskapi.user.domain.User;
import me.daniel.taskapi.user.dto.JoinDto;
import me.daniel.taskapi.user.exception.ExistsEmailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserJoinService {
    private final UserRepository userRepository;

    @Transactional
    public void doJoin(JoinDto.Req joinReq) {
        log.info("doLocalJoin {}", joinReq.getEmail());
        if (userRepository.findByEmail(joinReq.getEmail()).isPresent()) {
            throw new ExistsEmailException();
        }
        User user = User.create(joinReq.getEmail(), joinReq.getPassword());
        userRepository.save(user);
    }
}
