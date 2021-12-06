package com.micro.dict.repository;

import com.micro.base.common.bean.SearchData;
import com.micro.dict.model.DictionaryCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictionaryCategoryRepositoryCustom {
    Page<DictionaryCategory> pageableSearch(SearchData searchData, Pageable pageable);

    void modifyEnable(DictionaryCategory model);
}
