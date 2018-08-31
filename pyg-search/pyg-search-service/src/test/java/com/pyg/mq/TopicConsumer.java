package com.pyg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class TopicConsumer {


    /**
     * 需求：发送消息
     * 模式：发布订阅模式
     */
    @Test
    public void receiveMessage() throws Exception {
        //指定消息服务器发送地址
        String brokerURL = "tcp://193.112.23.174:61616";
        //创建消息连接工厂对象
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
        //从工厂获取连接对象
        Connection connection = cf.createConnection();

        //开启连接
        connection.start();

        //从连接中获取消息回话对象session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建消息存储空间，且给这个空间起一个名称
        Topic myTopic = session.createTopic("myTopic");

        //指定消息接收者，且指定从哪儿接受消息
        MessageConsumer consumer = session.createConsumer(myTopic);

        //监听接收
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                if(message instanceof TextMessage){
                    try {
                        TextMessage m = (TextMessage) message;
                        //获取消息
                        String text = m.getText();
                        System.out.println("接收消息："+text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //等待输入，让端口阻塞，服务一直开启
        System.in.read();

        consumer.close();
        session.close();
        connection.close();

    }

}
