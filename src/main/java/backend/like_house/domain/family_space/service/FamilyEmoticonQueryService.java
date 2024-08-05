package backend.like_house.domain.family_space.service;

import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetailListResponse;
import backend.like_house.domain.user.entity.User;

public interface FamilyEmoticonQueryService {
    FamilyEmoticonDetailListResponse familyEmoticonQueryService(User user, Long familySpaceId);
}
