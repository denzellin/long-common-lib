package com.isylph.mqtt.transceiver;

import com.isylph.mqtt.manage.config.MqttConfigManager;
import com.isylph.utils.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@EnableIntegrationManagement(defaultLoggingEnabled = "${mqtt.logging: false}")
@Configuration
public class MqttInitiator {
    private static final byte[] WILL_DATA;

    static {
        WILL_DATA = "offline".getBytes();
    }

    private MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter;
    /*
     * 订阅的bean名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";
    /*
     * 发布的bean名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";

    @Value("${mqtt.url}")
    private String url;

    @Value("${mqtt.username}")
    private String userName;

    @Value("${mqtt.password}")
    private String password;


    @Value("${mqtt.producer.client-id}")
    private String producerClientId;

    @Value("${mqtt.producer.default-topic}")
    private String producerDefaultTopic;


    @Value("${mqtt.consumer.client-id}")
    private String consumerClientId;

    @Value("${mqtt.consumer.default-topic:}")
    private String[] consumerDefaultTopic;


    @Autowired
    private MqttMsgDispatcher mqttMsgDispatcher;


    /*
     * MQTT连接器选项
     *
     * @return {@link org.eclipse.paho.client.mqttv3.MqttConnectOptions}
*/

    private MqttConnectOptions getMqttConnectOptions(String userName, String password) {
        MqttConnectOptions options = new MqttConnectOptions();

        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);

        // 设置连接的用户名
        options.setUserName(userName);

        // 设置连接的密码
        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[]{url});

        // 设置超时时间 单位为秒
        options.setConnectionTimeout(1000000);

        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);

        // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
        options.setWill("willTopic", WILL_DATA, 2, false);

        return options;
    }

    @Bean
    public MqttConnectOptions getMqttConnectOptions(){
        return getMqttConnectOptions(userName, password);
    }


    /*
     * MQTT客户端
     *
     * @return {@link org.springframework.integration.mqtt.core.MqttPahoClientFactory}
    */
    @Bean
    public MqttPahoClientFactory mqttProducerClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    @Bean
    public MqttPahoClientFactory mqttConsumerClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /*
     * MQTT信息通道（生产者）
     *
     * @return {@link org.springframework.messaging.MessageChannel}
    */
    @Bean(name = CHANNEL_NAME_OUT)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /*
     * MQTT消息处理器（生产者）
     *
     * @return {@link org.springframework.messaging.MessageHandler}
    */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                producerClientId,
                mqttProducerClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(producerDefaultTopic);
        return messageHandler;
    }

    /*
     * MQTT消息订阅绑定（消费者）
     *
     * @return {@link org.springframework.integration.core.MessageProducer}
    */
    @Bean(name = MqttConfigManager.mqttInboundBean)
    public MessageProducer inbound() {
        // 可以同时消费（订阅）多个Topic

        if(StringUtils.isEmpty(consumerDefaultTopic)){
            consumerDefaultTopic = null;
        }

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        consumerClientId,
                        mqttConsumerClientFactory(),
                        consumerDefaultTopic);
        adapter.setCompletionTimeout(5000);
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);
        adapter.setQos(1);
        // 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        this.mqttInboundAdapter = adapter;
        return adapter;
    }

    public void addTopic(String topic){
        mqttInboundAdapter.addTopic(topic);
    }

    public void removeTopic(String topic){
        mqttInboundAdapter.removeTopic(topic);
    }
    public String[] getTopic(){
        return mqttInboundAdapter.getTopic();
    }
    /*
     * MQTT信息通道（消费者）
     *
     * @return {@link org.springframework.messaging.MessageChannel}
*/
    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }
/*
    *
     * MQTT消息处理器（消费者）
     *
     * @return {@link org.springframework.messaging.MessageHandler}
*/
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return message -> {
            mqttMsgDispatcher.dispatch(message);
        };
    }
}
