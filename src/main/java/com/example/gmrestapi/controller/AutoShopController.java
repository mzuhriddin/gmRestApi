package com.example.gmrestapi.controller;

import com.example.gmrestapi.dto.ApiResponse;
import com.example.gmrestapi.dto.AutoShopDto;
import com.example.gmrestapi.entity.AutoShop;
import com.example.gmrestapi.repository.AutoShopRepository;
import com.example.gmrestapi.service.AutoShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autoShop")
@RequiredArgsConstructor
public class AutoShopController {

    final AutoShopRepository autoShopRepository;
    final AutoShopService autoShopService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<AutoShop> all = autoShopRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<AutoShop> optionalAutoShop = autoShopRepository.findById(id);
        return ResponseEntity.status(optionalAutoShop.isEmpty() ? 404 : 200).body(optionalAutoShop.orElse(new AutoShop()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody AutoShopDto autoShopDto) {
        ApiResponse add = autoShopService.add(autoShopDto);
        return ResponseEntity.status(add.isSuccess() ? 201 : 409).body(add);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody AutoShopDto autoShopDto) {
        ApiResponse edit = autoShopService.edit(id, autoShopDto);
        return ResponseEntity.status(edit.isSuccess() ? 200 : 409).body(edit);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<AutoShop> byId = autoShopRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.status(404).body("NOT FOUND");
        }
        AutoShop autoShop = byId.get();
        autoShop.setActive(false);
        autoShopRepository.save(autoShop);
        return ResponseEntity.ok().body("DELETED");

    }

}
