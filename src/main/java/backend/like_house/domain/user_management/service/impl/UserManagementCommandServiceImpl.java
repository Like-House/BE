package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.chatting.repository.ChatRepository;
import backend.like_house.domain.chatting.repository.UserChatRoomRepository;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.notification.repository.NotificationRepository;
import backend.like_house.domain.post.repository.CommentRepository;
import backend.like_house.domain.post.repository.PostLikeRepository;
import backend.like_house.domain.post.repository.PostRepository;
import backend.like_house.domain.post.repository.UserPostTagRepository;
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
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserPostTagRepository userPostTagRepository;
    private final CommentRepository commentRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final ChatRepository chatRepository;
    private final NotificationRepository notificationRepository;

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
        FamilySpace familySpace = manager.getFamilySpace();
        blockUser.setFamilySpace(null);

        contactRepository.deleteByFamilySpaceAndProfileId(familySpace, blockUser.getId());
        postLikeRepository.deleteByUser(blockUser);
        commentRepository.deleteByUser(blockUser);
        postRepository.deleteByUser(blockUser);
        userPostTagRepository.deleteAllByUser(blockUser);
        postRepository.deletePostsWithoutTags();
        userChatRoomRepository.deleteByUser(blockUser);
        chatRepository.setChatUserNullByUser(blockUser);
        notificationRepository.deleteByUserAndFamilySpace(blockUser, familySpace);

        blockUserRepository.save(UserManagementConverter.toBlockUser(blockUser, familySpace));
    }

    @Override
    public void releaseBlockUser(User manager, User blockUser) {
        blockUser.setFamilySpace(manager.getFamilySpace());
        blockUserRepository.deleteByUserAndFamilySpace(blockUser, manager.getFamilySpace());
    }
}
