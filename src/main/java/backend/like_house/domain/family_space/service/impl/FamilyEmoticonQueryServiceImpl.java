package backend.like_house.domain.family_space.service.impl;

import backend.like_house.domain.family_space.converter.FamilyEmoticonConverter;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetailListResponse;
import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.family_space.repository.FamilyEmoticonRepository;
import backend.like_house.domain.family_space.service.FamilyEmoticonQueryService;
import backend.like_house.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FamilyEmoticonQueryServiceImpl implements FamilyEmoticonQueryService {

    private final FamilyEmoticonRepository familyEmoticonRepository;

    @Override
    public FamilyEmoticonDetailListResponse familyEmoticonQueryService(User user, Long familySpaceId) {
        List<FamilyEmoticon> familyEmoticonList = familyEmoticonRepository.findAllByFamilySpaceId(familySpaceId);
        return FamilyEmoticonConverter.toFamilyEmoticonDetailResponseList(familyEmoticonList);
    }
}
