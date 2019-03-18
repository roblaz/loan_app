package com.nagel.loanapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private List<String> items = new ArrayList();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        items.add(getResources().getString(R.string.per));
        items.add(getResources().getString(R.string.intr));
        items.add(getResources().getString(R.string.rep));
        items.add(getResources().getString(R.string.outs));
        for (int n = 1; n <= Loan.getInstance().getPeriods(); ++n)
        {
            items.add("" + n);
            items.add(String.format("%1.2f", Loan.getInstance().interest(n)));
            items.add(String.format("%1.2f", Loan.getInstance().repayment(n)));
            items.add(String.format("%1.2f", Math.abs(Loan.getInstance().outstanding(n))));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GridView grid = findViewById(R.id.grid);
        adapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        adapter.addAll(items);
        grid.setAdapter(adapter);

    }
}
