package com.groot.base.micro.system.repository.impl;

import com.groot.base.common.SearchData;
import com.groot.base.web.repository.BaseRepository;
import com.groot.base.micro.system.model.Menu;
import com.groot.base.micro.system.model.QMenu;
import com.groot.base.micro.system.repository.MenuRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MenuRepositoryImpl extends BaseRepository implements MenuRepositoryCustom {

    @Override
    public Page<Menu> pageableMenu(SearchData searchData, Pageable pageable) {
        QMenu menu = QMenu.menu;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("enabled")) {
            where.and(menu.enabled.eq(searchData.getStringValue("enabled")));
        }
        JPAQuery<Menu> query = queryFactory.selectFrom(menu).where(where);
        return this.search(query, pageable);
    }

    @Override
    public void deleteMenuByIdArr(String[] idArr) {
        QMenu menu = QMenu.menu;
        queryFactory.delete(menu).where(menu.id.in(idArr)).execute();
    }

    @Override
    protected Class<?> getModelClass() {
        return Menu.class;
    }
}
