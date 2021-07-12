package com.micro.user.repository.impl;

import com.micro.common.util.SearchData;
import com.micro.user.base.BaseRepository;
import com.micro.user.model.QRole;
import com.micro.user.model.Role;
import com.micro.user.repository.RoleRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepositoryImpl extends BaseRepository implements RoleRepositoryCustom {
    @Override
    public Page<Role> pageableSearch(SearchData searchData, Pageable pageable) {
        QRole role = QRole.role;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("name")) {
            where.and(role.name.like("%" + searchData.getStringValue("name") + "%"));
        }
        JPAQuery<Role> query = queryFactory().selectFrom(role).where(where);
        long total = query.fetchCount();
        List<Role> roleList = querydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(roleList, pageable, total);
    }

    @Override
    public void batchDeleteByIds(Long[] ids) {
        QRole role = QRole.role;
        queryFactory().delete(role).where(role.id.in(ids)).execute();
    }

    @Override
    public List<Long> findRoleIdsByRoleName(String roleName) {
        QRole role = QRole.role;
        return queryFactory().select(role.id).from(role).where(role.name.like("%" + roleName + "%")).fetch();
    }

    @Override
    public List<String> getAllRoleName() {
        QRole role = QRole.role;
        return queryFactory().select(role.name).from(role).fetch();
    }

    @Override
    protected Class<?> getModelClass() {
        return Role.class;
    }
}
