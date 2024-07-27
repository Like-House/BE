package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.entity.QChat;
import backend.like_house.domain.chatting.entity.QChatRoom;
import backend.like_house.domain.chatting.entity.QUserChatRoom;
import backend.like_house.domain.family_space.entity.QFamilySpace;
import backend.like_house.domain.user.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomChatRoomRepositoryImpl implements CustomChatRoomRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public Slice<ChatRoom> getChatRoomsByUserIdAndFamilySpaceId(Long userId, Long familySpaceId, Long cursor, Integer take) {
        QUserChatRoom userChatRoom = QUserChatRoom.userChatRoom;
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChat chat = QChat.chat;

        BooleanExpression predicate = userChatRoom.user.id.eq(userId)
                .and(chatRoom.familySpace.id.eq(familySpaceId))
                .and(userChatRoom.chatRoom.id.eq(chatRoom.id));

        // If cursor is provided, add a predicate for pagination based on Chat's PK
        if (cursor != null) {
            predicate = predicate.and(chat.id.lt(cursor)); // Use lt for descending order
        }

        List<ChatRoom> chatRooms = queryFactory.selectFrom(chatRoom)
                                        .join(userChatRoom).on(userChatRoom.chatRoom.id.eq(chatRoom.id))
                                        .join(chat).on(chat.chatRoom.id.eq(chatRoom.id))
                                        .where(predicate)
                                        .orderBy(chat.id.desc())
                                        .limit(take + 1)
                                        .fetch();

        System.out.println(chatRooms);

        boolean hasNext = chatRooms.size() > take;
        if (hasNext) {
            chatRooms.remove(take.intValue());
        }

        PageRequest pageRequest = PageRequest.of(0, take);

        return new SliceImpl<>(chatRooms, pageRequest, hasNext);
    }
}
