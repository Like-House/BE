package backend.like_house.domain.user.repository;

import backend.like_house.domain.user.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.like_house.domain.chatting.entity.QChatRoom.chatRoom;
import static backend.like_house.domain.chatting.entity.QUserChatRoom.userChatRoom;
import static backend.like_house.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Tuple> getEmailAndSocialTypeByChatRoomId(
            Long chatRoomId
    ) {
        return queryFactory
                .select(user.email, user.socialType)
                .from(userChatRoom)
                .join(userChatRoom.user, user)
                .join(userChatRoom.chatRoom, chatRoom)
                .where(chatRoom.id.eq(chatRoomId))
                .fetch();
    }

    @Override
    public List<User> getUserByChatRoomId(
            Long chatRoomId
    ) {
        return queryFactory
                .select(user)
                .from(userChatRoom)
                .join(userChatRoom.user, user)
                .join(userChatRoom.chatRoom, chatRoom)
                .where(chatRoom.id.eq(chatRoomId))
                .fetch();
    }
}
