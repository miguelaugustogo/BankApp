package com.augustogo.bankapp.domain;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.ui.login.LoginActivity;
import com.augustogo.bankapp.data.repository.LoginRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserAccountTest {

    @Mock
    private LoginActivity view;
    @Mock
    private LoginRepository repository;
    @Mock
    private UserAccount userAccount;

    @Captor
    private ArgumentCaptor<BaseCallback<UserAccount>> loadUserCallBack;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public  void testLoginDomain(){
        userAccount.setRepository(repository);
        Mockito.when(userAccount.getUsername()).thenReturn("test@test.teste");
        Mockito.when(userAccount.getPassword()).thenReturn("Test@1");
        Assert.assertEquals(userAccount.getUsername(), "test@test.teste");
        Assert.assertEquals(userAccount.getPassword(), "Test@1");
        userAccount.login(loadUserCallBack.capture());
        Mockito.verify(userAccount).login(loadUserCallBack.capture());
    }

    @Test
    public void loadPreference(){
        userAccount.setRepository(repository);
        userAccount.loadPreference(Mockito.eq(view.getContext()), loadUserCallBack.capture());
        Mockito.verify(userAccount).loadPreference(Mockito.eq(view.getContext()), loadUserCallBack.capture());
    }

    @Test(expected = Exception.class)
    public void testRepositoryNull(){
        userAccount = new UserAccount("test@test.teste", "Test@1");
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldPasswordLowerCase(){
        userAccount = new UserAccount("test@test.teste", "TEST@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldPasswordUpCase(){
        userAccount = new UserAccount("test@test.teste", "test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldPasswordNumber(){
        userAccount = new UserAccount("test@test.teste", "test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldPasswordSpecial(){
        userAccount = new UserAccount("test@test.teste", "TestE1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldPasswordLength(){
        userAccount = new UserAccount("test@test.teste", "tes");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test
    public void testLoginEmailOk(){
        userAccount = new UserAccount("test@test.teste", "Test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test
    public void testLoginCpfOk(){
        userAccount = new UserAccount("12345678910", "Test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testLoginCpfInvalid(){
        userAccount = new UserAccount("1234567891", "Test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldUsernameAt(){
        userAccount = new UserAccount("testtest.teste", "Test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldUsernameDot(){
        userAccount = new UserAccount("test@testteste", "Test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldUsernameEmpty() {
        userAccount = new UserAccount("", "Test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldUsernameSpace() {
        userAccount = new UserAccount("  ", "Test@1");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldPasswordEmpty() {
        userAccount = new UserAccount("test@test.teste", "");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

    @Test(expected = Exception.class)
    public void testFieldPasswordSpace() {
        userAccount = new UserAccount("test@test.teste", "  ");
        userAccount.setRepository(repository);
        userAccount.login(null);
    }

}
