package com.micro.user.repository.impl;

import com.micro.user.base.BaseRepository;
import com.micro.user.model.QRoleRelationMenu;
import com.micro.user.model.RoleRelationMenu;
import com.micro.user.repository.RoleRelationMenuRepositoryCustom;

import java.util.List;

public class RoleRelationMenuRepositoryImpl extends BaseRepository implements RoleRelationMenuRepositoryCustom {
    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        QRoleRelationMenu roleRelationMenu = QRoleRelationMenu.roleRelationMenu;
        return queryFactory().selectFrom(roleRelationMenu).select(roleRelationMenu.menuId).where(roleRelationMenu.roleId.eq(roleId)).fetch();
    }

    @Override
    protected Class<?> getModelClass() {
        return RoleRelationMenu.class;
    }
}
