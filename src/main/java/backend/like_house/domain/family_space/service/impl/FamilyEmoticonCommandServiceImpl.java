package backend.like_house.domain.family_space.service.impl;

import backend.like_house.domain.family_space.converter.FamilyEmoticonConverter;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.CreateFamilyEmoticonRequest;
import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilyEmoticonRepository;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.family_space.service.FamilyEmoticonCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.FamilyEmoticonException;
import backend.like_house.global.error.handler.FamilySpaceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FamilyEmoticonCommandServiceImpl implements FamilyEmoticonCommandService {

    private final FamilyEmoticonRepository familyEmoticonRepository;
    private final FamilySpaceRepository familySpaceRepository;
    private final UserRepository userRepository;

    @Override
    public FamilyEmoticon createFamilyEmoticon(User user, CreateFamilyEmoticonRequest createFamilyEmoticonRequest) {
        // 이미 유효성 검사를 함.
        FamilySpace familySpace = familySpaceRepository.findById(createFamilyEmoticonRequest.getFamilySpaceId()).get();

        if (!userRepository.existsByFamilySpaceAndId(familySpace, user.getId())) {
            throw new FamilySpaceException(ErrorStatus.NOT_INCLUDE_USER_FAMILY_SPACE);
        }

        FamilyEmoticon familyEmoticon = FamilyEmoticonConverter.toFamilyEmoticon(createFamilyEmoticonRequest, familySpace);
        familyEmoticonRepository.save(familyEmoticon);

        return familyEmoticon;
    }

    @Override
    public void deleteFamilyEmoticon(Long familySpaceId, User user, Long familyEmoticonId) {

        FamilySpace familySpace = familySpaceRepository.findById(familySpaceId).get();

        if (!userRepository.existsByFamilySpaceAndId(familySpace, user.getId())) {
            throw new FamilySpaceException(ErrorStatus.NOT_INCLUDE_USER_FAMILY_SPACE);
        }

        FamilyEmoticon familyEmoticon = familyEmoticonRepository.findById(familyEmoticonId).orElseThrow(()->{
            throw new FamilyEmoticonException(ErrorStatus.FAMILY_EMOTICON_NOT_FOUND);
        });

        familyEmoticonRepository.delete(familyEmoticon);
    }
}
