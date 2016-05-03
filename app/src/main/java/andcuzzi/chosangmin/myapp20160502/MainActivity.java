package andcuzzi.chosangmin.myapp20160502;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickMethod(View v)
    {
        switch (v.getId())
        {
            case R.id.btnOK:
                this.onClickConnectionDB();
                break;

            case R.id.btnCipher:
                this.onClickCipher();
                break;

            default:
                break;
        }
    }

    private void onClickConnectionDB()
    {
        try
        {
            EditText txtDBAddr = (EditText) findViewById(R.id.txtDBAddr);
            EditText txtDBName = (EditText) findViewById(R.id.txtDBName);
            EditText txtID = (EditText) findViewById(R.id.txtID);
            EditText txtPW = (EditText) findViewById(R.id.txtPW);

            Editable strDBAddr = txtDBAddr.getText();
            Editable strDBName = txtDBName.getText();
            Editable strID = txtID.getText();
            Editable strPW = txtPW.getText();

            // DB 직접 접근 안됨 -> php 우회
            String link = String.format("http://" + strDBAddr.toString() + "/getdata.php", strDBAddr.toString());

            String data  = URLEncoder.encode("dbid",   "UTF-8") + "=" + URLEncoder.encode(strID.toString(),     "UTF-8");
            data += "&"  + URLEncoder.encode("dbpw",   "UTF-8") + "=" + URLEncoder.encode(strPW.toString(),     "UTF-8");
            data += "&"  + URLEncoder.encode("dbname", "UTF-8") + "=" + URLEncoder.encode(strDBName.toString(), "UTF-8");
            data += "&"  + URLEncoder.encode("table",  "UTF-8") + "=" + URLEncoder.encode("TestUser", "UTF-8");

            // networkOnMainThreadException, Manifest의 permission

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build()); // 임시방편 -> 정석: (Thread-handler)

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }

            // Intent 통하여 result를 ResultView에 전달
            Intent intent = new Intent(this, ResultView.class);
            intent.putExtra("result", sb.toString());

            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickCipher()
    {
        try
        {
            EditText txtPW = (EditText) findViewById(R.id.txtPW);
            Editable strPW = txtPW.getText();

            if (strPW.length() < 1)
            {
                throw new Exception("값을 입력해 주십시오.");
            }

            // Intent 통하여 result를 ResultView에 전달
            Intent intent = new Intent(this, CipherView.class);
            intent.putExtra("password", strPW.toString());

            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
