package com.micro.user.repository;

import com.micro.user.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByOrderBySort();

    List<Menu> findAllByIdInOrderBySort(List<Long> menuIds);
}
