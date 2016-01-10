package com.example.su.zuhe;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by su on 2016/1/9.
 */
public class Calculator extends Activity {
    private Button[] btnNum = new Button[11];
    private Button[] btnCommand = new Button[5];
    private EditText editText = null;
    private Button btnClear = null;
    private String lastCommand;
    private boolean clearFlag;
    private boolean firstFlag;
    private double result;
    public Calculator() {
        result = 0;
        firstFlag = true;
        clearFlag = false;
        lastCommand = "=";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        btnCommand[0] = (Button) findViewById(R.id.add);
        btnCommand[1] = (Button) findViewById(R.id.subtract);
        btnCommand[2] = (Button) findViewById(R.id.multiply);
        btnCommand[3] = (Button) findViewById(R.id.divide);
        btnCommand[4] = (Button) findViewById(R.id.equal);
        btnNum[0] = (Button) findViewById(R.id.num0);
        btnNum[1] = (Button) findViewById(R.id.num1);
        btnNum[2] = (Button) findViewById(R.id.num2);
        btnNum[3] = (Button) findViewById(R.id.num3);
        btnNum[4] = (Button) findViewById(R.id.num4);
        btnNum[5] = (Button) findViewById(R.id.num5);
        btnNum[6] = (Button) findViewById(R.id.num6);
        btnNum[7] = (Button) findViewById(R.id.num7);
        btnNum[8] = (Button) findViewById(R.id.num8);
        btnNum[9] = (Button) findViewById(R.id.num9);
        btnNum[10] = (Button) findViewById(R.id.point);
        editText = (EditText) findViewById(R.id.result);
        editText.setText("0.0");
        NumberAction na = new NumberAction();
        CommandAction ca = new CommandAction();
        for (Button bc : btnCommand) {
            bc.setOnClickListener((View.OnClickListener) ca);
        }
        for (Button bc : btnNum) {
            bc.setOnClickListener((View.OnClickListener) na);
        }
        btnClear = (Button) findViewById(R.id.clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("0.0");
                result = 0;
                firstFlag = true;
                clearFlag = false;
                lastCommand = "=";
            }
        });
    }
    private class NumberAction implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;
            String input = btn.getText().toString();
            if (firstFlag) {
                if (input.equals(".")) {
                    return;
                }
                if (editText.getText().toString().equals("0.0")) {
                    editText.setText("");
                }
                firstFlag = false;
            } else {
                String editTextStr = editText.getText().toString();
                if (editTextStr.indexOf(".") != -1 && input.equals(".")) {
                    return;
                }
                if (editTextStr.equals("-") && input.equals(".")) {
                    return;
                }
                if (editTextStr.equals("0") && !input.equals(".")) {
                    return;
                }
            }
            if (clearFlag) {
                editText.setText("");
                clearFlag = false;
            }
            editText.setText(editText.getText().toString() + input);
        }
    }
    private class CommandAction implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;
            String inputCommand = (String) btn.getText();
            if (firstFlag) {
                if (inputCommand.equals("-")) {
                    editText.setText("-");
                    firstFlag = false;
                }
            } else {
                if (!clearFlag) {
                    calculate(Double.parseDouble(editText.getText().toString()));
                }
                lastCommand = inputCommand;
                clearFlag = true;
            }
        }
    }
    private void calculate(double x) {


        if (lastCommand.equals("+")) {
            result += x;
        } else if (lastCommand.equals("-")) {
            result -= x;
        } else if (lastCommand.equals("*")) {
            result *= x;
        } else if (lastCommand.equals("/")) {
            result /= x;
        } else if (lastCommand.equals("=")) {
            result = x;
        }
        editText.setText("" + result);
    }
}
