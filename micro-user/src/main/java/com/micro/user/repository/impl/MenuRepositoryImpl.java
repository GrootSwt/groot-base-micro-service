package com.micro.user.repository.impl;

import com.micro.common.util.SearchData;
import com.micro.user.base.BaseRepository;
import com.micro.user.model.Menu;
import com.micro.user.model.QMenu;
import com.micro.user.repository.MenuRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MenuRepositoryImpl extends BaseRepository implements MenuRepositoryCustom {

    @Override
    public Page<Menu> pageableMenu(SearchData searchData, Pageable pageable) {
        QMenu menu = QMenu.menu;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("enabled")) {
            where.and(menu.enabled.eq(searchData.getStringValue("enabled")));
        }
        JPAQuery<Menu> query = queryFactory().selectFrom(menu).where(where);
        long count = query.fetchCount();
        List<Menu> menuList = querydsl().applyPagination(pageable, query).orderBy(menu.enabled.desc()).fetch();
        return new PageImpl<>(menuList, pageable, count);
    }

    @Override
    public void deleteMenuByIdArr(Long[] idArr) {
        QMenu menu = QMenu.menu;
        queryFactory().delete(menu).where(menu.id.in(idArr)).execute();
    }

    @Override
    protected Class<?> getModelClass() {
        return Menu.class;
    }
}
