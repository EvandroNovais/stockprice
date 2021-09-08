package com.evandro.stockprice.controllers;

import com.evandro.stockprice.services.RabbitmqService;
import constants.RabbitMQConstants;
import dtos.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "stock")
public class StockController {

    @Autowired
    private RabbitmqService rabbitmqService;

    @PutMapping
    private ResponseEntity updateStock(@RequestBody StockDto stockDto){
        System.out.println(stockDto.idProduct);
        this.rabbitmqService.sendMessage(RabbitMQConstants.QUEUE_STOCK, stockDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
