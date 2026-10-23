package com.example.deepseekpow;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tvStatus);
        tv.postDelayed(() -> tv.setText("版本: 2.0 终极版\n注入目标: DeepSeek\n运行状态: ✅ 模块已就绪"), 800);
    }
}
