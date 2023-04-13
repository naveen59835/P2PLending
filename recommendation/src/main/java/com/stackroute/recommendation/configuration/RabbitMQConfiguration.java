package com.stackroute.recommendation.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue recommendationQueue(){
        return new Queue("recommendation-queue");
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
    public Binding bindingExchangeAndQueue2(Queue recommendationQueue, Exchange recommendationExchange)
    {
        return BindingBuilder.bind(recommendationQueue).to(recommendationExchange).with("route-key").noargs();
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
