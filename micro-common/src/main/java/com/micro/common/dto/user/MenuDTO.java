package com.micro.common.dto.user;

import java.io.Serializable;
import java.util.List;

public class MenuDTO implements Serializable {

    private Long id;

    private String title;

    private String location;

    private String icon;

    private String sort;

    private String enabled;

    private Long pId;

    private List<MenuDTO> children;
}
