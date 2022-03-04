package com.groot.base.micro.system.service.impl;


import com.groot.base.common.SearchData;
import com.groot.base.micro.system.convertor.UserConvertor;
import com.groot.base.micro.system.repository.RoleRepository;
import com.groot.base.micro.system.repository.UserRepository;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.bean.result.ResultPageDTO;
import com.groot.base.web.exception.BusinessRuntimeException;
import com.groot.base.micro.system.bean.ChangePasswordBean;
import com.groot.base.micro.system.bean.LoginBean;
import com.groot.base.micro.system.dto.UserDTO;
import com.groot.base.micro.system.model.Role;
import com.groot.base.micro.system.model.User;
import com.groot.base.micro.system.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Value(value = "${groot.login.expire-time}")
    private Integer expireTime;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
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
    public LoginBean login(User user, HttpServletResponse response) {
        // 检查登录人是否注册
        User registerUser = userRepository.findFirstByLoginNameAndEnabled(user.getLoginName(), "1");
        if (registerUser == null) {
            throw new BusinessRuntimeException("用户没有注册或账号未启用!");
        }
        // 判断账号密码是否正确
        if (!registerUser.getLoginName().equals(user.getLoginName()) || !registerUser.getPassword().equals(user.getPassword())) {
            throw new BusinessRuntimeException("账号或密码不正确！");
        }
        LoginBean bean = new LoginBean();
        this.generateToken(registerUser, bean);
        return bean;
    }

    private void generateToken(User registerUser, LoginBean bean) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        stringRedisTemplate.opsForValue().set(token, registerUser.getLoginName(), expireTime, TimeUnit.MINUTES);
        bean.setToken(token);
        bean.setAccountName(registerUser.getLoginName());
        bean.setUserInfo(registerUser);
    }

    @Override
    public ResultPageDTO<UserDTO> pageableSearch(Pageable pageable, SearchData searchData) {
        // 根据查询条件中的角色名查询角色Id
        if (searchData.hasKey("roleName")) {
            List<String> roleIds = roleRepository.findRoleIdsByRoleName(searchData.getStringValue("roleName"));
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
        return ResultPageDTO.success("分页条件查询用户信息成功！", userDTOPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(String[] idArr) {
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
    public void changePassword(ChangePasswordBean changePasswordBean) {
        User user = userRepository.findFirstById(changePasswordBean.getId());
        if (!user.getPassword().equals(changePasswordBean.getOldPassword())) {
            throw new BusinessRuntimeException("原密码错误！");
        }
        userRepository.changePassword(changePasswordBean);
    }

    @Override
    public ResultDTO<Void> authorization(User toModel) {
        User user = userRepository.findFirstById(toModel.getId());
        if (user == null) {
            return ResultDTO.failure("用户不存在！");
        }
        user.setRoleId(toModel.getRoleId());
        userRepository.save(user);
        return ResultDTO.success("用户授权成功！");
    }

    @Override
    public User modifyUserInfo(User toModel) {
        User user = userRepository.findFirstById(toModel.getId());
        if (user == null) {
            throw new BusinessRuntimeException("用户不存在！");
        }
        user.setUsername(toModel.getUsername());
        user.setPhoneNumber(toModel.getPhoneNumber());
        user.setEmail(toModel.getEmail());
        User result = userRepository.save(user);
        user.setPassword(null);
        return result;
    }

    @Override
    public User modifyAvatar(User toModel) {
        User user = userRepository.findFirstById(toModel.getId());
        if (user == null) {
            throw new BusinessRuntimeException("用户不存在！");
        }
        user.setAvatar(toModel.getAvatar());
        User result = userRepository.save(user);
        user.setPassword(null);
        return result;
    }

    @Override
    public void logout(String token) {
        // 删除登录用户信息
        stringRedisTemplate.delete(token);
    }

    @Override
    public boolean loginNameIsExist(String loginName) {
        return userRepository.findFirstByLoginName(loginName) != null;
    }

    @Override
    public boolean phoneNumberIsExist(String phoneNumber) {
        return userRepository.findFirstByPhoneNumber(phoneNumber) != null;
    }

    @Override
    public boolean emailIsExist(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    /**
     * 根据角色Id和角色列表获取角色名
     *
     * @param id       角色Id
     * @param roleList 角色列表
     * @return 角色名
     */
    private String getRoleNameById(String id, List<Role> roleList) {
        Optional<Role> roleOptional = roleList.stream().filter(role -> role.getId().equals(id)).findFirst();
        if (roleOptional.isPresent()) {
            return roleOptional.get().getName();
        } else {
            return "";
        }
    }
}
