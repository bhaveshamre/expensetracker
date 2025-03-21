package com.example.expensetracker.Controller;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense expense;

    @BeforeEach
    void setUp() {
        expense = new Expense();
        expense.setId(1L);
        expense.setAmount(100.0);
        expense.setDescription("Groceries");
    }

    @Test
    void testGetAllExpenses() {
        when(expenseService.getUserExpenses()).thenReturn(List.of(expense));
        List<Expense> result = expenseController.getAllExpenses();
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
    }

    @Test
    void testGetExpenseById() {
        when(expenseService.getExpenseById(1L)).thenReturn(Optional.of(expense));
        Optional<Expense> result = expenseController.getExpenseById(1L);
        assertTrue(result.isPresent());
        assertEquals("Groceries", result.get().getDescription());
    }

    @Test
    void testCreateExpense() {
        when(expenseService.addExpense(expense)).thenReturn(expense);
        Expense result = expenseController.createExpense(expense);
        assertEquals(100.0, result.getAmount());
    }

    @Test
    void testUpdateExpense() {
        when(expenseService.updateExpense(1L, expense)).thenReturn(expense);
        Expense result = expenseController.updateExpense(1L, expense);
        assertEquals(100.0, result.getAmount());
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseService).deleteExpense(1L);
        expenseController.deleteExpense(1L);
        verify(expenseService, times(1)).deleteExpense(1L);
    }
}

