package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.repository.YouTubeCashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YouTubeService {

    private final YouTubeCashRepository repository;


}

