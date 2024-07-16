package backend.like_house.global.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "UserToken", timeToLive = 3600 * 24 * 14)
@AllArgsConstructor
@Getter
@Setter
public class Redis {

    @Id
    private String accessToken;
    private String refreshToken;
}
