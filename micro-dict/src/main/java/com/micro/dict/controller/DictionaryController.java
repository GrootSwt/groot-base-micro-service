package com.micro.dict.controller;

import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.base.common.dto.dict.DictionaryDTO;
import com.micro.dict.convertor.DictionaryConvertor;
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
    public ResultData conditionSearch(SearchData searchData) {
        List<Dictionary> dictionaries = dictionaryService.conditionSearch(searchData);
        return ResultData.success("条件查询数据字典成功！", dictionaryConvertor.toListDTO(dictionaries));
    }

    @ApiOperation(value = "分页条件查询")
    @GetMapping(value = "pageableSearch")
    public ResultData pageableSearch(SearchData searchData, Pageable pageable) {
        Page<Dictionary> dictionaries = dictionaryService.pageableSearch(searchData, pageable);
        Page<DictionaryDTO> dictionaryDTOS = dictionaryConvertor.toPageDTO(dictionaries);
        return ResultData.success("分页条件查询成功！", dictionaryDTOS);
    }

    @ApiOperation(value = "新增或修改")
    @PostMapping(value = "save")
    public ResultData save(@RequestBody DictionaryDTO dto) {
        dictionaryService.save(dictionaryConvertor.toModel(dto));
        return ResultData.success("保存成功！");
    }

    @ApiOperation(value = "更改启用状态")
    @PutMapping(value = "modifyEnabled")
    public ResultData modifyEnabled(@RequestBody DictionaryDTO dto) {
        dictionaryService.modifyEnabled(dictionaryConvertor.toModel(dto));
        return ResultData.success("修改启用状态成功");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "batchDelete")
    public ResultData batchDelete(@RequestParam(value = "idArr") Long[] idArr) {
        dictionaryService.batchDelete(idArr);
        return ResultData.success("删除成功！");
    }
}
