package android.com.smartgen;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.RandomStringUtils;

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
    }

    private void reloadQuestionnaire() {
        Intent myIntent = new Intent(GeneratePassword.this, Questionnaire.class);
        GeneratePassword.this.startActivity(myIntent);
    }

    public void generatePassword(View v) {
        EditText pass = (EditText) findViewById(R.id.passwordField);
        String gen_pass = RandomStringUtils.randomAlphanumeric(10);
        pass.setText(gen_pass);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
