package com.pyg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

public class TopicProducer {

    /**
     * 需求：发送消息
     * 模式：订阅模式
     */
    @Test
    public void sendMessage() throws Exception {
        //指定消息服务器发送地址
        String brokerURL = "tcp://193.112.23.174:61616";

        //创建消息连接工厂对象
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
        //从工厂中获取连接对象
        Connection connection = cf.createConnection();

        //开启连接
        connection.start();

        //从连接中获取消息回话对象
        //参数1:
        // true 表示使用的是消息事物确认提交模式
        // false 消息的自动确认模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建消息存储空间，且给这个空间起一个名称
        Topic myTopic = session.createTopic("myTopic");

        //创建消息发送者，且告知消息往哪儿发送
        //点对点：queue
        //发布订阅：topic
        //发布订阅和点对点根本却别在于发生消息数据结构不一样
        MessageProducer producer = session.createProducer(myTopic);

        //创建消息对象
        TextMessage message = new ActiveMQTextMessage();
        message.setText("齐天大圣孙悟空，修炼了八九玄功，火眼金睛");

        //发送消息
        producer.send(message);

        producer.close();
        session.close();
        connection.close();
    }
}
