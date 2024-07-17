package sd.asu.suprad.smartsecure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RoomActivity extends AppCompatActivity {

    public void sendNotification(String room) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Alerts")
                .setSmallIcon(R.drawable.logo_dalle)
                .setContentTitle("Intruder Alert")
                .setContentText("Motion has been detected in the " + room + ". Please Check the intrusion")
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        String channelId = "Alerts";
        NotificationChannel channel = new NotificationChannel(channelId, "Alerts of Intrusion", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        builder.setChannelId(channelId);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify("Alerts", 1, builder.build());
    }

    TextView roomNameTextView, motionSensorTextView, windowSensorTextView, doorSensorTextView, temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        roomNameTextView = findViewById(R.id.bedroom_Textview);
        motionSensorTextView = findViewById(R.id.motion_sensor);
        windowSensorTextView = findViewById(R.id.window_sensor);
        doorSensorTextView = findViewById(R.id.door_sensor);
        temperatureTextView = findViewById(R.id.temperature_room);

        Room room = (Room) getIntent().getSerializableExtra("room");
        assert room != null;

//        sendNotification("");

        roomNameTextView.setText(room.getRoomName());
        motionSensorTextView.setCompoundDrawablesWithIntrinsicBounds(room.isMotionSensor() ? R.drawable.baseline_circle_24 : R.drawable.status_red, 0, 0, 0);
        windowSensorTextView.setCompoundDrawablesWithIntrinsicBounds(room.isWindowSensor() ? R.drawable.baseline_circle_24 : R.drawable.status_red, 0, 0, 0);
        doorSensorTextView.setCompoundDrawablesWithIntrinsicBounds(room.isDoorSensor() ? R.drawable.baseline_circle_24 : R.drawable.status_red, 0, 0, 0);
        temperatureTextView.setText("Temperature: " + room.getTemperature() + "°F");

        temperatureTextView.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(RoomActivity.this, LoginActivity.class));
        });

        if (room.getRoomName().equals("Balcony")) {
            motionSensorTextView.setOnClickListener(view -> {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Alerts")
                        .setSmallIcon(R.drawable.logo_dalle)
                        .setContentTitle("Intruder Alert")
                        .setContentText("Motion has been detected in the living room. Please Check the intrusion")
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                String channelId = "Alerts";
                NotificationChannel channel = new NotificationChannel(channelId, "Alerts of Intrusion", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(channelId);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                notificationManager.notify("Alerts", 1, builder.build());
            });
        }
    }
}