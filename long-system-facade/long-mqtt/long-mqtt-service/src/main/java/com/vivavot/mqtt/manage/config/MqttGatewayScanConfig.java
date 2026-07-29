package com.vivavot.mqtt.manage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

@Configuration
@IntegrationComponentScan(basePackages = {"com.vivavot.mqtt.transceiver"})
public class MqttGatewayScanConfig {
}
