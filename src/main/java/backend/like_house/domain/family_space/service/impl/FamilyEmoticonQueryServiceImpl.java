package backend.like_house.domain.family_space.service.impl;

import backend.like_house.domain.family_space.converter.FamilyEmoticonConverter;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetailListResponse;
import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilyEmoticonRepository;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.family_space.service.FamilyEmoticonQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.FamilySpaceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FamilyEmoticonQueryServiceImpl implements FamilyEmoticonQueryService {

    private final FamilyEmoticonRepository familyEmoticonRepository;
    private final FamilySpaceRepository familySpaceRepository;
    private final UserRepository userRepository;

    @Override
    public FamilyEmoticonDetailListResponse familyEmoticonQueryService(User user, Long familySpaceId) {
        // 이미 validate 함.
        FamilySpace familySpace = familySpaceRepository.findById(familySpaceId).get();

        if (!userRepository.existsByFamilySpaceAndId(familySpace, user.getId())) {
            throw new FamilySpaceException(ErrorStatus.NOT_INCLUDE_USER_FAMILY_SPACE);
        }

        List<FamilyEmoticon> familyEmoticonList = familyEmoticonRepository.findAllByFamilySpaceId(familySpaceId);
        return FamilyEmoticonConverter.toFamilyEmoticonDetailResponseList(familyEmoticonList);
    }
}
