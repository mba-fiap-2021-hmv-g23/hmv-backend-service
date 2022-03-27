package br.com.fiap.hmv.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Getter
@Setter
@Builder
public class AttendanceQueueCalls {

    private List<CheckIn> inCall;
    private List<CheckIn> lastCalls;
    private List<CheckIn> awaitingCall;

    public List<CheckIn> getInCall() {
        return requireNonNullElse(inCall, new ArrayList<>());
    }

    public List<CheckIn> getLastCalls() {
        return requireNonNullElse(lastCalls, new ArrayList<>());
    }

    public List<CheckIn> getAwaitingCall() {
        return requireNonNullElse(awaitingCall, new ArrayList<>());
    }

}