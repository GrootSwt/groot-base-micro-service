package com.micro.dict.service.impl;

import com.groot.base.common.SearchData;
import com.micro.dict.model.DictionaryCategory;
import com.micro.dict.repository.DictionaryCategoryRepository;
import com.micro.dict.repository.DictionaryRepository;
import com.micro.dict.service.DictionaryCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@Service
public class DictionaryCategoryServiceImpl implements DictionaryCategoryService {

    @Resource
    private DictionaryCategoryRepository dictionaryCategoryRepository;
    @Resource
    private DictionaryRepository dictionaryRepository;

    @Override
    public void save(DictionaryCategory model) {
        if (model.getId() == null) {
            model.setCreateTime(new Date());
        }
        model.setModifyTime(new Date());
        dictionaryCategoryRepository.save(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(Long[] idArr) {
        // 根据类别id集合删除数据字典
        dictionaryRepository.deleteAllByCategoryIdIn(Arrays.asList(idArr));
        dictionaryCategoryRepository.deleteAllByIdIn(Arrays.asList(idArr));
    }

    @Override
    public Page<DictionaryCategory> pageableSearch(SearchData searchData, Pageable pageable) {
        return dictionaryCategoryRepository.pageableSearch(searchData, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyEnabled(DictionaryCategory model) {
        dictionaryCategoryRepository.modifyEnable(model);
    }
}
