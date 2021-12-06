package com.micro.dict.service;

import com.micro.base.common.bean.SearchData;
import com.micro.dict.model.Dictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictionaryService {
    Page<Dictionary> pageableSearch(SearchData searchData, Pageable pageable);

    void save(Dictionary model);

    void modifyEnabled(Dictionary toModel);

    void batchDelete(Long[] ids);
}
