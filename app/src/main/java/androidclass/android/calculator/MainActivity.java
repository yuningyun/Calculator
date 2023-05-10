package androidclass.android.calculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView formula;
    TextView number;

    boolean op = false; // 연산자 입력을 위한 변수
    boolean eqclick = false; // equals 눌렸는지 확인
    boolean spop = false; // %, sqrt, pow, 1/x 뒤에 연산자 있는지 없는지 확인
    private Stack<String> operatorStack; // 연산자를 위한 스택
    List<String> infix; // 중위 표기
    List<String> postfix; // 후위 표기
    List<String> memorylist; // 메모리 저장 리스트인

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formula = (TextView) findViewById(R.id.formula);
        number = (TextView) findViewById(R.id.number);

        operatorStack = new Stack<>();
        infix = new ArrayList<>();
        postfix = new ArrayList<>();
        memorylist = new ArrayList<>();
    }

    public void addnum(View v) {
        if(number.getText().toString().equals("0")){
            number.setText("");
        }
        op = true;
        switch(v.getId()){
            case R.id.num0:number.append("0"); break;
            case R.id.num1: number.append("1"); break;
            case R.id.num2: number.append("2"); break;
            case R.id.num3: number.append("3"); break;
            case R.id.num4: number.append("4"); break;
            case R.id.num5: number.append("5"); break;
            case R.id.num6: number.append("6"); break;
            case R.id.num7: number.append("7"); break;
            case R.id.num8: number.append("8"); break;
            case R.id.num9: number.append("9"); break;
            case R.id.point:
                if(number.getText().toString().equals("")){
                    number.append("0");
                } else if(number.getText().toString().contains(".")) {
                    Toast.makeText(getApplicationContext(), ".을 더 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
                number.append("."); break;
        }
    }

    public void addoperation(View v) {
        if(op == true){
            op = false;

            if(eqclick == true) {
                formula.setText("");
                if(number.getText().charAt(0) == '-' || number.getText().charAt(0) == '+') {
                    formula.append("(" + number.getText() + ")");
                } else { formula.setText(number.getText().toString()); }
                eqclick = false;
            } else if(spop == true) {
            } else if(number.getText().charAt(0) == '-' || number.getText().charAt(0) == '+') {
                formula.append("("+number.getText()+")");
            } else {
                formula.append(number.getText());
            }
            switch(v.getId()){
                case R.id.plus:
                    formula.append(" + ");
                    break;
                case R.id.sub:
                    formula.append(" - ");
                    break;
                case R.id.mul:
                    formula.append(" x ");
                    break;
                case R.id.div:
                    formula.append(" / ");
                    break;
            }
            if(spop == true) {
                spop = false;
                return;
            }
            number.setText("0");
        } else {
            if(formula.getText().length() - 1 == -1){
                Toast.makeText(getApplicationContext(), "연산자가 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            } else if (formula.getText().charAt(formula.getText().length() - 1) == ' ') {
                formula.setText(formula.getText().toString().substring(0, formula.getText().length() - 3));
            } else {
            }
            switch(v.getId()){
                case R.id.plus:
                    formula.append(" + ");
                    break;
                case R.id.sub:
                    formula.append(" - ");
                    break;
                case R.id.mul:
                    formula.append(" x ");
                    break;
                case R.id.div:
                    formula.append(" / ");
                    break;
            }
        }
    }

    public void clearentry(View v) {
        number.setText("0");
        op = false;
    }

    public void back(View v) {
        if(number.getText().length() - 1 == 0){
            number.setText("0");
            op = false;
        } else {
            number.setText(number.getText().toString().substring(0, number.getText().length() - 1));
        }
    }

    public void clear(View v) {
        formula.setText("");
        number.setText("0");
        op = false;
        spop = false;
        eqclick = false;
        infix.clear();
    }

    public void sign(View v) {
        if(number.getText().charAt(0) != '-' && number.getText().charAt(0) != '+'){
            number.setText("-"+number.getText());
        } else if(number.getText().charAt(0) == '-') {
            number.setText("+"+number.getText().toString().substring(1,number.getText().length()));
        } else {
            number.setText("-"+number.getText().toString().substring(1,number.getText().length()));
        }
    }

    // % 기호, sqrt(), 제곱, 1/x 등 기호 추가
    public void addspoperation(View v) {
        // 처음 넣는 수가 아니고 앞에 연산자가 없을 때 더하기 기호를 추가하고 뒤에 넣는다.
        if(eqclick == true) {
            formula.setText("");
            eqclick = false;
        } else if(!formula.getText().toString().isEmpty()){
            char in = formula.getText().toString().charAt(formula.getText().length()-2);
            if(in == '+' || in == '-' || in == 'x' || in == '/') {
            } else {
                formula.append(" + ");
            }
        }

        switch (v.getId()){
            case R.id.percent:
                formula.append(number.getText() + "%");
                break;
            case R.id.root:
                formula.append("sqrt(" + number.getText() + ")");
                break;
            case R.id.power:
                formula.append("pow(" + number.getText() + ")");
                break;
            case R.id.fraction:
                formula.append("1 / " + number.getText());
                break;
        }
        number.setText("0");
        op = false;
        spop = true;
    }


    public void equal(View v) {
        if (formula.length() == 0) return;
        if(op == false && number.getText().toString().equals("0")){
            char in = formula.getText().toString().charAt(formula.getText().length()-2);
            if(in == '+' || in == '-' || in == 'x' || in == '/') {
                Toast.makeText(getApplicationContext(), "계산을 할 수 없습니다. 숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            formula.append(number.getText().toString());
        }
        Collections.addAll(infix, formula.getText().toString().split(" "));
        result();
        eqclick = true;
    }

    // memory에 저장
    public void memoryset(View v) {
        memorylist.add(number.getText().toString());
    }

    // memory에 있는 가장 마지막에 저장한 값 읽어오기
    public void memoryread(View v) {
        if(memorylist.size() == 0) return;
        number.setText(memorylist.get(memorylist.size()-1));
    }

    // memory 내용 모두 지우기
    public void memoryclear(View v) {
        memorylist.clear();
    }

    // memory 마지막 값에 더하기
    public void memoryplus(View v) {
        if(memorylist.isEmpty()) {
            Toast.makeText(getApplicationContext(), "memory에 아무것도 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        double m_num = Double.parseDouble(memorylist.get(memorylist.size()-1));
        double n_num = Double.parseDouble(number.getText().toString());

        double result = m_num + n_num;
        memorylist.set(memorylist.size()-1, Double.toString(result));
    }

    // memory 마지막 값에 빼기
    public void memorysub(View v) {
        if(memorylist.isEmpty()) {
            Toast.makeText(getApplicationContext(), "memory에 아무것도 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        double m_num = Double.parseDouble(memorylist.get(memorylist.size()-1));
        double n_num = Double.parseDouble(number.getText().toString());

        double result;
        if (m_num >= 0 && n_num >= 0) {
            // 두 수 모두 양수인 경우
            if (m_num >= n_num) {
                result = m_num - n_num;
            } else {
                result = n_num - m_num;
                result = -result; // 결과가 음수일 때 부호를 변경
            }
        } else if (m_num < 0 && n_num < 0) {
            // 두 수 모두 음수인 경우
            result = -m_num - (-n_num); // 절댓값으로 계산 후 부호를 변경
        } else {
            // 두 수 중 하나가 음수인 경우
            result = m_num + n_num;
        }
        memorylist.set(memorylist.size()-1, Double.toString(result));
    }

    // memory에 저장한 값들을 모아 보여주고 memory값을 누르면 화면에 더해준다.
    public void memorylistcheck(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Memory list");

        builder.setItems(memorylist.toArray(new String[0]), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int inpos)
            {
                op = true;
                number.setText(memorylist.get(inpos));
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // 결과 계산을 위한 연산자 가중치
    int getweight(String operator) {
        int weight = 0;
        switch (operator) {
            case "x":
            case "/":
                weight = 3;
                break;
            case "+":
            case "-":
                weight = 1;
                break;
        }
        return weight;
    }

    // 특수문자 처리 ex) "(", ")", "%", "sqrt()", "
    void handlingchar() {
        for (int i = 0; i < infix.size(); i++) {
            if(infix.get(i).contains("%")) {
                String item = infix.get(i);
                item = item.substring(0, item.length()-1);
                Double it = Double.parseDouble(item);
                it = it * 0.01;
                item = Double.toString(it);
                infix.set(i, item);
            }
            if(infix.get(i).contains("sqrt(")){
                String item = infix.get(i);
                item = item.substring(5, item.length()-1);
                Double it = Double.parseDouble(item);
                it = Math.sqrt(it);
                item = Double.toString(it);
                infix.set(i, item);
            } else if (infix.get(i).contains("pow(")) {
                String item = infix.get(i);
                item = item.substring(4, item.length()-1);
                Double it = Double.parseDouble(item);
                it = Math.pow(it, 2);
                item = Double.toString(it);
                infix.set(i, item);
            } else if (infix.get(i).contains("(")) {
                String item = infix.get(i);
                item = item.substring(1, item.length() - 1);
                infix.set(i, item);
            }
        }
    }


    // 숫자 확인
    boolean isnumber(String str) {
        boolean result = true;
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    // 전위 -> 후위
    void infixTopostfix() {
        handlingchar();
        for (String item : infix) {
            // 피연산자
            if (isnumber(item)) postfix.add(String.valueOf(Double.parseDouble(item)));
                // 연산자
            else {
                if (operatorStack.isEmpty()) operatorStack.push(item);
                else {
                    if (getweight(operatorStack.peek()) >= getweight(item)) postfix.add(operatorStack.pop());
                    operatorStack.push(item);
                }
            }
        }
        while (!operatorStack.isEmpty()) postfix.add(operatorStack.pop());
    }

    // 계산하기
    String calculate(String num1, String num2, String op) {
        double first = Double.parseDouble(num1);
        double second = Double.parseDouble(num2);
        double result = 0.0;
        try {
            switch (op) {
                case "x": result = first * second;break;
                case "/": result = first / second;break;
                case "+": result = first + second;break;
                case "-": result = first - second;break;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "연산할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return String.valueOf(result);
    }

    // 결과값
    void result() {
        int i = 0;
        infixTopostfix();
        while (postfix.size() != 1) {
            if (!isnumber(postfix.get(i))) {
                postfix.add(i - 2, calculate(postfix.remove(i - 2), postfix.remove(i - 2), postfix.remove(i - 2)));
                i = -1;
            }
            i++;
        }
        number.setText(postfix.remove(0));
        infix.clear();
    }
}