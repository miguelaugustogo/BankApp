package com.augustogo.bankapp.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augustogo.bankapp.domain.Spending;
import com.augustogo.bankapp.R;
import com.augustogo.bankapp.util.CoinUtil;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterCardDashBoard extends RecyclerView.Adapter<AdapterCardDashBoard.AdapterCardDashBoardViewHolder> {

    private Context context;
    private List<Spending> spendingList;

    public AdapterCardDashBoard(Context context, List<Spending> spendingList) {
        this.context = context;
        this.spendingList = spendingList;
    }

    @NonNull
    @Override
    public AdapterCardDashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View viewCreate = LayoutInflater.from(context)
                .inflate(R.layout.card_item_dashboard, viewGroup, false);
        return new AdapterCardDashBoardViewHolder(viewCreate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCardDashBoardViewHolder holder, int position) {
        if(spendingList != null){
            Spending spent = spendingList.get(position);
            holder.setItem(spent);
        }
    }

    @Override
    public int getItemCount() {
        if(spendingList != null)
            return spendingList.size();
        return 0;
    }

    public class AdapterCardDashBoardViewHolder extends RecyclerView.ViewHolder {
        private final TextView textviewTypeTransaction;
        private final TextView textviewDate;
        private final TextView textviewDescription;
        private final TextView textviewValue;

        public AdapterCardDashBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewTypeTransaction = itemView.findViewById(R.id.textview_type_transaction);
            textviewDate = itemView.findViewById(R.id.textview_date);
            textviewDescription = itemView.findViewById(R.id.textview_description);
            textviewValue = itemView.findViewById(R.id.textview_value);
        }

        public void setItem(Spending spending) {
            textviewTypeTransaction.setText(spending.getTypeTransaction());
            textviewDate.setText(spending.getDate());
            textviewDescription.setText(spending.getDescription());
            textviewValue.setText(CoinUtil.formatReal(spending.getValue()));
        }
    }

    public static String formatReal(Double coin){
        NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String format = money.format(coin);
        return format.contains("(") ? format.replace("(", "-")
                .replace(")", ""):format;
    }
}
