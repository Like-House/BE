package backend.like_house.domain.user.repository;

import backend.like_house.domain.user.entity.QUser;
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
    public List<String> getEmailByChatRoomId(
            Long chatRoomId
    ) {
        return queryFactory
                .select(user.email)
                .from(userChatRoom)
                .join(userChatRoom.user, user)
                .join(userChatRoom.chatRoom, chatRoom)
                .where(chatRoom.id.eq(chatRoomId))
                .fetch();
    }
}
