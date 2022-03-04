package com.groot.base.micro.dict.service.impl;


import com.groot.base.common.SearchData;
import com.groot.base.micro.dict.repository.DictionaryRepository;
import com.groot.base.micro.dict.model.Dictionary;
import com.groot.base.micro.dict.service.DictionaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryRepository dictionaryRepository;

    @Override
    public Page<Dictionary> pageableSearch(SearchData searchData, Pageable pageable) {
        return dictionaryRepository.pageableSearch(searchData, pageable);
    }

    @Override
    public void save(Dictionary model) {
        if (model.getId() == null) {
            model.setCreateTime(new Date());
        }
        model.setModifyTime(new Date());
        dictionaryRepository.save(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyEnabled(Dictionary model) {
        dictionaryRepository.modifyEnabled(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(String[] ids) {
        dictionaryRepository.deleteAllByIdIn(Arrays.asList(ids));
    }

    @Override
    public List<Dictionary> conditionSearch(SearchData searchData) {
        return dictionaryRepository.conditionSearch(searchData);
    }
}
