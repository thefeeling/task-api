package me.daniel.taskapi.user.api;

import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.global.auth.AuthHelper;
import me.daniel.taskapi.global.auth.Authorization;
import me.daniel.taskapi.global.auth.TokenPayload;
import me.daniel.taskapi.user.application.UserJoinService;
import me.daniel.taskapi.user.application.UserLoginService;
import me.daniel.taskapi.user.application.UserSearchService;
import me.daniel.taskapi.user.dto.JoinDto;
import me.daniel.taskapi.user.dto.LoginDto;
import me.daniel.taskapi.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserLoginService userLoginService;
    private final UserSearchService userSearchService;
    private final UserJoinService userJoinService;

    @PostMapping("/login")
	public ResponseEntity<LoginDto.Res> loginEvents(@RequestBody @Valid LoginDto.Req loginReq) {
        return new ResponseEntity<>(userLoginService.doLogin(loginReq), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/me")
	public UserDto.Me me() {
        TokenPayload payload = AuthHelper.getCurrentTokenPayload();
        return userSearchService.get(payload.getId());
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity join(@RequestBody @Valid JoinDto.Req joinReq) {
        userJoinService.doJoin(joinReq);
        return ResponseEntity.noContent().build();
    }

}
