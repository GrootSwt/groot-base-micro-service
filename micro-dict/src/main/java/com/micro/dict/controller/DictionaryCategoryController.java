package com.micro.dict.controller;

import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.base.common.dto.dict.DictionaryCategoryDTO;
import com.micro.dict.convertor.DictionaryCategoryConvertor;
import com.micro.dict.model.DictionaryCategory;
import com.micro.dict.service.DictionaryCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = {"数据字典类别"})
@RestController
@RequestMapping(value = "dictionaryCategory")
public class DictionaryCategoryController {

    @Resource
    private DictionaryCategoryService dictionaryCategoryService;
    @Resource
    private DictionaryCategoryConvertor dictionaryCategoryConvertor;

    @ApiOperation(value = "数据字典类别新增和修改")
    @PostMapping(value = "save")
    public ResultData save(@RequestBody DictionaryCategoryDTO dto) {
        DictionaryCategory model = dictionaryCategoryConvertor.toModel(dto);
        dictionaryCategoryService.save(model);
        return ResultData.success("数据字典类别保存成功！");
    }

    @ApiOperation(value = "分页条件查询")
    @GetMapping(value = "pageableSearch")
    public ResultData pageableSearch(SearchData searchData, Pageable pageable) {
        Page<DictionaryCategory> dictionaryCategories = dictionaryCategoryService.pageableSearch(searchData, pageable);
        Page<DictionaryCategoryDTO> dictionaryCategoryDTOS = dictionaryCategoryConvertor.toPageDTO(dictionaryCategories);
        return ResultData.success("分页条件查询成功！", dictionaryCategoryDTOS);
    }

    @ApiOperation(value = "更改启用状态")
    @PutMapping(value = "modifyEnabled")
    public ResultData modifyEnabled(@RequestBody DictionaryCategoryDTO dto) {
        DictionaryCategory model = dictionaryCategoryConvertor.toModel(dto);
        dictionaryCategoryService.modifyEnabled(model);
        return ResultData.success("数据字典类别更改启用状态成功！");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "batchDelete")
    public ResultData batchDelete(Long[] idArr) {
        dictionaryCategoryService.batchDelete(idArr);
        return ResultData.success("删除操作成功！");
    }
}
