package com.micro.base.common.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * yaml工具类
 */
public class YamlUtil {
    /**
     * 默认路径
     */
    private static final String DEFAULT_PATH = "config/application.yml";

    /**
     * 根据路径获取yaml中的数据
     *
     * @param path 文件路径
     * @return yaml中的数据
     */
    public static Map<String, Object> getYamlByPath(String path) {
        Map<String, Object> result;
        InputStream resourceAsStream;
        if (path != null && !path.equals("")) {
            resourceAsStream = YamlUtil.class.getClassLoader().getResourceAsStream(path);
        } else {
            resourceAsStream = YamlUtil.class.getClassLoader().getResourceAsStream(DEFAULT_PATH);
        }
        Yaml yaml = new Yaml();
        result = yaml.load(resourceAsStream);
        return result;
    }
}
