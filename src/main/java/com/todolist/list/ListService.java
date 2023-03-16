package com.todolist.list;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    @Transactional
    public ListModel save(ListModel listModel) {
        return listRepository.save(listModel);
    }

    public List<ListModel> findAll() {
        return listRepository.findAll();
    }

    public Optional<ListModel> findById(UUID id) {
        return listRepository.findById(id);
    }

    @Transactional
    public void delete(ListModel listModel) {
        listRepository.delete(listModel);
    }
}
