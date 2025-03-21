package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Report;
import com.example.expensetracker.Entity.User;
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
public class GetAllReportTest {

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

        // Mock User and Expense (Lazy Loaded Fields)
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn("MockUser");
        when(mockUser.getUsername()).thenReturn("mockusername");

        Expense mockExpense = mock(Expense.class);
        when(mockExpense.getDescription()).thenReturn("Mock Expense");

        report1.setUser(mockUser);
        report1.setExpense(mockExpense);

        report2.setUser(mockUser);
        report2.setExpense(mockExpense);
    }


    @Test
    void testGetAllReports() {
        when(reportRepository.findAll()).thenReturn(List.of(report1, report2));

        List<Report> reports = reportService.getAllReports();

        assertNotNull(reports);
        assertEquals(2, reports.size());
        verify(reportRepository, times(1)).findAll();
    }

}
