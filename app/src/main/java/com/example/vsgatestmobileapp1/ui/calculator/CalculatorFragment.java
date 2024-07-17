package com.example.vsgatestmobileapp1.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.databinding.FragmentCalculatorBinding;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText display = binding.editTextCalculatorDisplay;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();
                String currentDisplay = display.getText().toString();

                int id = button.getId();
                if (id == R.id.buttonClear) {
                    display.setText("");
                } else if (id == R.id.buttonBackspace) {
                    if (currentDisplay.length() > 0) {
                        display.setText(currentDisplay.substring(0, currentDisplay.length() - 1));
                    }
                } else if (id == R.id.buttonEquals) {
                    display.setText(evaluateExpression(currentDisplay));
                } else {
                    display.setText(currentDisplay + buttonText);
                }
            }
        };

        // Attach the listener to all the buttons
        binding.buttonClear.setOnClickListener(listener);
        binding.buttonDivide.setOnClickListener(listener);
        binding.buttonMultiply.setOnClickListener(listener);
        binding.buttonBackspace.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonSubtract.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.buttonAdd.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.buttonEquals.setOnClickListener(listener);
        binding.button0.setOnClickListener(listener);
        binding.buttonDot.setOnClickListener(listener);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String evaluateExpression(String expression) {
        try {
            Expression exp = new ExpressionBuilder(expression).build();
            double result = exp.evaluate();
            return Double.toString(result);
        } catch (Exception e) {
            return "Error";
        }
    }
}
