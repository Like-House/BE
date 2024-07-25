package backend.like_house.domain.user_management.service;

import backend.like_house.domain.user.entity.User;

public interface BlockUserQueryService {

    boolean existsByUser(User user);
}
