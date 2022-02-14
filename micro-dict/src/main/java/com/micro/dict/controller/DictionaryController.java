package com.micro.dict.controller;


import com.groot.base.common.SearchData;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.bean.result.ResultListDTO;
import com.groot.base.web.bean.result.ResultPageDTO;
import com.micro.dict.convertor.DictionaryConvertor;
import com.micro.dict.dto.DictionaryDTO;
import com.micro.dict.model.Dictionary;
import com.micro.dict.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = {"数据字典"})
@RestController
@RequestMapping(value = "dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private DictionaryConvertor dictionaryConvertor;

    @ApiOperation(value = "条件查询")
    @GetMapping(value = "conditionSearch")
    public ResultListDTO<DictionaryDTO> conditionSearch(SearchData searchData) {
        List<Dictionary> dictionaries = dictionaryService.conditionSearch(searchData);
        return ResultListDTO.success("条件查询数据字典成功！", dictionaryConvertor.toListDTO(dictionaries));
    }

    @ApiOperation(value = "分页条件查询")
    @GetMapping(value = "pageableSearch")
    public ResultPageDTO<DictionaryDTO> pageableSearch(SearchData searchData, Pageable pageable) {
        Page<Dictionary> dictionaries = dictionaryService.pageableSearch(searchData, pageable);
        Page<DictionaryDTO> dictionaryDTOS = dictionaryConvertor.toPageDTO(dictionaries);
        return ResultPageDTO.success("分页条件查询成功！", dictionaryDTOS);
    }

    @ApiOperation(value = "新增或修改")
    @PostMapping(value = "save")
    public ResultDTO<Void> save(@RequestBody DictionaryDTO dto) {
        dictionaryService.save(dictionaryConvertor.toModel(dto));
        return ResultDTO.success("保存成功！");
    }

    @ApiOperation(value = "更改启用状态")
    @PutMapping(value = "modifyEnabled")
    public ResultDTO<Void> modifyEnabled(@RequestBody DictionaryDTO dto) {
        dictionaryService.modifyEnabled(dictionaryConvertor.toModel(dto));
        return ResultDTO.success("修改启用状态成功");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "batchDelete")
    public ResultDTO<Void> batchDelete(@RequestParam(value = "idArr") Long[] idArr) {
        dictionaryService.batchDelete(idArr);
        return ResultDTO.success("删除成功！");
    }
}
