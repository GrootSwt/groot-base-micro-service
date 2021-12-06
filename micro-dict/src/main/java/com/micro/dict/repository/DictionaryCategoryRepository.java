package com.micro.dict.repository;

import com.micro.dict.model.DictionaryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DictionaryCategoryRepository extends JpaRepository<DictionaryCategory, Long>,DictionaryCategoryRepositoryCustom {

    void deleteAllByIdIn(Collection<Long> ids);
}
