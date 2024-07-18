package backend.like_house.global.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "UserToken", timeToLive = 3600 * 24 * 14)
@AllArgsConstructor
@Getter
@Builder
public class Redis {

    @Id
    private String email;
    private String refreshToken;
}
