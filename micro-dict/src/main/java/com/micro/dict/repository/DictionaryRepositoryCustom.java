package com.micro.dict.repository;


import com.groot.base.common.SearchData;
import com.micro.dict.model.Dictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DictionaryRepositoryCustom {
    Page<Dictionary> pageableSearch(SearchData searchData, Pageable pageable);

    void modifyEnabled(Dictionary model);

    List<Dictionary> conditionSearch(SearchData searchData);
}
