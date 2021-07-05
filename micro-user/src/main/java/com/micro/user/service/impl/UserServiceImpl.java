package com.micro.user.service.impl;

import com.micro.common.util.JwtTokenUtil;
import com.micro.common.util.ResultUtil;
import com.micro.user.convertor.UserConvertor;
import com.micro.user.model.Menu;
import com.micro.user.model.Role;
import com.micro.user.model.User;
import com.micro.user.repository.RoleRepository;
import com.micro.user.repository.UserRepository;
import com.micro.user.service.MenuService;
import com.micro.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

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
        String token = JwtTokenUtil.generatorToken(userConvertor.toDto(registerUser), 60 * 60 * 2);
        // 获取菜单列表
        List<Menu> mapMenus = menuService.getMapMenusByRoleId(registerUser.getRoleId());
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
}
