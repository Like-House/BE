package backend.like_house.domain.family_space.service;

import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO;
import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.user.entity.User;

public interface FamilyEmoticonCommandService {
    FamilyEmoticon createFamilyEmoticon(User user, FamilyEmoticonDTO.CreateFamilyEmoticonRequest createFamilyEmoticonRequest);

    void deleteFamilyEmoticon(Long familySpaceId, User user, Long familyEmoticonId);
}
