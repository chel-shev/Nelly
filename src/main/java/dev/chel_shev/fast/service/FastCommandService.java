package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.repository.FastBotCommandRepository;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FastCommandService {

    private final FastBotCommandRepository repository;

    public FastCommandEntity save(String command, String label, FastInquiryType type) {
        FastCommandEntity commandEntity = repository.findByName(command).orElse(new FastCommandEntity());
        commandEntity.setLabel(label);
        commandEntity.setName(command);
        commandEntity.setType(type);
        return repository.save(commandEntity);
    }

    public FastCommandEntity getCommand(String command) {
        return repository.findByName(command).orElseThrow(() -> new CommandServiceException(command + " not found!"));
    }

    public FastCommandEntity getCommandByLabel(String label) {
        return repository.findByLabel(label).orElseThrow(() -> new CommandServiceException(label + " not found!"));
    }

    public FastCommandEntity getCommandByLabelOrName(String label) {
        return repository.findByLabelOrName(label, label).orElseThrow(() -> new CommandServiceException(label + " not found!"));
    }

    public boolean containsCommand(String text) {
        return true;
    }

    public boolean isCommandInquiry(String label) {
        List<FastInquiryType> fastInquiryTypes = Arrays.asList(FastInquiryType.COMMAND, FastInquiryType.COMMAND_SUBSCRIPTION);
        return repository.existsByLabelAndTypeInOrNameAndTypeIn(label, fastInquiryTypes, label, fastInquiryTypes);
    }

    public List<String> getCommands() {
        return repository.findAll().stream().map(FastCommandEntity::getLabel).toList();
    }

    public List<FastCommandEntity> getAllSubscription() {
        return repository.findAllByTypeIn(Arrays.asList(FastInquiryType.COMMAND_SUBSCRIPTION, FastInquiryType.KEYBOARD_SUBSCRIPTION));
    }

    static class CommandServiceException extends RuntimeException {

        CommandServiceException(String m) {
            super(m);
        }
    }
}
