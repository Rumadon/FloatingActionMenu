package test.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ianto.solutions.floatingactionmenu.FloatingActionMenu;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionMenu fab = findViewById(R.id.activity_fab);
        fab.addMenuItem(R.drawable.ic_build, null, null, "Build", (v) -> {
            Log.d("Yo", "Build");
            return Unit.INSTANCE;
        });
        fab.addMenuItem(R.drawable.ic_code, null, null, "Code", (v) -> {
            Log.d("Yo", "Code");
            return Unit.INSTANCE;
        });
        fab.addMenuItem(R.drawable.ic_create_black_24dp, null, null, "Create", (v) -> {
            Log.d("Yo", "Create");
            return Unit.INSTANCE;
        });
    }
}
