package com.stackroute.recommendation.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "borrower-service")
public interface BorrowerDetailsProxy {
    @GetMapping("/api/v1/borrower/borrower/{id}")
    Map<Object,Object> getBorrowerData(@PathVariable String id);
}
