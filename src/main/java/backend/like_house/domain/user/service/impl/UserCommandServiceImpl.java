package backend.like_house.domain.user.service.impl;

import backend.like_house.domain.user.dto.UserDTO.*;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.domain.user.service.UserCommandService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User updateUserProfile(User user, UpdateProfileRequest request) {
        Optional<User> requestUser = userRepository.findById(user.getId());
        requestUser.get().setUpdateUserProfile(request);
        return userRepository.save(requestUser.get());
    }

    @Override
    public User updateUserPassword(User user, UpdatePasswordRequest request) {

        // 기존 비밀번호와 동일한지 검증
        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new UserException(ErrorStatus.PASSWORD_SAME_AS_OLD);
        }

        Optional<User> requestUser = userRepository.findById(user.getId());
        requestUser.get().setUpdateUserPassword(passwordEncoder.encode(request.getNewPassword()));
        return userRepository.save(requestUser.get());
    }

    @Override
    public void commentAlarmSetting(User user) {
        user.commentAlarmSetting();
    }

    @Override
    public void commentReplyAlarmSetting(User user) {
        user.commentReplyAlarmSetting();
    }

    @Override
    public void eventAlarmSetting(User user) {
        user.eventAlarmSetting();
    }

    @Override
    public void chatAlarmSetting(User user) {
        user.chatAlarmSetting();
    }
}