package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.converter.UserManagementConverter;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.domain.user_management.entity.Custom;
import backend.like_house.domain.user_management.repository.BlockUserRepository;
import backend.like_house.domain.user_management.repository.ContactRepository;
import backend.like_house.domain.user_management.repository.CustomRepository;
import backend.like_house.domain.user_management.repository.RemoveUserRepository;
import backend.like_house.domain.user_management.service.UserManagementCommandService;
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
    private final RemoveUserRepository removeUserRepository;
    private final BlockUserRepository blockUserRepository;

    @Override
    public Custom modifyFamilyCustom(User user, Long userId, ModifyFamilyDataRequest request) {
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
    public void removeUser(User manager, User removeUser) {
        removeUser.setFamilySpace(null);
        removeUserRepository.save(UserManagementConverter.toRemoveUser(removeUser, manager.getFamilySpace()));
    }

    @Override
    public void releaseRemoveUser(User manager, User removeUser) {
        removeUser.setFamilySpace(manager.getFamilySpace());
        removeUserRepository.deleteByUserAndFamilySpace(removeUser, manager.getFamilySpace());
    }

    @Override
    public void blockUser(User manager, User blockUser) {
        blockUser.setFamilySpace(null);
        blockUserRepository.save(UserManagementConverter.toBlockUser(blockUser, manager.getFamilySpace()));
    }

    @Override
    public void releaseBlockUser(User manager, User blockUser) {
        blockUser.setFamilySpace(manager.getFamilySpace());
        blockUserRepository.deleteByUserAndFamilySpace(blockUser, manager.getFamilySpace());
    }
}
