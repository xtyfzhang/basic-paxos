package com.basicpaxos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 服务配置信息
 */
@ConfigurationProperties(prefix = "paxos")
@Component
@Data
public class ConfigInfo {

    String serviceIp;
}
