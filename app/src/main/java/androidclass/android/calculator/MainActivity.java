package androidclass.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView formula;
    TextView number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        formula = (TextView) findViewById(R.id.formula);
        number = (TextView) findViewById(R.id.number);

    }

    public void addnum(View v) {
        if(number.getText().toString().equals("0")){
            number.setText("");
        }
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

    public void clearentry(View v) {
        number.setText("0");
    }


}