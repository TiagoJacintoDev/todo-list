package com.todolist.list;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/lists")
public class ListController {
    @Autowired
    private ListService listService;

    private final String NOT_FOUND_BODY = "";
    private final String DELETED_BODY = "";

    @PostMapping
    public ResponseEntity<Object> saveList(@RequestBody @Valid ListDto listDto){
        var listModel = new ListModel();
        BeanUtils.copyProperties(listDto, listModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(listService.save(listModel));
    }

    @GetMapping
    public ResponseEntity<List<ListModel>> getAllLists(){
        return ResponseEntity.status(HttpStatus.OK).body(listService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneList(@PathVariable(value = "id") UUID id){
        Optional<ListModel> listModelOptional = listService.findById(id);
        if (listModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteList(@PathVariable(value = "id") UUID id){
        Optional<ListModel> parkingSpotModelOptional = listService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        listService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(DELETED_BODY);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateList(@PathVariable(value = "id") UUID id,
                                                           @RequestBody @Valid ListDto listDto){
        Optional<ListModel> listModelOptional = listService.findById(id);
        if (listModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        var listModel = new ListModel();
        BeanUtils.copyProperties(listDto, listModel);
        listModel.setId(listModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(listService.save(listModel));
    }
}