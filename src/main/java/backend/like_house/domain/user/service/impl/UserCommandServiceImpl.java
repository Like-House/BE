package backend.like_house.domain.user.service.impl;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    @Override
    public void commentAlarmSetting(User user) {
        user.commentAlarmSetting();
    }
}
