package android.com.smartgen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by snape on 09.12.15.
 */
public class PasswordStorage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwords_storage);

        Utils.loadPasswordsFromStorage();
        loadPasswords();
    }

    private void loadPasswords() {
        LinearLayout passwordsLayout = (LinearLayout) findViewById(R.id.passwords_layout);

        TableRow.LayoutParams params_label = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        params_label.setMargins(0, 50, 0, 0);
        TableRow.LayoutParams params_pass = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        params_pass.setMargins(0, 10, 0, 0);

        if(Utils.passwords.isEmpty()) {
            TableRow row_label = new TableRow(this);
            row_label.setLayoutParams(params_label);

            TextView label = new TextView(this);
            label.setText("На данный момент в базе нет паролей");
            label.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);
            passwordsLayout.addView(label);

            return;
        }

        for ( final Map.Entry<String, String> password : Utils.passwords.entrySet()) {
            TableRow row_label = new TableRow(this);
            row_label.setLayoutParams(params_label);

            TextView label = new TextView(this);
            label.setText(password.getValue());
            label.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);
            passwordsLayout.addView(label);

            TableRow row_pass = new TableRow(this);
            row_pass.setLayoutParams(params_pass);

            TextView pass = new TextView(this);
            pass.setText(password.getKey());
            pass.setTextSize(TypedValue.COMPLEX_UNIT_PT, 7);
            passwordsLayout.addView(pass);
        }
    }
}
