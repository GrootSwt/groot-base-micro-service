package com.micro.dict.repository;

import com.micro.dict.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, DictionaryRepositoryCustom {

    void deleteAllByIdIn(Collection<Long> ids);
}
