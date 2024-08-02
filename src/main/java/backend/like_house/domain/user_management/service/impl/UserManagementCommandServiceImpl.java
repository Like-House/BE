package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.family_space.entity.FamilySpace;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserManagementCommandServiceImpl implements UserManagementCommandService {

    private final UserManagementCommandService self;
    private final ContactRepository contactRepository;
    private final CustomRepository customRepository;
    private final RemoveUserRepository removeUserRepository;
    private final BlockUserRepository blockUserRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserPostTagRepository userPostTagRepository;
    private final CommentRepository commentRepository;

    @Transactional
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

    @Transactional
    @Override
    public void removeUser(User manager, User removeUser) {
        removeUser.setFamilySpace(null);
        removeUserRepository.save(UserManagementConverter.toRemoveUser(removeUser, manager.getFamilySpace()));
    }

    @Transactional
    @Override
    public void releaseRemoveUser(User manager, User removeUser) {
        removeUser.setFamilySpace(manager.getFamilySpace());
        removeUserRepository.deleteByUserAndFamilySpace(removeUser, manager.getFamilySpace());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void blockUser(User manager, User blockUser) {
        FamilySpace familySpace = manager.getFamilySpace();
        blockUser.setFamilySpace(null);

        self.deleteCustomContactByUser(blockUser, familySpace);
        self.deletePostLikesByUser(blockUser);
        self.deleteCommentsByUser(blockUser);
        self.deletePostsByUser(blockUser);
        self.deletePostTagByUser(blockUser);
        self.deletePostsWithoutTags();

        // TODO 채팅, 알림

        blockUserRepository.save(UserManagementConverter.toBlockUser(blockUser, familySpace));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteCustomContactByUser(User blockUser, FamilySpace familySpace) {
        contactRepository.deleteByFamilySpaceAndProfileId(familySpace, blockUser.getId());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deletePostLikesByUser(User blockUser) {
        postLikeRepository.deleteByUser(blockUser);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deletePostsWithoutTags() {
        postRepository.deletePostsWithoutTags();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deletePostTagByUser(User blockUser) {
        userPostTagRepository.deleteAllByUser(blockUser);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteCommentsByUser(User blockUser) {
        commentRepository.deleteByUser(blockUser);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deletePostsByUser(User blockUser) {
        postRepository.deleteByUser(blockUser);
    }

    @Transactional
    @Override
    public void releaseBlockUser(User manager, User blockUser) {
        blockUser.setFamilySpace(manager.getFamilySpace());
        blockUserRepository.deleteByUserAndFamilySpace(blockUser, manager.getFamilySpace());
    }
}
