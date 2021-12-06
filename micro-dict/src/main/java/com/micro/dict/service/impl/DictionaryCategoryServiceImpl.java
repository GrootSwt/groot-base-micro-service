package com.micro.dict.service.impl;

import com.micro.base.common.bean.SearchData;
import com.micro.dict.model.DictionaryCategory;
import com.micro.dict.repository.DictionaryCategoryRepository;
import com.micro.dict.service.DictionaryCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@Service
public class DictionaryCategoryServiceImpl implements DictionaryCategoryService {

    @Resource
    private DictionaryCategoryRepository dictionaryCategoryRepository;

    @Override
    public void save(DictionaryCategory model) {
        if (model.getId() == null) {
            model.setCreateTime(new Date());
        }
        model.setModifyTime(new Date());
        dictionaryCategoryRepository.save(model);
    }

    @Override
    public void batchDelete(Long[] idArr) {
        dictionaryCategoryRepository.deleteAllByIdIn(Arrays.asList(idArr));
    }

    @Override
    public Page<DictionaryCategory> pageableSearch(SearchData searchData, Pageable pageable) {
        return dictionaryCategoryRepository.pageableSearch(searchData, pageable);
    }

    @Override
    public void modifyEnabled(DictionaryCategory model) {
        dictionaryCategoryRepository.modifyEnable(model);
    }
}
