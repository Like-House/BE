package backend.like_house.domain.family_space.repository.querydsl;

import backend.like_house.domain.family_space.entity.FamilySpace;

public interface FamilySpaceQueryRepository {

    void deleteAllUserConnectFamilySpace(FamilySpace familySpace);
}
