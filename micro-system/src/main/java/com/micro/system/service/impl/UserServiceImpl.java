package com.micro.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.base.common.dto.system.UserDTO;
import com.micro.base.common.util.JwtTokenUtil;
import com.micro.base.web.exception.BusinessRuntimeException;
import com.micro.base.web.util.LoginUserInfoUtil;
import com.micro.system.bean.ChangePasswordBean;
import com.micro.system.convertor.UserConvertor;
import com.micro.system.model.Role;
import com.micro.system.model.User;
import com.micro.system.repository.RoleRepository;
import com.micro.system.repository.UserRepository;
import com.micro.system.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Value(value = "${micro.jwt.expireTime}")
    private Integer expireTime;

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserConvertor userConvertor;
    @Resource
    private RoleRepository roleRepository;

    public User getUserByLoginName(String loginName) {
        return userRepository.findFirstByLoginNameAndEnabled(loginName, "1");
    }

    @Override
    public User getUserByLoginNameAndPassword(String loginName, String password) {
        return userRepository.findFirstByLoginNameAndPassword(loginName, password);
    }

    @Override
    public ResultData validateLoginInfoAndGenerateToken(User user, HttpServletResponse response) {
        // 检查登录人是否注册
        User registerUser = userRepository.findFirstByLoginNameAndEnabled(user.getLoginName(), "1");
        if (registerUser == null) {
            return ResultData.failure("用户没有注册或账号未启用!");
        }
        // 判断账号密码是否正确
        if (!registerUser.getLoginName().equals(user.getLoginName()) || !registerUser.getPassword().equals(user.getPassword())) {
            return ResultData.failure("账号或密码不正确！");
        }
        // 获取token
        registerUser.setPassword(null);
        String token = JwtTokenUtil.generatorToken(userConvertor.toDTO(registerUser), expireTime);

        // 在session中存放用户信息
        LoginUserInfoUtil.setOperatorInfo(userConvertor.toDTO(registerUser));
        // 返回token和登录用户信息
        Map<String, Object> data = new HashMap<>(16);
        data.put("userInfo", registerUser);
        data.put("token", token);
        Cookie tokenCookie = new Cookie("token", token);
        Cookie userInfoCookie;
        try {
            userInfoCookie = new Cookie("userInfo", URLEncoder.encode(JSON.toJSONString(registerUser), "UTF-8"));
        }catch (Exception e) {
            throw new BusinessRuntimeException("向cookie中添加用户信息失败！");
        }
        response.addCookie(tokenCookie);
        response.addCookie(userInfoCookie);
        return ResultData.success("登录成功！", data);
    }

    @Override
    public ResultData pageableSearch(Pageable pageable, SearchData searchData) {
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
        return ResultData.success("分页条件查询用户信息成功！", userDTOPage);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeUserEnabled(User toModel) {
        userRepository.changeUserEnable(toModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData changePassword(ChangePasswordBean changePasswordBean) {
        User user = userRepository.findFirstById(changePasswordBean.getId());
        if (!user.getPassword().equals(changePasswordBean.getOldPassword())) {
            return ResultData.failure("原密码输入错误");
        }
        userRepository.changePassword(changePasswordBean);
        return ResultData.success("修改密码成功！");
    }

    @Override
    public ResultData authorization(User toModel) {
        User user = userRepository.findFirstById(toModel.getId());
        if (user == null) {
            return ResultData.failure("用户不存在！");
        }
        user.setRoleId(toModel.getRoleId());
        userRepository.save(user);
        return ResultData.success("用户授权成功！");
    }

    @Override
    public ResultData modifyUserInfo(User toModel) {
        User user = userRepository.findFirstById(toModel.getId());
        if (user == null) {
            return ResultData.failure("用户不存在！");
        }
        user.setUsername(toModel.getUsername());
        user.setPhoneNumber(toModel.getPhoneNumber());
        user.setEmail(toModel.getEmail());
        User result = userRepository.save(user);
        user.setPassword(null);
        return ResultData.success("更改用户信息成功！", result);
    }

    @Override
    public ResultData modifyAvatar(User toModel) {
        User user = userRepository.findFirstById(toModel.getId());
        if (user == null) {
            return ResultData.failure("用户不存在！");
        }
        user.setAvatar(toModel.getAvatar());
        User result = userRepository.save(user);
        user.setPassword(null);
        return ResultData.success("设置头像成功！", userConvertor.toDTO(result));
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
