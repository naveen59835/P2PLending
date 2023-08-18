package com.stackroute.payment.controller.config;

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
        return new Queue("loan-approval");
    }
    @Bean
    public Exchange exchange(){
        return new DirectExchange("loan-approval-exchange");
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
    public Queue approvalNotificationQueue(){
        return new Queue("loan-approval-notification");
    }
    @Bean
    public Exchange approvalNotificationExchange(){
        return new DirectExchange("loan-approval-notification-exchange");
    }
    @Bean
    public Binding bindingExchangeAndQueue2(Queue approvalNotificationQueue, Exchange approvalNotificationExchange)
    {
        return BindingBuilder.bind(approvalNotificationQueue).to(approvalNotificationExchange).with("route-key").noargs();
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
    public Binding bindingExchangeAndQueue3(Queue emiPaymentQueue, Exchange emiPaymentExchange)
    {
        return BindingBuilder.bind(emiPaymentQueue).to(emiPaymentExchange).with("route-key").noargs();
    }
    @Bean
    public Queue emiPaymentNotificationQueue(){
        return new Queue("pay-emi-notification");
    }
    @Bean
    public Exchange  emiPaymentNotificationExchange(){
        return new DirectExchange("pay-emi-notification-exchange");
    }
    @Bean
    public Binding bindingExchangeAndQueue4(Queue emiPaymentNotificationQueue, Exchange emiPaymentNotificationExchange)
    {
        return BindingBuilder.bind(emiPaymentNotificationQueue).to(emiPaymentNotificationExchange).with("route-key").noargs();
    }
    @Bean
    public Queue loanApprovalRecommendationQueue(){
        return new Queue("loan-approval-recommendation");
    }
    @Bean
    public Exchange  loanApprovalRecommendationExchange(){
        return new DirectExchange("loan-approval-recommendation-exchange");
    }
    @Bean
    public Binding bindingExchangeAndQueue5(Queue loanApprovalRecommendationQueue, Exchange loanApprovalRecommendationExchange)
    {
        return BindingBuilder.bind(loanApprovalRecommendationQueue).to(loanApprovalRecommendationExchange).with("route-key").noargs();
    }
}
