package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.repository.YouTubeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YouTubeService {

    private final YouTubeRepository repository;


}

