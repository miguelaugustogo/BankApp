package com.augustogo.bankapp.ui.dashboard;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.augustogo.bankapp.domain.Spending;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class DashBoardViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Mock
    DashBoardViewModel dashBoardViewModel;
    @Mock
    Observer<List<Spending>> listSpendingObserver;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        dashBoardViewModel = new DashBoardViewModel();
        dashBoardViewModel.listSpendingLiveData().observeForever(listSpendingObserver);
    }

    @Test
    public void setListSpendingViewModel(){

        dashBoardViewModel.listSpending(1);
        assertTrue(dashBoardViewModel.listSpendingLiveData().hasObservers());
        dashBoardViewModel.listSpendingLiveData();
        assertNotNull(listSpendingObserver);
    }
}
