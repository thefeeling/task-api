package me.daniel.taskapi.user.application;

import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.global.model.user.UserId;
import me.daniel.taskapi.user.dao.UserLoginEventRepository;
import me.daniel.taskapi.user.domain.User;
import me.daniel.taskapi.user.dao.UserRepository;
import me.daniel.taskapi.user.dto.UserDto;
import me.daniel.taskapi.user.exception.NotExistsUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSearchService {
	private final UserRepository userRepository;
	private final UserLoginEventRepository userLoginEventRepository;

	public UserDto.Me get(Long id) {
	    User user = userRepository.findById(id).orElseThrow(NotExistsUserException::new);
		return new UserDto.Me(
			id,
			user.getEmail(),
			userLoginEventRepository.findLatestLoginedAtByUserId(UserId.of(id))
		);
	}
}
