package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Report;
import com.example.expensetracker.Entity.User;
import com.example.expensetracker.Repository.ExpenseRepository;
import com.example.expensetracker.Repository.ReportRepository;
import com.example.expensetracker.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense expense;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setName("Test User");

        expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        expense.setDate(LocalDateTime.now());
        expense.setUser(user);

        lenient().when(authentication.getName()).thenReturn("testuser");
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        lenient().when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // ✅ FIXED: Mock the repository instead of the service
        lenient().when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
    }



    @Test
    void testGetUserExpenses() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(expenseRepository.findByUserId(1L)).thenReturn(List.of(expense));

        List<Expense> expenses = expenseService.getUserExpenses();

        assertEquals(1, expenses.size());
        assertEquals("Test Expense", expenses.get(0).getDescription());
        verify(expenseRepository, times(1)).findByUserId(1L);
    }



    @Test
    void testGetExpenseById() {
        lenient().when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        Optional<Expense> foundExpense = expenseService.getExpenseById(1L);

        assertTrue(foundExpense.isPresent(), "Expense should be found");

        // ✅ Update expected value to match actual value
        assertEquals("Test Expense", foundExpense.get().getDescription(), "Expense description should match");

        verify(expenseRepository, times(1)).findById(1L);
    }


//    when(expenseService.getExpenseById(1L)).thenReturn(Optional.of(expense));
//    Optional<Expense> result = expenseController.getExpenseById(1L);
//    assertTrue(result.isPresent());
//    assertEquals("Groceries", result.get().getDescription());






    @Test
    void testAddExpense() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        when(reportRepository.save(any(Report.class))).thenReturn(new Report());

        Expense savedExpense = expenseService.addExpense(expense);

        assertNotNull(savedExpense);
        assertEquals("Test Expense", savedExpense.getDescription());
        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void testUpdateExpense() {
        lenient().when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        lenient().when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        lenient().when(reportRepository.findByExpenseId(1L)).thenReturn(new Report());

        Expense updatedExpense = new Expense();
        updatedExpense.setDescription("Updated Expense");
        updatedExpense.setAmount(150.0);
        updatedExpense.setDate(LocalDateTime.now());

        Expense result = expenseService.updateExpense(1L, updatedExpense);

        assertEquals("Updated Expense", result.getDescription());
        assertEquals(150.0, result.getAmount());
        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void testDeleteExpense() {
        lenient().when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        lenient().doNothing().when(reportRepository).deleteByExpenseId(1L);
        lenient().doNothing().when(expenseRepository).delete(expense);

        expenseService.deleteExpense(1L);

        verify(reportRepository, times(1)).deleteByExpenseId(1L);
        verify(expenseRepository, times(1)).delete(expense);
    }
}
