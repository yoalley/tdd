package com.ccl;

/**
 * @Description:
 * @Author: ccl
 * @Date: 2022-06-10 16:20
 */
public class BudgetService {

    private final BudgetMapper mapper;

    public BudgetService(BudgetMapper mapper) {
        this.mapper = mapper;
    }

    public long queryAmount(Period period) {
        return mapper.findAll().stream().mapToLong(budget -> budget.getOverlappingAmount(period)).sum();
    }

}
