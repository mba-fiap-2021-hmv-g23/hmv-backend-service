package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckInAppService {

    private final CheckInPort checkInPort;
    private final AttendancePort attendancePort;

    public Mono<Void> checkIn(final CheckIn checkIn) {
        return checkInPort.insert(checkIn).flatMap(u -> Mono.zip(
                        checkInPort.findAwaitingAttendance(),
                        attendancePort.findAttendantsInService())
                .flatMap(tuple2 -> {
                    List<CheckIn> awaitingAttendance = tuple2.getT1();
                    List<User> attendantsInService = tuple2.getT2();
                    return Mono.empty();
                }));
    }
}
