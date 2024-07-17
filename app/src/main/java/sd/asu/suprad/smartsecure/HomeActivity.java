package sd.asu.suprad.smartsecure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.home_recycler_view);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Living Room", true, true, true, true, 72));
        rooms.add(new Room("Bed Room", true, true, true, true, 73));
        rooms.add(new Room("Master Bed Room", true, true, true, true, 71));
        rooms.add(new Room("Kitchen Room", false, false, true, true, 70));
        rooms.add(new Room("Bath Room 1", true, true, true, true, 73));
        rooms.add(new Room("Bath Room 2", false, true, false, false, 75));
        rooms.add(new Room("Balcony", true, true, true, true, 72));
        rooms.add(new Room("Attic", false,true, true, false, 69));
        rooms.add(new Room("Basement", true, true, true, true, 71));

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new HomeElementAdapter(HomeActivity.this, rooms));
    }
}