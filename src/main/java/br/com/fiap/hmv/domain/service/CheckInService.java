package br.com.fiap.hmv.domain.service;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class CheckInService {

    private static final long AVG_SERVICE_TIME_IN_MINUTES = 7L;

    public static void calculateServiceQueueTime(
            List<CheckIn> awaitingAttendance, List<User> attendantsInService, final CheckIn checkIn
    ) {
        LocalDateTime estimatedOpeningHours = checkIn.getInclusionDate()
                .plusMinutes(checkIn.getEstimatedTimeArrival().getMinutes())
                .plusMinutes(AVG_SERVICE_TIME_IN_MINUTES * awaitingAttendance.size());
        checkIn.setQueuePatientsNumber(awaitingAttendance.size());
        checkIn.setEstimatedOpeningHours(estimatedOpeningHours);
    }

}