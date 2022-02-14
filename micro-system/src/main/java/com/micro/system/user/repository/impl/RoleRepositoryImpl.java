package com.micro.system.user.repository.impl;


import com.groot.base.common.SearchData;
import com.groot.base.web.repository.BaseRepository;
import com.micro.system.user.model.QRole;
import com.micro.system.user.model.Role;
import com.micro.system.user.repository.RoleRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
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
        JPAQuery<Role> query = queryFactory.selectFrom(role).where(where);
        return this.search(query, pageable);
    }

    @Override
    public void batchDeleteByIds(Long[] ids) {
        QRole role = QRole.role;
        queryFactory.delete(role).where(role.id.in(ids)).execute();
    }

    @Override
    public List<Long> findRoleIdsByRoleName(String roleName) {
        QRole role = QRole.role;
        return queryFactory.select(role.id).from(role).where(role.name.like("%" + roleName + "%")).fetch();
    }

    @Override
    public void changeRoleEnabled(Role toModel) {
        QRole role = QRole.role;
        queryFactory.update(role).set(role.enabled, toModel.getEnabled()).where(role.id.eq(toModel.getId())).execute();
    }


    @Override
    protected Class<?> getModelClass() {
        return Role.class;
    }
}
