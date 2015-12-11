package android.com.smartgen;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
        final LinearLayout passwordsLayout = (LinearLayout) findViewById(R.id.passwords_layout);

        TableRow.LayoutParams params_label = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        params_label.setMargins(0, 10, 0, 0);
        TableRow.LayoutParams params_pass = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params_pass.setMargins(0, 10, 0, 0);

        if(Utils.passwords.isEmpty()) {
            TableRow row_label = new TableRow(this);
            row_label.setLayoutParams(params_label);

            TextView label = new TextView(this);
            label.setText("На данный момент в базе нет паролей");
            label.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);

            row_label.addView(label);
            passwordsLayout.addView(row_label);

            return;
        }

        for ( final Map.Entry<String, String> password : Utils.passwords.entrySet()) {
            final TableRow row_label = new TableRow(this);
            row_label.setLayoutParams(params_label);

            TextView label = new TextView(this);
            label.setText(password.getValue());
            label.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);

            final TableRow row_pass = new TableRow(this);
            row_pass.setLayoutParams(params_pass);

            TextView pass = new TextView(this);
            pass.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
            pass.setText(password.getKey());
            pass.setTextSize(TypedValue.COMPLEX_UNIT_PT, 7);
            pass.setPadding(0, 0, 0, 30);

            Button delete = new Button(this);
            delete.setWidth(200);
            delete.setHeight(50);
            TableRow.LayoutParams button_params = new TableRow.LayoutParams(200, TableRow.LayoutParams.WRAP_CONTENT);
            button_params.setMargins(0, 35, 0, 0);
            delete.setLayoutParams(button_params);
            delete.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            delete.setBackgroundColor(getResources().getColor(R.color.colorDeleteButton));
            delete.setTextColor(getResources().getColor(R.color.colorMetal));
            delete.setText("Удалить");

            delete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Passwords size: " + Utils.passwords.size());
                    passwordsLayout.removeView(row_label);
                    passwordsLayout.removeView(row_pass);
                    Utils.passwords.remove(password.getKey());

                    Utils.savePasswordToStorage();

                    System.out.println("Passwords size: " + Utils.passwords.size());
                }
            });

            row_label.addView(label);
            row_pass.addView(pass);
            row_pass.addView(delete);
            passwordsLayout.addView(row_label);
            passwordsLayout.addView(row_pass);
        }
    }
}
