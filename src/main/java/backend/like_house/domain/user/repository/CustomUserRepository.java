package backend.like_house.domain.user.repository;

import com.querydsl.core.Tuple;

import java.util.List;

public interface CustomUserRepository {
    List<Tuple> getEmailAndSocialTypeByChatRoomId(Long chatRoomId);
}
