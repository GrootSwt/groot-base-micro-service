package com.micro.user.service.impl;

import com.micro.common.dto.user.MenuDTO;
import com.micro.common.dto.user.UserDTO;
import com.micro.common.util.JwtTokenUtil;
import com.micro.common.util.ResultUtil;
import com.micro.common.util.SearchData;
import com.micro.user.convertor.UserConvertor;
import com.micro.user.model.Role;
import com.micro.user.model.User;
import com.micro.user.repository.RoleRepository;
import com.micro.user.repository.UserRepository;
import com.micro.user.service.MenuService;
import com.micro.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Value(value = "${jwt.expireTime}")
    private Integer expireTime;

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserConvertor userConvertor;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleRepository roleRepository;

    public User getUserByLoginName(String loginName) {
        return userRepository.findFirstByLoginName(loginName);
    }

    @Override
    public User getUserByLoginNameAndPassword(String loginName, String password) {
        return userRepository.findFirstByLoginNameAndPassword(loginName, password);
    }

    @Override
    public ResultUtil validateLoginInfoAndGenerateToken(User user) {
        // 检查登录人是否注册
        User registerUser = userRepository.findFirstByLoginName(user.getLoginName());
        if (registerUser == null) {
            return ResultUtil.failure("用户没有注册!");
        }
        // 判断账号密码是否正确
        if (!registerUser.getLoginName().equals(user.getLoginName()) || !registerUser.getPassword().equals(user.getPassword())) {
            return ResultUtil.failure("账号或密码不正确！");
        }
        // 获取token
        registerUser.setPassword(null);
        String token = JwtTokenUtil.generatorToken(userConvertor.toDTO(registerUser), expireTime);
        // 获取菜单列表
        List<MenuDTO> mapMenus = menuService.getMapMenusByRoleId(registerUser.getRoleId());
        // 获取角色信息
        Role role = roleRepository.findFirstById(registerUser.getRoleId());
        // 返回token和登录用户信息
        Map<String, Object> data = new HashMap<>(16);
        data.put("userInfo", registerUser);
        data.put("token", token);
        data.put("menu", mapMenus);
        data.put("role", role);
        return ResultUtil.success("登录成功！", data);
    }

    @Override
    public ResultUtil pageableSearch(Pageable pageable, SearchData searchData) {
        // 根据查询条件中的角色名查询角色Id
        if (searchData.hasKey("roleName")) {
            List<Long> roleIds = roleRepository.findRoleIdsByRoleName(searchData.getStringValue("roleName"));
            searchData.put("roleIds", roleIds);
        }
        Page<User> userPage = userRepository.pageableSearch(pageable, searchData);
        List<User> userList = userPage.getContent();
        List<UserDTO> userDTOList = userConvertor.toListDTO(userList);
        List<Role> roleList = roleRepository.findAll();
        // 获取所有角色名
        userDTOList.forEach(userDTO -> {
            userDTO.setPassword("");
            userDTO.setRoleName(getRoleNameById(userDTO.getRoleId(), roleList));
        });
        PageImpl<UserDTO> userDTOPage = new PageImpl<>(userDTOList, userPage.getPageable(), userPage.getTotalElements());
        return ResultUtil.success("分页条件查询用户信息成功！", userDTOPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(Long[] idArr) {
        userRepository.deleteByIdIn(Arrays.asList(idArr));
    }

    @Override
    public void addOrEditUser(User user) {
        // 编辑时根据用户id获取用户密码
        if (user.getId() != null) {
            User userModel = userRepository.findFirstById(user.getId());
            user.setPassword(userModel.getPassword());
        }
        userRepository.save(user);
    }

    /**
     * 根据角色Id和角色列表获取角色名
     *
     * @param id       角色Id
     * @param roleList 角色列表
     * @return 角色名
     */
    private String getRoleNameById(Long id, List<Role> roleList) {
        Optional<Role> roleOptional = roleList.stream().filter(role -> role.getId().equals(id)).findFirst();
        if (roleOptional.isPresent()) {
            return roleOptional.get().getName();
        } else {
            return "";
        }
    }
}
