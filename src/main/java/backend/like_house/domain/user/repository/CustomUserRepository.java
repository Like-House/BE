package backend.like_house.domain.user.repository;

import backend.like_house.domain.user.entity.User;
import com.querydsl.core.Tuple;

import java.util.List;

public interface CustomUserRepository {
    List<Tuple> getEmailAndSocialTypeByChatRoomId(Long chatRoomId);

    List<User> getUserByChatRoomId(
            Long chatRoomId
    );
}
