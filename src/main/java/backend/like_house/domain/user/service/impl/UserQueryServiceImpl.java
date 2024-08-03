package backend.like_house.domain.user.service.impl;

import backend.like_house.domain.user.converter.UserConverter;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.domain.user.service.UserQueryService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDTO.GetProfileResponse getUserProfile(User user) {
        return UserConverter.toGetProfileResponseDTO(user);
    }
}
