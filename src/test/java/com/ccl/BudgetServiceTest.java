package com.ccl;

import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Description:
 * @Author: ccl
 * @Date: 2022-06-10 16:19
 */
public class BudgetServiceTest {

    BudgetMapper mapper = mock(BudgetMapper.class);
    BudgetService budgetService = new BudgetService(mapper);
    @Test
    public void no_budgets() {
        givenBudgets();
        assertAmount(LocalDate.of(2022,06,10), LocalDate.of(2022,06,20), 0);
    }

    @Test
    public void query_whole_month() {
        givenBudgets(new Budget(YearMonth.of(2022, 06), 30));
        assertAmount(LocalDate.of(2022, 06, 01),
                LocalDate.of(2022, 06, 30),
                30);
    }

    @Test
    public void query_1_day() {
        givenBudgets(new Budget(YearMonth.of(2022, 06), 30));
        assertAmount(LocalDate.of(2022, 06, 01),
                LocalDate.of(2022, 06, 01),
                01);
    }
    @Test
    public void query_2_day() {
        givenBudgets(new Budget(YearMonth.of(2022, 06), 30));
        assertAmount(LocalDate.of(2022, 06, 01),
                LocalDate.of(2022, 06, 02),
                02);
    }

    @Test
    public void query_start_before_budgets() {
        givenBudgets(new Budget(YearMonth.of(2022, 06), 30));
        assertAmount(LocalDate.of(2022, 05, 25),
                LocalDate.of(2022, 06, 02),
                02);
    }

    @Test
    public void query_end_after_budgets() {
        givenBudgets(new Budget(YearMonth.of(2022, 06), 30));
        assertAmount(LocalDate.of(2022, 06, 25),
                LocalDate.of(2022, 07, 02),
                06);
    }

    @Test
    public void query_out_of_budgets() {
        givenBudgets(new Budget(YearMonth.of(2022, 06), 30));
        assertAmount(LocalDate.of(2022, 05, 25),
                LocalDate.of(2022, 05, 29),
                0);
    }

    @Test
    public void query_2_budgets() {
        givenBudgets(new Budget(YearMonth.of(2022, 05), 310),
                new Budget(YearMonth.of(2022, 06), 30));
        assertAmount(LocalDate.of(2022, 05, 11),
                LocalDate.of(2022, 06, 20),
                210+20);
    }

    private void assertAmount(LocalDate start, LocalDate end, long expected) {
        long amount  =  budgetService.queryAmount(new Period(start, end));
        assertEquals(expected,amount);
    }

    private OngoingStubbing<List<Budget>> givenBudgets(Budget... budgets) {
        return when(mapper.findAll()).thenReturn(Arrays.asList(budgets));
    }
}
