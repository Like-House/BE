package backend.like_house.global.redis;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.FamilySpaceException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import backend.like_house.domain.user.entity.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private static final int CODE_LENGTH_MIN = 8;
    private static final int CODE_LENGTH_MAX = 12;
    private static final String CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom random = new SecureRandom();

    public void saveRefreshToken(String email, SocialType socialType, String refreshToken) {
        String key = generateKey(email, socialType.toString());
        redisTemplate.opsForValue().set(key, refreshToken, 14, TimeUnit.DAYS); // 14일간 유지
    }

    public void saveFamilySpaceCode(Long familySpaceId, String code) {
        redisTemplate.opsForValue().set(String.valueOf(familySpaceId), code, 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(code, String.valueOf(familySpaceId), 7, TimeUnit.DAYS);
    }

    public void saveEmailCode(String receiver, String code) {
        redisTemplate.opsForValue().set(receiver, code, 5, TimeUnit.MINUTES);
    }

    public LocalDateTime getFamilySpaceCodeExpirationByCode(String code) {
        Long expiration = redisTemplate.getExpire(code, TimeUnit.SECONDS);
        if (expiration == null || expiration <= 0) {
            throw new FamilySpaceException(ErrorStatus.FAMILY_SPACE_CODE_EXPIRATION_INVALID);
        } else {
            return LocalDateTime.now().plusSeconds(expiration);
        }
    }

    public Long getFamilySpaceIdByCode(String code) {
        String familySpaceId = redisTemplate.opsForValue().get(code);
        if (familySpaceId == null) {
            throw new FamilySpaceException(ErrorStatus.FAMILY_SPACE_CODE_EXPIRATION_INVALID);
        } else {
            return Long.parseLong(familySpaceId);
        }
    }

    public String getOrGenerateFamilySpaceCodeById(Long familySpaceId) {
        String code = redisTemplate.opsForValue().get(String.valueOf(familySpaceId));
        if (code == null) {
            code = generateUniqueFamilySpaceCode();
            saveFamilySpaceCode(familySpaceId, code);
        }

        return code;
    }

    public String getEmailVerificationCode(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public String generateFamilySpaceCodeById(Long familySpaceId) {
        String code = redisTemplate.opsForValue().get(String.valueOf(familySpaceId));
        if (code == null) {
            code = generateUniqueFamilySpaceCode();
            saveFamilySpaceCode(familySpaceId, code);
        }
        Long expiration = redisTemplate.getExpire(code, TimeUnit.SECONDS);
        if (expiration == null || expiration <= 0) {
            redisTemplate.delete(String.valueOf(familySpaceId));
            redisTemplate.delete(code);
            code = generateUniqueFamilySpaceCode();
            saveFamilySpaceCode(familySpaceId, code);
        }
        return code;
    }

    public String resetFamilySpaceCodeById(Long familySpaceId) {
        String id = String.valueOf(familySpaceId);
        String existingCode = redisTemplate.opsForValue().get(id);

        if (existingCode != null) {
            redisTemplate.delete(id);
            redisTemplate.delete(existingCode);
        }

        String newCode = generateUniqueFamilySpaceCode();
        saveFamilySpaceCode(familySpaceId, newCode);
        return newCode;
    }

    private String generateUniqueFamilySpaceCode() {
        String code;

        do {
            code = generateRandomCode();
        } while (Boolean.TRUE.equals(redisTemplate.hasKey(code)));

        return code;
    }

    private String generateRandomCode() {
        int length = CODE_LENGTH_MIN + random.nextInt(CODE_LENGTH_MAX - CODE_LENGTH_MIN + 1);
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CODE_CHARACTERS.charAt(random.nextInt(CODE_CHARACTERS.length())));
        }

        return sb.toString();
    }

    // email과 socialType으로 키 생성
    private String generateKey(String email, String socialType) {
        return email + ":" + socialType;
    }

    public void deleteEmailVerificationCode(String email) {
        redisTemplate.delete(email);
    }
}
