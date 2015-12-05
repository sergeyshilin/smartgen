package android.com.smartgen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import java.util.HashMap;
import java.util.Map;

public class Questionnaire extends AppCompatActivity {

    private Map<EditText, String> inputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateInputIds();
        listenInputs();
        listenButtons();
    }

    private void listenButtons() {
        Button prev = (Button) findViewById(R.id.button_to_1_from_2);
        Button next = (Button) findViewById(R.id.button_to_2_from_1);
        Button save = (Button) findViewById(R.id.saveQuestionsButton);

        prev.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                TableLayout table1 = (TableLayout) findViewById(R.id.table_1);
                TableLayout table2 = (TableLayout) findViewById(R.id.table_2);

                table2.setVisibility(View.GONE);
                table1.setVisibility(View.VISIBLE);
                table1.scrollTo(0, 0);
            }
        });

        next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout table1 = (TableLayout) findViewById(R.id.table_1);
                TableLayout table2 = (TableLayout) findViewById(R.id.table_2);

                table1.setVisibility(View.GONE);
                table2.setVisibility(View.VISIBLE);
                table2.scrollTo(0, 0);
//                AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
//                anim.setDuration(1000);
//                anim.setRepeatMode(Animation.REVERSE);
//                table1.startAnimation(anim);


            }
        });
    }

    public void saveAnswers(View view) {
        saveAnswersToStorage();

        Intent myIntent = new Intent(Questionnaire.this, GeneratePassword.class);
        Questionnaire.this.startActivity(myIntent);
    }

    private void saveAnswersToStorage() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for (final Map.Entry<EditText, String> input : inputs.entrySet())
            editor.putString(input.getKey().getText().toString(), input.getValue());

        editor.commit();
    }

    private void generateInputIds() {
        inputs = new HashMap<>();

        inputs.put((EditText) findViewById(R.id.answer_1), "");
        inputs.put((EditText) findViewById(R.id.answer_2), "");
        inputs.put((EditText) findViewById(R.id.answer_3), "");
        inputs.put((EditText) findViewById(R.id.answer_4), "");
        inputs.put((EditText) findViewById(R.id.answer_5), "");
        inputs.put((EditText) findViewById(R.id.answer_6), "");
        inputs.put((EditText) findViewById(R.id.answer_7), "");
        inputs.put((EditText) findViewById(R.id.answer_8), "");
        inputs.put((EditText) findViewById(R.id.answer_9), "");
        inputs.put((EditText) findViewById(R.id.answer_10), "");
    }

    private void listenInputs() {
        for (final Map.Entry<EditText, String> input : inputs.entrySet()) {
            input.getKey().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);

                    if(input.getValue().isEmpty() && !String.valueOf(s).isEmpty())
                        progress.setProgress((int) (progress.getProgress() + new Float(100) / inputs.size()));
                    else if (!input.getValue().isEmpty() && String.valueOf(s).isEmpty())
                        progress.setProgress((int) (progress.getProgress() - new Float(100) / inputs.size()));

                    input.setValue(String.valueOf(s));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questionnaire, menu);
        return true;
    }

}