package com.stackroute.loan.configuartion;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue queue(){
        return new Queue("loan-notification");
    }
    @Bean
    public Queue recommendationQueue(){
        return new Queue("recommendation-queue");
    }
    @Bean
    public Exchange exchange(){
        return new DirectExchange("loan-notification-exchange");
    }
    @Bean
    public Exchange recommendationExchange(){
        return new DirectExchange("recommendation-exchange");
    }
    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
    @Bean
    public Binding bindingExchangeAndQueue(Queue queue, Exchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with("route-key").noargs();
    }
    @Bean
    public Binding bindingExchangeAndQueue2(Queue recommendationQueue, Exchange recommendationExchange)
    {
        return BindingBuilder.bind(recommendationQueue).to(recommendationExchange).with("route-key").noargs();
    }

    @Bean
    public Queue loanApprovalQueue(){
        return new Queue("loan-approval");
    }
    @Bean
    public Exchange loanApprovalExchange(){
        return new DirectExchange("loan-approval-exchange");
    }
    @Bean
    public Binding bindingExchangeAndQueue3(Queue loanApprovalQueue, Exchange loanApprovalExchange)
    {
        return BindingBuilder.bind(loanApprovalQueue).to(loanApprovalExchange).with("route-key").noargs();
    }

    @Bean
    public Queue emiPaymentQueue(){
        return new Queue("pay-emi");
    }
    @Bean
    public Exchange emiPaymentExchange(){
        return new DirectExchange("pay-emi-exchange");
    }
    @Bean
    public Binding bindingExchangeAndQueue4(Queue emiPaymentQueue, Exchange emiPaymentExchange)
    {
        return BindingBuilder.bind(emiPaymentQueue).to(emiPaymentExchange).with("route-key").noargs();
    }

}
