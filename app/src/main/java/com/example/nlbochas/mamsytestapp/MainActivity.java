package com.example.nlbochas.mamsytestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText inputNumbers;
    private TextView numbersAmount;
    private TextView simpleNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        inputNumbers = (EditText) this.findViewById(R.id.edt_numbers);
        numbersAmount = (TextView) this.findViewById(R.id.txt_summ);
        simpleNumbers = (TextView) this.findViewById(R.id.txt_simple_numbers);
        Button calculateButton = (Button) this.findViewById(R.id.btn_calculate);
        calculateButton.setOnClickListener(this);
    }

    private static List<Integer> findPrimes(long number) {
        List<Integer> list = new ArrayList<>();
        BitSet isComposite = new BitSet ((int) number + 1);
        isComposite.set(1, true);
        for (int i = 2; i <= number; i++) {
            if (!isComposite.get(i)) {
                list.add(i);
                int multiple = 2;
                while (i * multiple <= number) {
                    isComposite.set(i * multiple, true);
                    multiple++;
                }
            }
        }
        return list;
    }

    private void showResults(long enteredNumber) {
        simpleNumbers.setText("");
        int numberAmount = 0;
        List<Integer>  numbersList = findPrimes(enteredNumber);

        for (int i = 0; i < numbersList.size(); i ++) {
            simpleNumbers.append(String.valueOf(numbersList.get(i)) + " ");
            numberAmount = numberAmount + numbersList.get(i);
        }

        numbersAmount.setText(String.valueOf(numberAmount));
    }

    @Override
    public void onClick(View v) {
        showResults(Long.parseLong(inputNumbers.getText().toString()));
        hideKeyboard();
    }

    private void hideKeyboard() {
        try {
            InputMethodManager input = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View currentFocus = getCurrentFocus();
            if (currentFocus == null) {
                return;
            }
            if (currentFocus.getWindowToken() == null) {
                return;
            }
            input.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }
}
