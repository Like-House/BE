package backend.like_house.domain.family_space.service;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;

public interface FamilySpaceCommandService {

    FamilySpace generateNewFamilySpace(User user);
}
