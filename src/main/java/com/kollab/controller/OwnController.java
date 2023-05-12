package com.kollab.controller;

import com.kollab.dto.ItemDto;
import com.kollab.repository.ItemRepository;
import com.kollab.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OwnController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/api/item/create")
    public ResponseEntity<?> createItem(@RequestBody @Valid ItemDto item){
        System.out.println("in own controller - api/item/create");
        itemService.saveItem(item);
        return new ResponseEntity<>("Item created", HttpStatus.OK);
    }
}
