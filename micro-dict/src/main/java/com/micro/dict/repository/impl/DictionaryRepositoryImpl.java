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

import java.util.Calendar;
import java.util.Date;
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
            where.and(dictionary.categoryId.eq(searchData.getLongValue("categoryId")));
        }
        if (searchData.hasKey("dictionaryKey")) {
            where.and(dictionary.dictionaryKey.like("%" + searchData.getStringValue("dictionaryKey") + "%"));
        }
        if (searchData.hasKey("startDate") && searchData.hasKey("endDate")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(searchData.getDateValue("endDate"));
            calendar.add(Calendar.DATE, 1);
            Date endDate = calendar.getTime();
            where.and(dictionary.createTime.after(searchData.getDateValue("startDate")));
            where.and(dictionary.createTime.before(endDate));
        }
        if (searchData.hasKey("enabled")) {
            where.and(dictionary.enabled.eq(searchData.getStringValue("enabled")));
        }
        JPAQuery<Dictionary> jpaQuery = queryFactory.selectFrom(dictionary).where(where);
        return this.search(jpaQuery, pageable, dictionary.createTime.asc());
    }

    @Override
    public void modifyEnabled(Dictionary model) {
        QDictionary dictionary = QDictionary.dictionary;
        queryFactory
                .update(dictionary)
                .set(dictionary.enabled, model.getEnabled())
                .where(dictionary.id.eq(model.getId()))
                .execute();
    }

    @Override
    public List<Dictionary> conditionSearch(SearchData searchData) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("categoryId")) {
            where.and(dictionary.categoryId.eq(searchData.getLongValue("categoryId")));
        }
        return queryFactory.selectFrom(dictionary).where(where).orderBy(dictionary.createTime.asc()).fetch();
    }
}
