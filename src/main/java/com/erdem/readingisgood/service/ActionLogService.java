package com.erdem.readingisgood.service;

import com.erdem.readingisgood.model.ActionLog;
import com.erdem.readingisgood.repository.ActionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class ActionLogService {

    private final ActionLogRepository actionLogRepository;

    public <T> void save(T object, ActionLog.ActionLogTypeEnum type, String detail) {
        actionLogRepository.save(build(object, type, detail));
    }

    private <T> ActionLog<T> build(T object, ActionLog.ActionLogTypeEnum type, String detail) {
        ActionLog<T> actionLog = new ActionLog<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            actionLog.setUsername(authentication.getName());
        }
        actionLog.setTimestamp(Instant.now().toEpochMilli());
        actionLog.setType(type);
        actionLog.setDetail(detail);
        actionLog.setRelatedObject(object);
        return actionLog;
    }
}
