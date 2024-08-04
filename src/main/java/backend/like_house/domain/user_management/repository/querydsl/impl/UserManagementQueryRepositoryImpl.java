package backend.like_house.domain.user_management.repository.querydsl.impl;

import static backend.like_house.domain.user.entity.QUser.*;
import static backend.like_house.domain.user_management.entity.QBlockUser.*;
import static backend.like_house.domain.user_management.entity.QContact.*;
import static backend.like_house.domain.user_management.entity.QCustom.*;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementResponse.FamilyData;
import backend.like_house.domain.user_management.repository.querydsl.UserManagementQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserManagementQueryRepositoryImpl implements UserManagementQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FamilyData> findFamilyUser(User managerUser) {
        return jpaQueryFactory.select(Projections.constructor(FamilyData.class,
                user.id, user.profileImage, user.name, custom.nickname, custom.memo, user.isRoomManager))
                .from(user)
                .leftJoin(contact).on(contact.user.eq(managerUser).and(contact.familySpace.eq(managerUser.getFamilySpace()))
                        .and(contact.profileId.eq(user.id)))
                .leftJoin(custom).on(custom.contact.eq(contact))
                .where(user.familySpace.eq(managerUser.getFamilySpace()))
                .fetch();
    }

    @Override
    public List<FamilyData> findFamilyBlockUser(User managerUser) {
        return jpaQueryFactory.select(Projections.constructor(FamilyData.class,
                        user.id, user.profileImage, user.name, custom.nickname, custom.memo, user.isRoomManager))
                .from(user)
                .innerJoin(blockUser).on(blockUser.user.eq(user).and(blockUser.familySpace.eq(managerUser.getFamilySpace())))
                .leftJoin(contact).on(contact.user.eq(managerUser).and(contact.familySpace.eq(managerUser.getFamilySpace()))
                        .and(contact.profileId.eq(user.id)))
                .leftJoin(custom).on(custom.contact.eq(contact))
                .fetch();
    }
}
