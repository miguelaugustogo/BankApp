package com.augustogo.bankapp.ui.login;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.config.RetrofitApi;
import com.augustogo.bankapp.data.remote.LoginService;
import com.augustogo.bankapp.data.repository.LoginRepository;
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

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class LoginRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    LoginService loginService;
    @Mock
    UserAccount userAccount;
    @Mock
    LoginRepository loginRepository;
    @Mock
    LoginViewModel loginViewModel;
    @Mock
    Observer<UserAccount> userAccountObserver;
    @Captor
    ArgumentCaptor<BaseCallback<UserAccount>> callbackArgumentCaptor;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loginService = RetrofitApi.create(LoginService.class);
        userAccount= new UserAccount("teste@teste.teste","Test@1");
        loginRepository = LoginRepository.getInstance();
        loginViewModel = new LoginViewModel();
        loginViewModel.userAccountLiveData().observeForever(userAccountObserver);
    }

    @Test
    public void login(){

        loginRepository.login("teste@teste.teste","Test@1", callbackArgumentCaptor.capture());
        assertTrue(loginViewModel.userAccountLiveData().hasObservers());
    }

}

