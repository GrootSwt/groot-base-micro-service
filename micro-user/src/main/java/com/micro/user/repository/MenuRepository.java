package com.micro.user.repository;

import com.micro.user.model.Menu;
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
     * 查询所有id在menuIds中的菜单列表并排序
     *
     * @param menuIds 菜单Id列表
     * @return 菜单列表
     */
    List<Menu> findAllByIdInAndEnabledOrderBySort(List<Long> menuIds, String enabled);

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


}
