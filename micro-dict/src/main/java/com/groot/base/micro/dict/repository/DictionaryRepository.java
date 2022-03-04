package com.groot.base.micro.dict.repository;


import com.groot.base.micro.dict.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DictionaryRepository extends JpaRepository<Dictionary, String>, DictionaryRepositoryCustom {

    /**
     * 根据id集合删除数据字典
     *
     * @param ids id集合
     */
    void deleteAllByIdIn(Collection<String> ids);

    /**
     * 根据数据字典类别id集合删除数据字典
     *
     * @param ids 数据字典类别id集合
     */
    void deleteAllByCategoryIdIn(Collection<String> ids);
}
