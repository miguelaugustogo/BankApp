package com.augustogo.bankapp.domain;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.data.repository.StatementsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class SpendingTest  {
    @Mock
    private StatementsRepository repository;
    @Mock
    private Spending spending;

    @Captor
    private ArgumentCaptor<BaseCallback<List<Spending>>> loadSpendingCallBack;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void  testLIstSpending(){
        spending.setRepository(repository);
        spending.listSpending(Mockito.eq(1), loadSpendingCallBack.capture());
        Mockito.verify(spending).listSpending(Mockito.anyLong(), loadSpendingCallBack.capture());
        repository.listStatements(Mockito.anyLong(), loadSpendingCallBack.capture());
        Mockito.verify(repository).listStatements(Mockito.anyLong(), loadSpendingCallBack.capture());
    }

    @Test (expected =  Exception.class)
    public void testIdLessOrEqualsZero(){
        spending = new Spending();
        spending.setRepository(repository);
        spending.listSpending(Mockito.eq(1), loadSpendingCallBack.capture());
    }
}
