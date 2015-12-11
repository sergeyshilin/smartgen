package android.com.smartgen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.commons.lang3.RandomStringUtils;
import org.w3c.dom.Text;

import java.util.Map;
import java.util.RandomAccess;

/**
 * Created by snape on 05.12.15.
 */
public class GeneratePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Utils.emptyAnswers())
            reloadQuestionnaire();

        setContentView(R.layout.generate_password);
        listenSeeker();
        listenCheckboxes();
    }

    private void listenCheckboxes() {
        CheckBox checkbox_digits = (CheckBox) findViewById(R.id.checkbox_digits);
        CheckBox checkbox_capital = (CheckBox) findViewById(R.id.checkbox_capital);
        CheckBox checkbox_lower = (CheckBox) findViewById(R.id.checkbox_lower);
        CheckBox checkbox_specials = (CheckBox) findViewById(R.id.checkbox_specials);
        CheckBox checkbox_personal = (CheckBox) findViewById(R.id.checkbox_person);

        checkbox_digits.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.ISSETDIGITS = isChecked;
            }
        });

        checkbox_capital.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.ISSETCAPITAL = isChecked;
            }
        });

        checkbox_lower.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.ISSETLOWER = isChecked;
            }
        });

        checkbox_specials.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.ISSETSPECIALS = isChecked;
            }
        });

        checkbox_personal.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.ISSETPERSONDATA = isChecked;
            }
        });
    }

    private void listenSeeker() {
        final SeekBar mySeekBar = ((SeekBar) findViewById(R.id.passLen));

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                TextView lenPassText = (TextView) findViewById(R.id.passLen_text);

                if (progress >= 0)
                    Utils.PASSLEN = 6;
                if (progress >= 10)
                    Utils.PASSLEN = 7;
                if (progress >= 20)
                    Utils.PASSLEN = 8;
                if (progress >= 30)
                    Utils.PASSLEN = 9;
                if (progress >= 40)
                    Utils.PASSLEN = 10;
                if (progress >= 50)
                    Utils.PASSLEN = 11;
                if (progress >= 60)
                    Utils.PASSLEN = 12;
                if (progress >= 70)
                    Utils.PASSLEN = 13;
                if (progress >= 80)
                    Utils.PASSLEN = 14;
                if (progress >= 90)
                    Utils.PASSLEN = 15;

                lenPassText.setText("Длина (" + Utils.PASSLEN + " символов)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void reloadQuestionnaire() {
        Intent myIntent = new Intent(GeneratePassword.this, Questionnaire.class);
        GeneratePassword.this.startActivity(myIntent);
    }

    public void generatePassword(View v) {
        EditText pass = (EditText) findViewById(R.id.passwordField);
        TextView hint = (TextView) findViewById(R.id.passHint);

        String gen_pass = Utils.generateHaveAllSymbolsPassword();

        if(Utils.ISSETPERSONDATA)
            gen_pass = Utils.generatePersonalDataPassword(gen_pass);

        pass.setText(gen_pass);
        if(Utils.ISSETPERSONDATA)
            hint.setText(Utils.HINT);
        else
            hint.setText("");
        Button savePassword = (Button) findViewById(R.id.savePasswordButton);
        savePassword.setVisibility(View.VISIBLE);

    }

    public void savePassword(View v) {
        final EditText passwordLabel = new EditText(this);
        passwordLabel.setId(R.id.savePasswordLabel);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить пароль");
        builder.setView(passwordLabel);

        builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText savePasswordLabel = (EditText) passwordLabel.findViewById(R.id.savePasswordLabel);
                if (!savePasswordLabel.getText().toString().isEmpty()) {
                    Utils.passwords.put(Utils.HINT, savePasswordLabel.getText().toString());
                    Utils.savePasswordToStorage();
                    openPasswordStorage();
                }
            }
        });
        builder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void openPasswordStorage() {
        Intent myIntent = new Intent(GeneratePassword.this, PasswordStorage.class);
        GeneratePassword.this.startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questionnaire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_questions:
                reloadQuestionnaire();
                return true;
            case R.id.action_passwords:
                openPasswordStorage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}