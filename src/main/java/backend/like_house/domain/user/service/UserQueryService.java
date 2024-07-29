package backend.like_house.domain.user.service;

import backend.like_house.domain.user.entity.User;
import java.util.Optional;

public interface UserQueryService {

    Optional<User> findUser(Long id);
}
