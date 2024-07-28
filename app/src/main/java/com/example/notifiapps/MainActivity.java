package com.example.notifiapps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private List<NotificationData> notifications;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the list of notifications and the random generator
        notifications = new ArrayList<>();
        random = new Random();

        // Add different types of notifications
        notifications.add(new NotificationData("WhatsApp Message", "You have a new message from John.", R.drawable.ic_whatsapp));
        notifications.add(new NotificationData("Facebook Notification", "Anna commented on your photo.", R.drawable.ic_facebook));
        notifications.add(new NotificationData("Battery Low", "Your battery is running low. Please charge your device.", R.drawable.ic_battery));
        notifications.add(new NotificationData("Alarm", "Wake up! It's time for your morning run.", R.drawable.ic_alarm));
        notifications.add(new NotificationData("Amazon Delivery", "Your package has been delivered.", R.drawable.ic_amazon));
        notifications.add(new NotificationData("Swiggy Order", "Your order is on the way.", R.drawable.ic_swiggy));
        notifications.add(new NotificationData("LinkedIn Message", "You have a new connection request.", R.drawable.ic_linkedin));

        // Set up the button to show a random notification
        findViewById(R.id.showNotificationButton).setOnClickListener(view -> {
            showRandomNotification();
        });
    }

    private void showRandomNotification() {
        // Get a random notification
        NotificationData randomNotification = notifications.get(random.nextInt(notifications.size()));

        // Inflate the custom notification layout
        LayoutInflater inflater = getLayoutInflater();
        View notificationView = inflater.inflate(R.layout.activity_notification, null);

        // Set title, message, and icon
        TextView titleView = notificationView.findViewById(R.id.notificationTitle);
        TextView messageView = notificationView.findViewById(R.id.notificationMessage);
        ImageView iconView = notificationView.findViewById(R.id.notificationIcon);

        titleView.setText(randomNotification.title);
        messageView.setText(randomNotification.message);
        titleView.setTypeface(Typeface.SERIF); // Change font to something like Times New Roman
        titleView.setTextSize(22); // Enlarge the size of the title text
        titleView.setTextColor(Color.BLACK); // Set title text color
        messageView.setTextColor(Color.DKGRAY); // Set message text color

        if (randomNotification.iconResId != 0) {
            iconView.setImageResource(randomNotification.iconResId);
            iconView.setVisibility(View.VISIBLE);
        }

        // Create a new LinearLayout to hold the notification view
        LinearLayout rootLayout = findViewById(R.id.rootLayout);
        notificationView.setBackgroundColor(Color.parseColor("#EFEFEF")); // Change background color
        rootLayout.addView(notificationView);

        // Set up any additional customizations or interactions
        notificationView.setOnClickListener(v -> {
            showResponseDialog(randomNotification.title);
        });
    }

    private void showResponseDialog(String notificationTitle) {
        // Create a dialog to respond to the notification
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Respond to " + notificationTitle);

        // Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Send", (dialog, which) -> {
            String response = input.getText().toString();
            Toast.makeText(MainActivity.this, "Response sent: " + response, Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Class to hold notification data
    private static class NotificationData {
        String title;
        String message;
        int iconResId;

        NotificationData(String title, String message, int iconResId) {
            this.title = title;
            this.message = message;
            this.iconResId = iconResId;
        }
    }
}

