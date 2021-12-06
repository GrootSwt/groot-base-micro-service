package com.micro.dict.repository;

import com.micro.base.common.bean.SearchData;
import com.micro.dict.model.Dictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictionaryRepositoryCustom {
    Page<Dictionary> pageableSearch(SearchData searchData, Pageable pageable);

    void modifyEnabled(Dictionary model);
}
