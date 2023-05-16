package com.kollab.controller.list;

import com.kollab.dto.list.ListDeleteDto;
import com.kollab.dto.list.ListDto;
import com.kollab.dto.item.ItemDeleteDto;
import com.kollab.dto.list.ListUpdateDto;
import com.kollab.service.ListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListController {
    @Autowired
    private ListService listService;

    @PostMapping("/api/list/create")
    public ResponseEntity<?> createList(@RequestBody @Valid ListDto listDto){
        System.out.println("in list controller /api/list/create");
        listService.saveList(listDto);
        return new ResponseEntity<>("List created", HttpStatus.OK);
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

    @DeleteMapping("/api/list/delete")
    public ResponseEntity<?> deleteItem(@RequestBody @Valid ListDeleteDto listDeleteDto){
        try {
            System.out.println("in item controller - api/list/delete");
            listService.deleteList(listDeleteDto);
            return new ResponseEntity<>("List deleted", HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("Error deleting list", HttpStatus.BAD_REQUEST);
        }
    }
}
