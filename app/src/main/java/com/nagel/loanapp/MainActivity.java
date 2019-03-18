package com.nagel.loanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtCost, txtLoan, txtRate, txtPaym, txtYear, txtTerm;
    private Button btnAmortisation, btnCalculate, btnClear;

    private void inuitViews(){
        txtCost = findViewById(R.id.txtCost);
        txtLoan = findViewById(R.id.txtLoan);
        txtRate = findViewById(R.id.txtRate);
        txtPaym = findViewById(R.id.txtPaym);
        txtYear = findViewById(R.id.txtYear);
        txtTerm = findViewById(R.id.txtTerm);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCalculate(v);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClear(v);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inuitViews();
        btnAmortisation = findViewById(R.id.btnAmortisation);

        btnAmortisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Loan.getInstance().getPeriods() > 0) {
                    Intent intent = new Intent(MainActivity.this, PlanActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        disable(txtPaym);

    }

    private void disable(View v){
        v.setEnabled(false);
    }

    public void onClear(View view) {
        txtCost.setText("");
        txtLoan.setText("");
        txtRate.setText("");
        txtPaym.setText("");
        txtYear.setText("");
        txtTerm.setText("");
        Loan.getInstance().setPrincipal(1);
        Loan.getInstance().setInterestRate(1);
        Loan.getInstance().setPeriods(1);
        txtCost.requestFocus();
    }

    public void onCalculate(View view) {
        double cost = 0;
        double loan;
        double rate;
        int year = 0;
        int term = 0;
        try {
            String text = txtCost.getText().toString().trim();
            if (text.length() > 0) {
                cost = Double.parseDouble(text);
                if (cost < 0) throw new Exception();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Coast format exception. Must be >0", Toast.LENGTH_SHORT).show();
            txtCost.requestFocus();
            return;
        }
        try {
            loan = Double.parseDouble(txtLoan.getText().toString().trim());
            if (loan < 0) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "Loan < 0", Toast.LENGTH_SHORT).show();
            txtLoan.requestFocus();
            return;
        }
        try {
            rate = Double.parseDouble(txtRate.getText().toString().trim());
            if (rate <= 0 && rate > 50) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "rate must be 1 to 50", Toast.LENGTH_SHORT).show();
            txtRate.requestFocus();
            return;
        }
        try {
            year = Integer.valueOf(txtYear.getText().toString().trim());
            if (year <= 0 && year > 60) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "Year must be 1 to 60", Toast.LENGTH_SHORT).show();
            txtYear.requestFocus();
            return;
        }
        try {
            term = Integer.valueOf(txtTerm.getText().toString().trim());
            if (term <= 0 || term > 12) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "term must be 1 to 12", Toast.LENGTH_SHORT).show();
            txtTerm.requestFocus();
            return;
        }
        Loan.getInstance().setPrincipal(loan + cost);
        Loan.getInstance().setInterestRate(rate / 100 / term);
        Loan.getInstance().setPeriods(year * term);
        txtPaym.setText(String.format("%1.2f", Loan.getInstance().payment()));
    }

    public void onAmort(View view) {
        if (Loan.getInstance().getPeriods() > 0) {
            Intent intent = new Intent(this, PlanActivity.class);
            startActivity(intent);
        }
    }
}

