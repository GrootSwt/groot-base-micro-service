package com.micro.base.web.util;

import com.micro.base.common.dto.user.UserDTO;
import com.micro.base.common.bean.OperatorInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUserInfoUtil {

    private static OperatorInfo operatorInfo;

    /**
     * 获取当前操作人员信息
     *
     * @return 操作人员信息
     */
    public static OperatorInfo getOperatorInfo() {
        if (operatorInfo == null) {
            operatorInfo = new OperatorInfo();
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            assert servletRequestAttributes != null;
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("userInfo");
            userDTOToOperatorInfo(userDTO);
        }

        return operatorInfo;
    }

    /**
     * 存放当前登录人员信息
     *
     * @param userDTO 登录人员信息
     */
    public static void setOperatorInfo(UserDTO userDTO) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userDTO);
        operatorInfo = null;
    }

    /**
     * 用户信息转为操作员信息
     *
     * @param userDTO 登录用户信息
     * @return 操作员信息
     */
    private static void userDTOToOperatorInfo(UserDTO userDTO) {
        if (userDTO != null) {
            BeanUtils.copyProperties(userDTO, operatorInfo);
        }
    }
}