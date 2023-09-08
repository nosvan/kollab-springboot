package com.kollab.controller.item;

import com.kollab.dto.item.ItemDeleteDto;
import com.kollab.dto.item.ItemDto;
import com.kollab.dto.item.ItemPermissionDto;
import com.kollab.dto.item.ItemUpdateDto;
import com.kollab.entity.item.Item;
import com.kollab.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/api/item/get-all-own")
    public ResponseEntity<?> getAllItems() throws Exception {
        try {
            System.out.println("in own controller - api/item/getallown");
            List<Item> items = itemService.getItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Error getting items", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/item/get")
    public ResponseEntity<?> getItem(@RequestParam String itemId) throws Exception {
        try {
            System.out.println("in own controller - api/item/get");
            Item item = itemService.getItem(itemId);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Error getting items", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/item/get-users")
    public ResponseEntity<?> getItemUsers(@RequestParam String itemId) throws Exception {
        try {
            System.out.println("in own controller - api/item/get-users");
            Item item = itemService.getItem(itemId);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Error getting items", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/item/get-permissions")
    public ResponseEntity<?> getItemPermissions(@RequestParam String itemId) throws Exception {
        try {
            System.out.println("in own controller - api/item/get-permissions");
            List<ItemPermissionDto> itemPermissionDtoList = itemService.getItemPermissions(itemId);
            return new ResponseEntity<>(itemPermissionDtoList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Error getting item permissions", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/item/create")
    public ResponseEntity<?> createItem(@RequestBody @Valid ItemDto item){
        System.out.println("in own controller - api/item/create");
        try {
            ItemDto itemToReturn = itemService.createItem(item);
            return new ResponseEntity<>(itemToReturn, HttpStatus.OK) ;
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating item", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/item/update")
    public ResponseEntity<?> updateItem(@RequestBody @Valid ItemUpdateDto item){
        try {
            System.out.println("in item controller - api/item/update");
            ItemDto itemToReturn = itemService.updateItem(item);
            return new ResponseEntity<>(itemToReturn, HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("Error updating item", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/item/delete")
    public ResponseEntity<?> deleteItem(@RequestParam String itemId){
        try {
            System.out.println("in item controller - api/item/delete");
            itemService.deleteItem(itemId);
            return new ResponseEntity<>("Item deleted", HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("Error deleting item", HttpStatus.BAD_REQUEST);
        }
    }
}
