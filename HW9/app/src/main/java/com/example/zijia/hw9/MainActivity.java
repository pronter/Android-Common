package com.example.zijia.hw9;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends AppCompatActivity {

    private Button button, button1;
    private EditText createText, checkText;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private static final int UPDATE = 0;
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE:
                    progressDialog.dismiss();
                    byte[] data = Base64.decode((message.obj.toString()).getBytes(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Matrix matrix = new Matrix();
                    matrix.postScale(10, 10);
                    Bitmap newbit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    BitmapDrawable drawable = new BitmapDrawable(newbit);
                    imageView.setImageDrawable(drawable);
                    break;
                default:
                    break;
            }
            super.handleMessage(message);
        }
    };

    public class DownLoad implements Runnable {
        private final String NAMESPACE = "http://WebXml.com.cn/";
        private final String METHODNAME = "enValidateByte";
        private final String SOAPACTION = "http://WebXml.com.cn/enValidateByte";
        private final String URL = "http://webservice.webxml.com.cn/WebServices/ValidateCodeWebService.asmx";
        public DownLoad() {}
        public void run() {
            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);
            String str = createText.getText().toString();
            Log.e("str", str);
            request.addProperty("byString", str);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transportSE = new HttpTransportSE(URL);
            try {
                transportSE.call(SOAPACTION, envelope);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("fault", envelope.bodyIn.toString());
            SoapObject result = (SoapObject)envelope.bodyIn;
            SoapPrimitive detail = (SoapPrimitive)result.getProperty("enValidateByteResult");
            Message msg = new Message();
            msg.obj = detail;
            msg.what = UPDATE;
            handler.sendMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        button1 = (Button)findViewById(R.id.button2);
        createText = (EditText)findViewById(R.id.createText);
        checkText = (EditText)findViewById(R.id.checkText);
        imageView = (ImageView)findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(MainActivity.this, "Requesting...", "Please requesting...", true, false);
                DownLoad download = new DownLoad();
                new Thread(download).start();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkText.getText().toString().equals(createText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "正确！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "错误！请重新输入", Toast.LENGTH_SHORT).show();
                    checkText.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
