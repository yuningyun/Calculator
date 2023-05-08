package androidclass.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView formula;
    TextView number;

    boolean op = false; // 연산자 입력을 위한 변수
    private Stack<String> operatorStack; // 연산자를 위한 스택
    List<String> infix; // 중위 표기
    List<String> postfix; // 후위 표기
    List<String> memorylist; // 메모리 저장 리스트

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
                }
                number.append("."); break;
        }
    }

    public void addoperation(View v) {
        if(formula.getText() == null || op == true){
            op = false;

            if(number.getText().charAt(0) == '-' || number.getText().charAt(0) == '+') {
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
            number.setText("0");
        } else {
            if(formula.getText().length() - 1 == -1){
                Toast.makeText(getApplicationContext(), "연산자가 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (formula.getText().charAt(formula.getText().length() - 1) == '%') {
            } else {
                formula.setText(formula.getText().toString().substring(0, formula.getText().length() - 3));
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

    public void percent(View v) {
        formula.append(number.getText() + "%");
        number.setText("0");
        op = false;
    }

    public void equal(View v) {
        if (formula.length() == 0) return;

    }

    public void memoryset(View v) {
        memorylist.add(number.getText().toString());
    }

    public void memoryread(View v) {
        if(memorylist.size() == 0) return;
        number.setText(memorylist.get(memorylist.size()-1));
    }

    public void memoryclear(View v) {
        memorylist.clear();
    }

    public void memoryplus(View v) {
        double m_num = Double.parseDouble(memorylist.get(memorylist.size()-1));
        double n_num = Double.parseDouble(number.getText().toString());

        double result = m_num + n_num;
        memorylist.set(memorylist.size()-1, Double.toString(result));
    }

    public void memorysub(View v) {
        double m_num = Double.parseDouble(memorylist.get(memorylist.size()-1));
        double n_num = Double.parseDouble(number.getText().toString());

        double result = m_num - n_num;
        memorylist.set(memorylist.size()-1, Double.toString(result));
    }
}