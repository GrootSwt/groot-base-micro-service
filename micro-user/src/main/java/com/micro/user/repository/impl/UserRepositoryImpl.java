package com.micro.user.repository.impl;

import com.micro.base.web.repository.BaseRepository;
import com.micro.base.web.bean.SearchData;
import com.micro.user.bean.ChangePasswordBean;
import com.micro.user.model.QUser;
import com.micro.user.model.User;
import com.micro.user.repository.UserRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        JPAQuery<User> query = queryFactory().selectFrom(user).where(where);
        long total = query.fetchCount();
        List<User> userList = querydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(userList, pageable, total);
    }

    @Override
    public void changeUserEnable(User toModel) {
        QUser user = QUser.user;
        queryFactory()
                .update(user)
                .set(user.enabled, toModel.getEnabled())
                .where(user.id.eq(toModel.getId()))
                .execute();
    }

    @Override
    public void changePassword(ChangePasswordBean changePasswordBean) {
        QUser user = QUser.user;
        queryFactory()
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
