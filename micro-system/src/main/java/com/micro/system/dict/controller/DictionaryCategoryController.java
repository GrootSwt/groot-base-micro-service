package com.micro.system.dict.controller;

import com.groot.base.common.SearchData;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.bean.result.ResultPageDTO;
import com.micro.system.dict.convertor.DictionaryCategoryConvertor;
import com.micro.system.dict.dto.DictionaryCategoryDTO;
import com.micro.system.dict.model.DictionaryCategory;
import com.micro.system.dict.service.DictionaryCategoryService;
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
    public ResultDTO<Void> save(@RequestBody DictionaryCategoryDTO dto) {
        DictionaryCategory model = dictionaryCategoryConvertor.toModel(dto);
        dictionaryCategoryService.save(model);
        return ResultDTO.success("数据字典类别保存成功！");
    }

    @ApiOperation(value = "分页条件查询")
    @GetMapping(value = "pageableSearch")
    public ResultPageDTO<DictionaryCategoryDTO> pageableSearch(SearchData searchData, Pageable pageable) {
        Page<DictionaryCategory> dictionaryCategories = dictionaryCategoryService.pageableSearch(searchData, pageable);
        Page<DictionaryCategoryDTO> dictionaryCategoryDTOS = dictionaryCategoryConvertor.toPageDTO(dictionaryCategories);
        return ResultPageDTO.success("数据字典类别分页条件查询成功！", dictionaryCategoryDTOS);
    }

    @ApiOperation(value = "更改启用状态")
    @PutMapping(value = "modifyEnabled")
    public ResultDTO<Void> modifyEnabled(@RequestBody DictionaryCategoryDTO dto) {
        DictionaryCategory model = dictionaryCategoryConvertor.toModel(dto);
        dictionaryCategoryService.modifyEnabled(model);
        return ResultDTO.success("数据字典类别更改启用状态成功！");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "batchDelete")
    public ResultDTO<Void> batchDelete(@RequestParam Long[] idArr) {
        dictionaryCategoryService.batchDelete(idArr);
        return ResultDTO.success("删除操作成功！");
    }
}
