package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.entity.ChatRoom;
import com.amazonaws.services.ec2.model.UserData;
import org.springframework.data.domain.Slice;

public interface CustomChatRoomRepository {

    Slice<ChatRoom> getChatRoomsByUserIdAndFamilySpaceId(Long userId, Long familySpaceId, Long cursor, Integer take);

    ChatRoomDTO.ChatRoomData getUserDataByUserIdAndChatRoomId(Long userId, Long chatRoomId);
}
