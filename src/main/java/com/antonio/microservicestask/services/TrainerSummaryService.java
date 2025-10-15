package com.antonio.microservicestask.services;

import com.antonio.microservicestask.entities.MonthSummary;
import com.antonio.microservicestask.entities.TrainerSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.entities.YearSummary;
import com.antonio.microservicestask.repositories.TrainerSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerSummaryService {

    private final TrainerSummaryRepository repository;

    public void processTrainingEvent(TrainerWorkload workload) {
        log.debug("Operation: Processing training event for username: {}", workload.getUsername());
        Optional<TrainerSummary> existing = repository.findFirstByUsername(workload.getUsername());
        
        LocalDateTime trainingDate = workload.getTrainingDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        int year = trainingDate.getYear();
        String month = trainingDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        if (existing.isEmpty()) {
            log.debug("Operation: Creating new trainer summary");
            createNewTrainerSummary(workload, year, month);
        } else {
            log.debug("Operation: Updating existing trainer summary");
            updateExistingTrainerSummary(existing.get(), workload, year, month);
        }
        log.debug("Operation completed: Training event processed");
    }

    private void createNewTrainerSummary(TrainerWorkload workload, int year, String month) {
        MonthSummary monthSummary = new MonthSummary();
        monthSummary.setMonth(month);
        monthSummary.setTotalDuration(workload.getTrainingDuration());

        YearSummary yearSummary = new YearSummary();
        yearSummary.setYear(year);
        yearSummary.setMonths(List.of(monthSummary));

        TrainerSummary summary = new TrainerSummary();
        summary.setUsername(workload.getUsername());
        summary.setFirstName(workload.getFirstName());
        summary.setLastName(workload.getLastName());
        summary.setActive(workload.isActive());
        summary.setYears(List.of(yearSummary));

        repository.save(summary);
    }

    private void updateExistingTrainerSummary(TrainerSummary summary, TrainerWorkload workload, int year, String month) {
        YearSummary yearSummary = getYearSummary(summary, year);

        MonthSummary monthSummary = getMonthSummary(month, yearSummary);

        monthSummary.setTotalDuration(monthSummary.getTotalDuration() + workload.getTrainingDuration());
        repository.save(summary);
    }

    private YearSummary getYearSummary(TrainerSummary summary, int year) {
        YearSummary yearSummary = summary.getYears().stream()
                .filter(y -> y.getYear().equals(year))
                .findFirst()
                .orElseGet(() -> {
                    YearSummary newYear = new YearSummary();
                    newYear.setYear(year);
                    newYear.setMonths(new ArrayList<>());
                    summary.getYears().add(newYear);
                    return newYear;
                });
        return yearSummary;
    }

    private MonthSummary getMonthSummary(String month, YearSummary yearSummary) {
        MonthSummary monthSummary = yearSummary.getMonths().stream()
                .filter(m -> m.getMonth().equals(month))
                .findFirst()
                .orElseGet(() -> {
                    MonthSummary newMonth = new MonthSummary();
                    newMonth.setMonth(month);
                    newMonth.setTotalDuration(0);
                    yearSummary.getMonths().add(newMonth);
                    return newMonth;
                });
        return monthSummary;
    }

    public void updateTrainerSummary(String username, String firstName, String lastName, Boolean active, List<YearSummary> years) {
        log.debug("Operation: Updating trainer summary for username: {}", username);
        repository.updateByUsername(username, firstName, lastName, active, years);
        log.debug("Operation completed: Trainer summary updated");
    }
}