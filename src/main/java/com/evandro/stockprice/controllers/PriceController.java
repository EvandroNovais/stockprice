package com.evandro.stockprice.controllers;


import com.evandro.stockprice.services.RabbitmqService;
import constants.RabbitMQConstants;
import dtos.PriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "price")
public class PriceController {

    @Autowired
    private RabbitmqService rabbitmqService;

    @PutMapping
    private ResponseEntity updatePrice(@RequestBody PriceDto priceDto){
        System.out.println(priceDto.idProduct);
        this.rabbitmqService.sendMessage(RabbitMQConstants.QUEUE_PRICE, priceDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
