package com.augustogo.bankapp.ui.login;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.augustogo.bankapp.domain.UserAccount;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    LoginViewModel loginViewModel;
    @Mock
    Observer<UserAccount> userAccountObserver;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loginViewModel = new LoginViewModel();
        loginViewModel.userAccountLiveData().observeForever(userAccountObserver);
    }

    @Test
    public void setLoginViewModel(){
        loginViewModel.login("teste@teste.teste","Test@1");
        assertTrue(loginViewModel.userAccountLiveData().hasObservers());
        loginViewModel.userAccountLiveData();
        assertNotNull(userAccountObserver);
    }
}
