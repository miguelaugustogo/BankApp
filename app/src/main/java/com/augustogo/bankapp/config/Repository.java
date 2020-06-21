package com.augustogo.bankapp.config;

public class Repository {

    public final RepositoryData data;

    protected Repository(){
        this.data = new RepositoryData();
    }

    public class RepositoryData{
        public <T> T restApi(Class<T> type){return App.getRetrofitFactory().createService(type);}
    }
}
