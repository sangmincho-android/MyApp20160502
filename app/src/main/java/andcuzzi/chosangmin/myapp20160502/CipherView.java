package andcuzzi.chosangmin.myapp20160502;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by chosangmin on 16. 5. 3..
 */
public class CipherView extends Activity
{
    private String keyStr = "this is a key.";
    private byte[] key = null;
    private byte[] encoded = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cipher_view);

        this.generateKey();

        Intent intent = getIntent();
        String password = intent.getStringExtra("password");

        TextView txtOrigin = (TextView)findViewById(R.id.txtCipherOrigin);
        txtOrigin.setText(password);
    }

    public void cipherClickMethod(View v)
    {
        switch (v.getId())
        {
            case R.id.btnEncode:
                this.clickEncode();
                break;

            case R.id.btnDecode:
                this.clickDecode();
                break;

            case R.id.btnCipherBack:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    private void clickEncode()
    {
        TextView txtOrigin  = (TextView)findViewById(R.id.txtCipherOrigin);
        TextView txtEncoded = (TextView)findViewById(R.id.txtCipherEncoded);

        try
        {
            encoded = this.encrypt(key, txtOrigin.getText().toString().getBytes());

            txtEncoded.setText(new String(encoded));
        }
        catch (Exception ex)
        {
        }
    }

    private void clickDecode()
    {
        TextView txtEncoded = (TextView)findViewById(R.id.txtCipherEncoded);
        TextView txtDecoded = (TextView)findViewById(R.id.txtCipherDecoded);

        try
        {
            byte[] result = this.decrypt(key, encoded);

            txtDecoded.setText(new String(result));
        }
        catch (Exception ex)
        {
        }
    }

    private void generateKey()
    {
        try
        {
            byte[] keyStart = keyStr.getBytes();

            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(keyStart);

            kgen.init(128, sr); // 192 and 256 bits may not be available

            SecretKey skey = kgen.generateKey();
            key = skey.getEncoded();
        }
        catch (Exception ex)
        {
        }
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] encrypted = cipher.doFinal(clear);

        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

        byte[] decrypted = cipher.doFinal(encrypted);

        return decrypted;
    }
}
