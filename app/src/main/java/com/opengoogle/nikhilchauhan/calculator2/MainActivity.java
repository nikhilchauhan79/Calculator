package com.opengoogle.nikhilchauhan.calculator2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.*;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private EditText input;
    private EditText opText;
    private EditText result;

    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(STATE_PENDING_OPERATION,pendingOperation);
        if(operand1!=null)
        {
            outState.putDouble(STATE_OPERAND1,operand1);
        }
        super.onSaveInstanceState(outState, outPersistentState);

    }

    public class YourService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            // do your jobs here
            return super.onStartCommand(intent, flags, startId);
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation=savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1=savedInstanceState.getDouble(STATE_OPERAND1);
        opText.setText(pendingOperation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, YourService.class));

        input = (EditText) findViewById(R.id.inputText);
        opText = (EditText) findViewById(R.id.operator);
        result = (EditText) findViewById(R.id.result);


        Button zeroButton = (Button) findViewById(R.id.zero);
        Button oneButton = (Button) findViewById(R.id.one);
        Button twoButton = (Button) findViewById(R.id.two);
        Button threeButton = (Button) findViewById(R.id.three);
        Button fourButton = (Button) findViewById(R.id.four);
        Button fiveButton = (Button) findViewById(R.id.five);
        Button sixButton = (Button) findViewById(R.id.six);
        Button sevenButton = (Button) findViewById(R.id.seven);
        Button eightButton = (Button) findViewById(R.id.eight);
        Button nineButton = (Button) findViewById(R.id.nine);

        Button equalButton = (Button) findViewById(R.id.equals);
        Button powerButton = (Button) findViewById(R.id.power);
        Button mulButton = (Button) findViewById(R.id.multiply);
        Button minusButton = (Button) findViewById(R.id.minus);
        Button plusButton = (Button) findViewById(R.id.plus);
        Button moduloButton = (Button) findViewById(R.id.modulo);
        Button divideButton = (Button) findViewById(R.id.divide);

        Button negButton = (Button) findViewById(R.id.neg);
        Button dotButton = (Button) findViewById(R.id.dot);
        Button clearButton = (Button) findViewById(R.id.clear);


        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = input.getText().toString();
                String temp = input.getText().toString();
                for (int i = 0; i < temp.length(); i++) {
                    if (temp.charAt(i) == '.') {
                        return;

                    }


                }
                input.append(".");
            }
        });

        negButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = input.getText().toString();
                if (input.length() == 0) {
                    input.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        input.setText("");
                    } catch (NumberFormatException e) {
                        input.setText("");
                    }
                }

            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                input.append(b.getText().toString());

            }
        };
        zeroButton.setOnClickListener(listener);
        oneButton.setOnClickListener(listener);
        twoButton.setOnClickListener(listener);
        threeButton.setOnClickListener(listener);
        fourButton.setOnClickListener(listener);
        fiveButton.setOnClickListener(listener);
        sixButton.setOnClickListener(listener);
        sevenButton.setOnClickListener(listener);
        eightButton.setOnClickListener(listener);
        nineButton.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = input.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    result.setText("");
                }
                pendingOperation = op;
                opText.setText(pendingOperation);
            }
        };
        equalButton.setOnClickListener(opListener);
        mulButton.setOnClickListener(opListener);
        minusButton.setOnClickListener(opListener);
        plusButton.setOnClickListener(opListener);
        powerButton.setOnClickListener(opListener);
        moduloButton.setOnClickListener(opListener);
        powerButton.setOnClickListener(opListener);
        divideButton.setOnClickListener(opListener);

        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = input.getText().toString();
                String op = opText.getText().toString();
                operand1 = null;
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);

                } catch (NumberFormatException e) {
                    result.setText("");
                }
                opText.setText(pendingOperation);

            }
        };
        clearButton.setOnClickListener(clearListener);


    }

    private void performOperation(Double value, String operator) {
        if (null == operand1) {
            operand1 = value;
        } else {
            operand2 = value;
            if (pendingOperation.equals("=")) {
                pendingOperation = operator;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "/":
                    if (operand2 == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= operand2;
                    }
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;

                case "+":
                    operand1 += operand2;
                    break;
                case "%":
                    operand1 %= operand2;
                    break;
                case "^":
                    operand1 = Math.pow(operand1, operand2);
                    break;
            }
        }

        result.setText(operand1.toString());
        input.setText("");
    }


}
