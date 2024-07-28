package backend.like_house.domain.family_space.repository.querydsl.impl;

import static backend.like_house.domain.user.entity.QUser.user;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.querydsl.FamilySpaceQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FamilySpaceQueryRepositoryImpl implements FamilySpaceQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteAllUserConnectFamilySpace(FamilySpace familySpace) {
        jpaQueryFactory.update(user)
                .set(user.familySpace, (FamilySpace) null)
                .where(user.familySpace.eq(familySpace))
                .execute();
    }
}
