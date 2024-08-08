package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.domain.user_management.converter.UserManagementConverter;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.domain.user_management.entity.Custom;
import backend.like_house.domain.user_management.repository.BlockUserRepository;
import backend.like_house.domain.user_management.repository.ContactRepository;
import backend.like_house.domain.user_management.repository.CustomRepository;
import backend.like_house.domain.user_management.service.UserManagementCommandService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.FamilySpaceException;
import backend.like_house.global.error.handler.UserException;
import backend.like_house.global.error.handler.UserManagementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserManagementCommandServiceImpl implements UserManagementCommandService {

    private final ContactRepository contactRepository;
    private final CustomRepository customRepository;
    private final BlockUserRepository blockUserRepository;
    private final UserRepository userRepository;

    @Override
    public Custom modifyFamilyCustom(User user, Long userId, ModifyFamilyDataRequest request) {
        User modifyUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        if (!modifyUser.getFamilySpace().equals(user.getFamilySpace())) {
            throw new FamilySpaceException(ErrorStatus.NOT_INCLUDE_USER_FAMILY_SPACE);
        }

        Optional<Contact> contact = contactRepository.findByUserAndFamilySpaceAndProfileId(
                user, user.getFamilySpace(), userId);
        Custom custom;
        if (contact.isPresent()) {
            custom = customRepository.findByContact(contact.get()).get();
            custom.setUpdateCustom(request);
        } else {
            Contact saveContact = contactRepository.save(UserManagementConverter.toContact(user, userId));
            custom = customRepository.save(UserManagementConverter.toCustom(saveContact, request));
        }
        return custom;
    }

    @Override
    public void blockUser(User manager, Long blockUserId) {
        User blockUser = userRepository.findById(blockUserId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        if (blockUser.equals(manager)) {
            throw new UserManagementException(ErrorStatus.CANNOT_BLOCK_YOURSELF);
        }
        if (blockUserRepository.existsByUserAndFamilySpace(blockUser, manager.getFamilySpace())) {
            throw new UserManagementException(ErrorStatus.ALREADY_BLOCKED_USER);
        }
        if (!blockUser.getFamilySpace().equals(manager.getFamilySpace())) {
            throw new FamilySpaceException(ErrorStatus.NOT_INCLUDE_USER_FAMILY_SPACE);
        }

        blockUser.setFamilySpace(null);
        blockUserRepository.save(UserManagementConverter.toBlockUser(blockUser, manager.getFamilySpace()));
    }

    @Override
    public void releaseBlockUser(User manager, Long blockUserId) {
        User blockUser = userRepository.findById(blockUserId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        if (blockUser.equals(manager)) {
            throw new UserManagementException(ErrorStatus.CANNOT_RELEASE_BLOCK_YOURSELF);
        }
        if (!blockUserRepository.existsByUserAndFamilySpace(blockUser, manager.getFamilySpace())) {
            throw new UserManagementException(ErrorStatus.ALREADY_RELEASE_BLOCK_USER);
        }
        if (blockUser.getFamilySpace() != null) {
            throw new FamilySpaceException(ErrorStatus.ALREADY_BELONG_OTHER_FAMILY_SPACE);
        }

        blockUser.setFamilySpace(manager.getFamilySpace());
        blockUserRepository.deleteByUserAndFamilySpace(blockUser, manager.getFamilySpace());
    }
}
