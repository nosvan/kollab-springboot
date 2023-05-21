package com.kollab.controller.list;

import com.kollab.dto.list.*;
import com.kollab.dto.user.UserDto;
import com.kollab.entity.item.Item;
import com.kollab.entity.list.Category;
import com.kollab.service.ItemService;
import com.kollab.service.ListPermissionService;
import com.kollab.service.ListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListController {
    @Autowired
    private ListService listService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ListPermissionService listPermissionService;

    @GetMapping("/api/list/get-all-own")
    public ResponseEntity<?> getOwnLists(){
        System.out.println("in list controller /api/list/getallown");
        try {
            return new ResponseEntity<>(listService.getOwnLists(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting lists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/list/get-lists")
    public ResponseEntity<?> getLists(){
        System.out.println("in list controller /api/list/get-lists");
        try {
            return new ResponseEntity<>(listService.getLists(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting lists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/list/get-items")
    public ResponseEntity<?> getListItems(@RequestParam Category category, @RequestParam String categoryId){
        System.out.println("in list controller /api/list/get-items");
        try {
            List<Item> items = itemService.getItems(category, categoryId);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting lists items", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/list/get-users")
    public ResponseEntity<?> getListUsers(@RequestParam String listId){
        System.out.println("in list controller /api/list/get-users");
        try {
            List<UserDto> usersDto = listService.getListUsers(Long.valueOf(listId));
            return new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting lists items", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/api/list/join")
    public ResponseEntity<?> joinList(@RequestBody @Valid ListJoinDto listJoinDto) throws Exception {
        System.out.println("in list controller /api/list/join");
        try {
            return new ResponseEntity<>(listPermissionService.joinList(listJoinDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error joining list", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/list/create")
    public ResponseEntity<?> createList(@RequestBody @Valid ListDto listDto){
        System.out.println("in list controller /api/list/create");
        return new ResponseEntity<>(listService.createList(listDto), HttpStatus.OK);
    }

    @PostMapping("/api/list/update")
    public ResponseEntity<?> updateList(@RequestBody @Valid ListUpdateDto listUpdateDto){
        try {
            System.out.println("in list controller /api/list/update");
            listService.updateList(listUpdateDto);
            return new ResponseEntity<>("List updated", HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("Error updating list", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/list/edit-passcode")
    public ResponseEntity<?> updateList(@RequestBody @Valid ListEditPasscodeDto listEditPasscodeDto){
        try {
            System.out.println("in list controller /api/list/edit-passcode");
            listService.editPasscode(listEditPasscodeDto);
            return new ResponseEntity<>("List passcode updated", HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("Error updating list", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/list/delete")
    public ResponseEntity<?> deleteList(@RequestBody @Valid ListDeleteDto listDeleteDto){
        try {
            System.out.println("in list controller - api/list/delete");
            listService.deleteList(listDeleteDto);
            return new ResponseEntity<>("List deleted", HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("Error deleting list", HttpStatus.BAD_REQUEST);
        }
    }
}
