package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Report;
import com.example.expensetracker.Repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private Report report1, report2;

    @BeforeEach
    void setUp() {
        report1 = new Report();
        report1.setId(1L);
        report1.setUsername("User1");
        report1.setAmount(500.0);
        report1.setDate(LocalDateTime.of(2024, 3, 18, 10, 0));

        report2 = new Report();
        report2.setId(2L);
        report2.setUsername("User2");
        report2.setAmount(750.0);
        report2.setDate(LocalDateTime.of(2024, 3, 18, 15, 30));
    }

    @Test
    void testGetUserExpenseSummary() {
        when(reportRepository.findAll()).thenReturn(List.of(report1, report2));

        Map<String, Double> summary = reportService.getUserExpenseSummary();
        System.out.println("User Expense Summary: " + summary);  // Debugging

        assertNotNull(summary);
        assertEquals(500.0, summary.get("User1"));
        assertEquals(750.0, summary.get("User2"));
    }

    @Test
    void testGetExpensesByDate() {
        when(reportRepository.findAll()).thenReturn(List.of(report1, report2));

        Map<LocalDate, List<Report>> reportByDate = reportService.getExpensesByDate();
        System.out.println("Expenses By Date: " + reportByDate);  // Debugging

        LocalDate expectedDate = report1.getDate().toLocalDate();
        assertTrue(reportByDate.containsKey(expectedDate));
        assertEquals(2, reportByDate.get(expectedDate).size());
    }



}
