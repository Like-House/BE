package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.domain.user_management.entity.Custom;
import backend.like_house.domain.user_management.repository.ContactRepository;
import backend.like_house.domain.user_management.repository.CustomRepository;
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

    @Override
    public Custom modifyFamilyCustom(User user, Long userId, ModifyFamilyDataRequest request) {
        Optional<Contact> contact = contactRepository.findByUserAndFamilySpaceAndProfileId(
                user, user.getFamilySpace(), userId);
        if (contact.isPresent()) {
            Custom custom = customRepository.findByContact(contact.get()).get();
            custom.setUpdateCustom(request);
        } else {
            // contact 없음 -> contact + custom save
        }
        return null;
    }
}
