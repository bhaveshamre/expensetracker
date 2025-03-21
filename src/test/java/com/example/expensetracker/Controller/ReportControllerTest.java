package com.example.expensetracker.Controller;

import com.example.expensetracker.Entity.Report;
import com.example.expensetracker.Service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private Report report;

    @BeforeEach
    void setUp() {
        report = new Report();
        report.setId(1L);
        report.setAmount(500.0);
        report.setAmount(500.0);
    }

    @Test
    void testGetAllReports() {
        when(reportService.getAllReports()).thenReturn(List.of(report));
        List<Report> result = reportController.getAllReports();
        assertEquals(1, result.size());
        assertEquals(500.0, result.get(0).getAmount());
    }

    @Test
    void testGetUserExpenseSummary() {
        Map<String, Double> mockSummary = Map.of("User1", 1000.0, "User2", 750.0);
        when(reportService.getUserExpenseSummary()).thenReturn(mockSummary);
        Map<String, Double> result = reportController.getUserExpenseSummary();
        assertEquals(1000.0, result.get("User1"));
        assertEquals(750.0, result.get("User2"));
    }

    @Test
    void testGetExpensesByDate() {
        LocalDate date = LocalDate.of(2024, 3, 18);
        Map<LocalDate, List<Report>> mockReportMap = Map.of(date, List.of(report));
        when(reportService.getExpensesByDate()).thenReturn(mockReportMap);
        Map<LocalDate, List<Report>> result = reportController.getExpensesByDate();
        assertTrue(result.containsKey(date));
        assertEquals(1, result.get(date).size());
        assertEquals(500.0, result.get(date).get(0).getAmount());
    }
}

