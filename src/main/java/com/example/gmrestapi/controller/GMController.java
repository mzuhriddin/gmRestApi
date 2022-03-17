package com.example.gmrestapi.controller;


import com.example.gmrestapi.dto.ApiResponse;
import com.example.gmrestapi.dto.GMDto;
import com.example.gmrestapi.entity.GM;
import com.example.gmrestapi.repository.GMRepository;
import com.example.gmrestapi.service.GMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/gm")
@RequiredArgsConstructor
public class GMController {
    final GMRepository gmRepository;
    final GMService gmService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<GM> all = gmRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable UUID id) {
        Optional<GM> optionalGM = gmRepository.findById(id);
        return ResponseEntity.status(optionalGM.isEmpty() ? 404 : 200).body(optionalGM.orElse(new GM()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody GMDto gmDto) {
        ApiResponse add = gmService.add(gmDto);
        return ResponseEntity.status(add.isSuccess() ? 201 : 409).body(add);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody GMDto gmDto) {
        ApiResponse edit = gmService.edit(id, gmDto);
        return ResponseEntity.status(edit.isSuccess() ? 200 : 409).body(edit);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
//        if (gmRepository.existsById(id)) {
//            gmRepository.deleteById(id);
//            return ResponseEntity.ok().body("Deleted");
//        }
//        else return  ResponseEntity.status(404).body("GM NOT FOUND");

        Optional<GM> byId = gmRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.status(404).body("NOT FOUND");
        }
        GM gm = byId.get();
        gm.setActive(false);
        gmRepository.save(gm);
        return ResponseEntity.ok().body("DELETED");

    }
}
