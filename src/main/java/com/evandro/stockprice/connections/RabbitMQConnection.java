package com.evandro.stockprice.connections;

import constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConnection {
    private static final String EXCHANGE_NAME = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding relationship(Queue queue, DirectExchange exchange){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add(){
        Queue queueStock = this.queue(RabbitMQConstants.QUEUE_STOCK);
        Queue queuePrice = this.queue(RabbitMQConstants.QUEUE_PRICE);

        DirectExchange exchange = this.directExchange();

        Binding linkStock = this.relationship(queueStock, exchange);
        Binding linkPrice = this.relationship(queuePrice, exchange);

        //Create queues on Rabbitmq.
        this.amqpAdmin.declareQueue(queueStock);
        this.amqpAdmin.declareQueue(queuePrice);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(linkStock);
        this.amqpAdmin.declareBinding(linkPrice);
    }
}
