package myapplication.automation.test.dragondlespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class SpaceDragons extends AppCompatActivity {
    private Intent intent;
    private Button play, scores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spacedragons);

        scores = (Button) findViewById(R.id.SCORES);
        scores.setText("Scores");

        play = (Button) findViewById(R.id.PLAY);
        play.setText("Start !");
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), GameActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
