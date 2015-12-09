package android.com.smartgen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

public class Questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buildQuestions();
    }

    private void buildQuestions() {
        LinearLayout questionsLayout = (LinearLayout) findViewById(R.id.questions_layout);

        TableRow.LayoutParams params_text = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        params_text.setMargins(0, 50, 0, 0);
        TableRow.LayoutParams params_input = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        params_input.setMargins(0, 0, 0, 0);

        int i = 0;

        for ( final Map.Entry<String, String> question : Utils.questions.entrySet()) {
            TableRow row_question = new TableRow(this);
            row_question.setLayoutParams(params_text);

            TextView question_text = new TextView(this);
            question_text.setText(Html.fromHtml("<b>" + (i + 1) + ".</b> " + question.getKey()));
            question_text.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);
            row_question.addView(question_text);

            TableRow row_answer = new TableRow(this);
            row_answer.setLayoutParams(params_input);

            ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
            if(!question.getValue().isEmpty())
                progress.setProgress((int) (progress.getProgress() + new Float(100) / Utils.questions.size()));

            final EditText input_answer = new EditText(this);
            input_answer.setLines(1);
            input_answer.setSingleLine(true);
            input_answer.setText(question.getValue());
            input_answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);

                    if (question.getValue().isEmpty() && !String.valueOf(s).isEmpty())
                        progress.setProgress((int) (progress.getProgress() + new Float(100) / Utils.questions.size()));
                    else if (!question.getValue().isEmpty() && String.valueOf(s).isEmpty())
                        progress.setProgress((int) (progress.getProgress() - new Float(100) / Utils.questions.size()));

                    question.setValue(String.valueOf(s));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            row_answer.addView(input_answer);

            questionsLayout.addView(row_question, 2 * i);
            questionsLayout.addView(row_answer, 2 * i + 1);

            i++;

        }

        Button save = (Button) findViewById(R.id.saveQuestionsButton);
        save.setVisibility(View.VISIBLE);

    }

    public void saveAnswers(View view) {
        Utils.saveAnswersToStorage();

        Intent myIntent = new Intent(Questionnaire.this, GeneratePassword.class);
        Questionnaire.this.startActivity(myIntent);
    }

}