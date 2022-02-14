package com.micro.system.user.repository;


import com.micro.system.user.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 菜单Repository
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {

    /**
     * 排序查询所有菜单
     *
     * @return 菜单列表
     */
    List<Menu> findAllByOrderBySort();

    /**
     * 排序查询所有菜单
     *
     * @return 菜单列表
     */
    List<Menu> findAllByTypeOrderBySort(String type);

    /**
     * 查询所有id在menuIds中的菜单列表并排序
     *
     * @param menuIds 菜单Id列表
     * @return 菜单列表
     */
    List<Menu> findAllByIdInAndEnabledAndTypeOrderBySort(List<Long> menuIds, String enabled, String type);

    /**
     * 根据菜单Id获取菜单
     *
     * @param menuId 菜单Id
     * @return 菜单
     */
    Menu findFirstById(Long menuId);

    /**
     * 获取全部启用菜单
     *
     * @param enabled 启用
     * @return 全部启用菜单
     */
    List<Menu> findAllByEnabledOrderBySort(String enabled);

    /**
     * 根据路径、类型查重
     *
     * @param location 路径
     * @param type     类型
     * @return 是否存在
     */
    Long countAllByLocationAndType(String location, String type);

    /**
     * 根据父菜单id获取所有子菜单
     *
     * @param parentId 父菜单id
     * @return 子菜单列表
     */
    List<Menu> findAllByParentIdOrderBySort(Long parentId);

    Menu findFirstByLocation(String location);
}
