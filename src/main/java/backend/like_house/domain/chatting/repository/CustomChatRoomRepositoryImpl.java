package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.entity.QChat;
import backend.like_house.domain.chatting.entity.QChatRoom;
import backend.like_house.domain.chatting.entity.QUserChatRoom;
import backend.like_house.domain.family_space.entity.QFamilySpace;
import backend.like_house.domain.user.entity.QUser;
import com.amazonaws.services.ec2.model.UserData;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static backend.like_house.domain.chatting.dto.ChatRoomDTO.*;

@Repository
@RequiredArgsConstructor
public class CustomChatRoomRepositoryImpl implements CustomChatRoomRepository {

    private final JPAQueryFactory queryFactory;
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Slice<ChatRoom> getChatRoomsByUserIdAndFamilySpaceId(Long userId, Long familySpaceId, Long cursor, Integer take) {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChat chat = QChat.chat;
        QUserChatRoom userChatRoom = QUserChatRoom.userChatRoom;

        // 쿼리 생성
        List<Tuple> results = queryFactory
                .select(chatRoom, chat.id.max()) // chat_room과 max(chat.id)를 선택
                .from(chatRoom)
                .join(chat).on(chatRoom.id.eq(chat.chatRoom.id)) // chat과 조인
                .join(userChatRoom).on(userChatRoom.chatRoom.id.eq(chatRoom.id)) // user_chat_room과 조인
                .where(userChatRoom.user.id.eq(userId) // user_id 조건
                        .and(chatRoom.familySpace.id.eq(familySpaceId))) // family_space_id 조건
                .groupBy(chatRoom.id) // chat_room.id로 그룹화
                .having(chat.id.max().lt(cursor))
                .orderBy(chat.id.max().desc()) // 마지막 채팅이 있는 것부터 내림차순 정렬
                .limit(take + 1) // 요청된 개수 + 1
                .fetch();

        boolean hasNext = results.size() > take;
        List<ChatRoom> chatRooms = results.stream()
                .map(tuple -> tuple.get(chatRoom)) // Tuple에서 ChatRoom 객체 추출
                .collect(Collectors.toList());

        if (hasNext) {
            chatRooms.remove(chatRooms.size() - 1); // 마지막 요소 제거
        }

        return new SliceImpl<>(chatRooms, PageRequest.of(0, take), hasNext); // 페이지 정보와 함께 Slice 반환
    }

    @Override
    public ChatRoomData getUserDataByUserIdAndChatRoomId(Long userId, Long chatRoomId) {
        QUserChatRoom userChatRoom = QUserChatRoom.userChatRoom;
        QUser user = QUser.user;

         return queryFactory
                .select(Projections.constructor(ChatRoomData.class, user.name, user.profileImage))
                .from(userChatRoom)
                .join(user)
                .on(userChatRoom.user.eq(user))
                .where(userChatRoom.chatRoom.id.eq(chatRoomId).and(userChatRoom.user.id.ne(userId)))
                .fetchOne();
    }

}