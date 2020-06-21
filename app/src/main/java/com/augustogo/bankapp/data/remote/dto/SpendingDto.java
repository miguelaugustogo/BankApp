package com.augustogo.bankapp.data.remote.dto;

import com.augustogo.bankapp.domain.Spending;
import com.augustogo.bankapp.util.DateTypeDeserializer;

import java.util.ArrayList;
import java.util.List;

public class SpendingDto {
    private List<StatementsDto> statementList;
    private Error error;

    public List<StatementsDto> getStatementList() {
        return statementList;
    }

    public void setStatementList(List<StatementsDto> statementList) {
        this.statementList = statementList;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<Spending> toDomain() {
        List<Spending> list = new ArrayList<>();
        for (StatementsDto statementsDto : statementList) {

            list.add(new Spending(statementsDto.getTitle(), statementsDto.getDesc(),
                    DateTypeDeserializer.formatDate(statementsDto.getDate()), statementsDto.getValue()));
        }
        return list;
    }
}
