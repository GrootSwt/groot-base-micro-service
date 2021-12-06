package com.micro.system.repository.impl;

import com.micro.base.web.repository.BaseRepository;
import com.micro.system.model.QRoleRelationMenu;
import com.micro.system.model.RoleRelationMenu;
import com.micro.system.repository.RoleRelationMenuRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRelationMenuRepositoryImpl extends BaseRepository implements RoleRelationMenuRepositoryCustom {
    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
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
