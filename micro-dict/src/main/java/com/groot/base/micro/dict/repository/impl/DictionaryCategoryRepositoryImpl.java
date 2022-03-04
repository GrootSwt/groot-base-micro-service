package com.groot.base.micro.dict.repository.impl;


import com.groot.base.common.SearchData;
import com.groot.base.web.repository.BaseRepository;
import com.groot.base.micro.dict.model.DictionaryCategory;
import com.groot.base.micro.dict.model.QDictionaryCategory;
import com.groot.base.micro.dict.repository.DictionaryCategoryRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;

public class DictionaryCategoryRepositoryImpl extends BaseRepository implements DictionaryCategoryRepositoryCustom {
    @Override
    protected Class<?> getModelClass() {
        return DictionaryCategory.class;
    }

    @Override
    public Page<DictionaryCategory> pageableSearch(SearchData searchData, Pageable pageable) {
        QDictionaryCategory dictionaryCategory = QDictionaryCategory.dictionaryCategory;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("categoryName")) {
            where.and(dictionaryCategory.categoryName.like("%" + searchData.getStringValue("categoryName") + "%"));
        }
        if (searchData.hasKey("startDate") && searchData.hasKey("endDate")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(searchData.getDateValue("endDate"));
            calendar.add(Calendar.DATE, 1);
            Date endDate = calendar.getTime();
            where.and(dictionaryCategory.createTime.after(searchData.getDateValue("startDate")));
            where.and(dictionaryCategory.createTime.before(endDate));
        }
        if (searchData.hasKey("enabled")){
            where.and(dictionaryCategory.enabled.eq(searchData.getStringValue("enabled")));
        }
        JPAQuery<DictionaryCategory> jpaQuery = queryFactory.selectFrom(dictionaryCategory).where(where);
            return this.search(jpaQuery, pageable, dictionaryCategory.createTime.asc());
    }

    @Override
    public void modifyEnable(DictionaryCategory model) {
        QDictionaryCategory dictionaryCategory = QDictionaryCategory.dictionaryCategory;
        queryFactory
                .update(dictionaryCategory)
                .set(dictionaryCategory.enabled, model.getEnabled())
                .where(dictionaryCategory.id.eq(model.getId()))
                .execute();
    }
}
