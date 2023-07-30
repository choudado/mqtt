package com.dado;

import org.fusesource.mqtt.client.*;

import java.util.concurrent.TimeUnit;

public class Sub {
  static final String TOPIC_NAME = "hello/world";
  static final String IP = "59.115.210.66";
  static final int PORT = 1883;

  public static void main(String[] args) throws Exception {
    MQTT mqtt = new MQTT();
    mqtt.setHost(IP, PORT); // 設定ip和port

    BlockingConnection connection = mqtt.blockingConnection();
    connection.connect(); // 連接Broker
    System.out.println("Connected to Broker!");

    // 設置Topic，傳送品質為EXACTLY_ONCE
    Topic[] topics = {new Topic(TOPIC_NAME, QoS.EXACTLY_ONCE)};
    connection.subscribe(topics);

    while (true) {
      // 取得訊息
      Message message = connection.receive(10, TimeUnit.SECONDS);
      if (message != null) {
        System.out.println("Received messages. " + new String(message.getPayload()));
        message.ack(); // 返回ack，告知Broker收到訊息
      }
    }
  }
}
