package com.dado;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pub {

  static final String TOPIC_NAME = "hello/world";
  static final String IP = "59.115.210.66";
  static final int PORT = 1883;
  static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

  public static void main(String[] args) throws Exception {
    MQTT mqtt = new MQTT();
    mqtt.setHost(IP, PORT); // 設定ip和port
    BlockingConnection connection = mqtt.blockingConnection();
    connection.connect(); // 連接Broker
    System.out.println("Connected to Broker!");

    while (true) {
      String value = createRandom();
      // 發佈訊息，TOPIC為"temperature/Wuling"，設置傳送品質為AT_LEAST_ONCE，不保留訊息
      connection.publish(TOPIC_NAME, value.getBytes(), QoS.AT_LEAST_ONCE, false);
      System.out.println("Sent messages with temperature=" + value);
      Thread.sleep(1000);
    }
  }

  public static String createRandom() {
    return sdf.format(new Date())
        + ":"
        + new BigDecimal(Math.random() * -10 + 30).setScale(1, RoundingMode.FLOOR).toPlainString();
  }
}
