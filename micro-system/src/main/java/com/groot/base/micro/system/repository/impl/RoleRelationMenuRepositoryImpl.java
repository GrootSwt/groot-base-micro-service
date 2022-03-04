package com.groot.base.micro.system.repository.impl;


import com.groot.base.web.repository.BaseRepository;
import com.groot.base.micro.system.model.QRoleRelationMenu;
import com.groot.base.micro.system.model.RoleRelationMenu;
import com.groot.base.micro.system.repository.RoleRelationMenuRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRelationMenuRepositoryImpl extends BaseRepository implements RoleRelationMenuRepositoryCustom {
    @Override
    public List<String> getMenuIdsByRoleId(String roleId) {
        QRoleRelationMenu roleRelationMenu = QRoleRelationMenu.roleRelationMenu;
        return queryFactory
                .select(roleRelationMenu.menuId)
                .from(roleRelationMenu)
                .where(roleRelationMenu.roleId.eq(roleId))
                .fetch();
    }

    @Override
    protected Class<?> getModelClass() {
        return RoleRelationMenu.class;
    }
}
