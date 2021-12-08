package com.micro.dict.repository.impl;

import com.micro.base.common.bean.SearchData;
import com.micro.base.web.repository.BaseRepository;
import com.micro.dict.model.Dictionary;
import com.micro.dict.model.QDictionary;
import com.micro.dict.repository.DictionaryRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class DictionaryRepositoryImpl extends BaseRepository implements DictionaryRepositoryCustom {
    @Override
    protected Class<?> getModelClass() {
        return Dictionary.class;
    }

    @Override
    public Page<Dictionary> pageableSearch(SearchData searchData, Pageable pageable) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("categoryId")) {
            where.and(dictionary.categoryId.eq(searchData.getStringValue("categoryId")));
        }
        JPAQuery<Dictionary> jpaQuery = queryFactory.selectFrom(dictionary).where(where);
        return this.search(jpaQuery, pageable, dictionary.createTime.desc());
    }

    @Override
    public void modifyEnabled(Dictionary model) {
        QDictionary dictionary = QDictionary.dictionary;
        queryFactory
                .update(dictionary)
                .set(dictionary.enabled,model.getEnabled())
                .where(dictionary.id.eq(model.getId()))
                .execute();
    }

    @Override
    public List<Dictionary> conditionSearch(SearchData searchData) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("categoryId")) {
            where.and(dictionary.categoryId.eq(searchData.getStringValue("categoryId")));
        }
        return queryFactory.selectFrom(dictionary).where(where).orderBy(dictionary.createTime.asc()).fetch();
    }
}
