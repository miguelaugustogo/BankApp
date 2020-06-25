package com.augustogo.bankapp.ui.dashboard;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.config.RetrofitApi;
import com.augustogo.bankapp.data.remote.StatementsService;
import com.augustogo.bankapp.data.repository.DashBoardRepository;
import com.augustogo.bankapp.domain.Spending;
import com.augustogo.bankapp.domain.UserAccount;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class DashBoardTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    StatementsService statementsService;
    @Mock
    UserAccount userAccount;
    @Mock
    DashBoardRepository dashBoardRepository;
    @Mock
    DashBoardViewModel dashBoardViewModel;
    @Mock
    Observer<List<Spending>> listSpendingObserver;
    @Captor
    ArgumentCaptor<BaseCallback<List<Spending>>> callbackArgumentCaptor;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        statementsService = RetrofitApi.create(StatementsService.class);
        userAccount= new UserAccount("teste@teste.teste","Test@1");
        dashBoardRepository = DashBoardRepository.getInstance();
        dashBoardViewModel = new DashBoardViewModel();
        dashBoardViewModel.listSpendingLiveData().observeForever(listSpendingObserver);
    }

    @Test
    public void listSpending(){

        dashBoardRepository.listStatements(1, callbackArgumentCaptor.capture());
        assertTrue(dashBoardViewModel.listSpendingLiveData().hasObservers());
    }
}
