package com.groot.base.micro.dict.repository;


import com.groot.base.micro.dict.model.DictionaryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DictionaryCategoryRepository extends JpaRepository<DictionaryCategory, String>,DictionaryCategoryRepositoryCustom {

    void deleteAllByIdIn(Collection<String> ids);
}
