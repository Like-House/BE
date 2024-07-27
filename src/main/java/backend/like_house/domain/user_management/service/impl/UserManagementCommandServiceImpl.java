package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.domain.user_management.entity.Custom;
import backend.like_house.domain.user_management.repository.ContactRepository;
import backend.like_house.domain.user_management.repository.CustomRepository;
import backend.like_house.domain.user_management.service.UserManagementCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserManagementCommandServiceImpl implements UserManagementCommandService {

    private final ContactRepository contactRepository;
    private final CustomRepository customRepository;

    @Override
    public Custom modifyFamilyCustom(User user, Long userId, ModifyFamilyDataRequest request) {

        return null;
    }
}
