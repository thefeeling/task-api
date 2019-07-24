package me.daniel.taskapi.user.application;

import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.global.auth.AuthType;
import me.daniel.taskapi.global.auth.TokenPayload;
import me.daniel.taskapi.global.model.user.UserId;
import me.daniel.taskapi.user.dao.UserRepository;
import me.daniel.taskapi.user.domain.User;
import me.daniel.taskapi.user.dto.LoginDto;
import me.daniel.taskapi.user.event.UserLoginedEvent;
import me.daniel.taskapi.user.exception.NotExistsUserException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLoginService implements ApplicationEventPublisherAware {

    private final UserRepository userRepository;
    private final TokenPublishService tokenPublishService;
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    /**
     *
     * @param loginReq - 로그인 요청 DTO
     * @return LoginDto.Res
     */
    public LoginDto.Res doLogin(LoginDto.Req loginReq) {
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(NotExistsUserException::new)
                .verifyPassword(loginReq.getPassword());
        TokenPayload payload = new TokenPayload(user.getId(), AuthType.LOCAL);
        eventPublisher.publishEvent(new UserLoginedEvent(UserId.of(payload.getId()), payload.getAuthType()));
        Pair<LocalDateTime, String> rs = tokenPublishService.publish(payload);
        return new LoginDto.Res(rs.getSecond(), rs.getFirst());
    }

}

