package dev.chel_shev.fast.inquiry.command.workout.remove;

import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutRemoveHandler extends FastCommandInquiryHandler<WorkoutRemoveInquiry> {
}