package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.repository.CommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandService {

    private final CommandRepository repository;

    public CommandEntity save(String command) {
        CommandEntity commandEntity = repository.findByCommand(command).orElse(null);
        if (isNull(commandEntity)) {
            CommandEntity entity = new CommandEntity(null, command, null, null);
            return repository.save(entity);
        }
        return commandEntity;
    }

    public CommandEntity getCommand(String command) {
        return repository.findByCommand(command).orElseThrow(() -> new CommandServiceException(command + " not found!"));
    }

    static class CommandServiceException extends RuntimeException {

        CommandServiceException(String m) {
            super(m);
        }
    }
}
