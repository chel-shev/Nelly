package dev.chel_shev.mind.controller;

import dev.chel_shev.mind.entity.MiRecordEntity;
import dev.chel_shev.mind.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mind/record")
@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/{recordId}")
    MiRecordEntity getRecord(@PathVariable Long recordId) {
        return recordService.getRecord(recordId);
    }

    @PostMapping("/")
    MiRecordEntity addRecord(@RequestBody MiRecordEntity record) {
        return recordService.addRecord(record);
    }
}
