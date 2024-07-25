package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.repository.BlockUserRepository;
import backend.like_house.domain.user_management.service.BlockUserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlockUserQueryServiceImpl implements BlockUserQueryService {

    private final BlockUserRepository blockUserRepository;

    @Override
    public boolean existsByUser(User user) {
        return blockUserRepository.existsByUser(user);
    }
}
