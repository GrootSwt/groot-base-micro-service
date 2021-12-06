package com.micro.system.repository.impl;

import com.micro.base.common.bean.SearchData;
import com.micro.base.web.repository.BaseRepository;
import com.micro.system.bean.ChangePasswordBean;
import com.micro.system.model.QUser;
import com.micro.system.model.User;
import com.micro.system.repository.UserRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryCustom {

    @Override
    public Page<User> pageableSearch(Pageable pageable, SearchData searchData) {
        QUser user = QUser.user;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("username")) {
            where.and(user.username.like("%" + searchData.getStringValue("username") + "%"));
        }
        if (searchData.hasKey("roleIds")) {
            where.and(user.roleId.in(searchData.getLongArrayValue("roleIds")));
        }
        JPAQuery<User> query = queryFactory.selectFrom(user).where(where);
        return this.search(query, pageable);
    }

    @Override
    public void changeUserEnable(User toModel) {
        QUser user = QUser.user;
        queryFactory
                .update(user)
                .set(user.enabled, toModel.getEnabled())
                .where(user.id.eq(toModel.getId()))
                .execute();
    }

    @Override
    public void changePassword(ChangePasswordBean changePasswordBean) {
        QUser user = QUser.user;
        queryFactory
                .update(user)
                .set(user.password, changePasswordBean.getNewPassword())
                .where(user.id.eq(changePasswordBean.getId()))
                .execute();
    }

    @Override
    protected Class<?> getModelClass() {
        return User.class;
    }
}
