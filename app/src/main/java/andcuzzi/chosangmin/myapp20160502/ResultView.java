package andcuzzi.chosangmin.myapp20160502;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
* Created by chosangmin on 16. 5. 3..
        */

public class ResultView extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");

        TextView txtResult = (TextView)findViewById(R.id.txtResult);
        txtResult.setText(result);
    }

    public void backClickMethod(View v)
    {
        switch (v.getId())
        {
            case R.id.btnBack:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }
}