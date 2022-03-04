package com.groot.base.micro.dict.convertor;


import com.groot.base.web.convertor.BaseConvertor;
import com.groot.base.micro.dict.dto.DictionaryDTO;
import com.groot.base.micro.dict.model.Dictionary;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DictionaryConvertor extends BaseConvertor<Dictionary, DictionaryDTO> {
    @Override
    public Dictionary toModel(DictionaryDTO dictionaryDTO) {
        Dictionary model = new Dictionary();
        BeanUtils.copyProperties(dictionaryDTO, model);
        return model;
    }

    @Override
    public DictionaryDTO toDTO(Dictionary dictionary) {
        DictionaryDTO dto = new DictionaryDTO();
        BeanUtils.copyProperties(dictionary, dto);
        return dto;
    }
}
