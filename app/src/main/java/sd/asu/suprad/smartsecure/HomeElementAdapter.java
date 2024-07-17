package sd.asu.suprad.smartsecure;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeElementAdapter extends RecyclerView.Adapter<HomeElementAdapter.HomeElementViewholder> {

    ArrayList<Room> rooms;
    Context context;

    public HomeElementAdapter(Context context, ArrayList<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
        Log.e("CHECK", ""+rooms.size());
    }

    public static class HomeElementViewholder extends RecyclerView.ViewHolder {

        TextView roomName;
        TextView status;
        public HomeElementViewholder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.room_name);
            status = itemView.findViewById(R.id.statusTextView);
        }
    }

    @NonNull
    @Override
    public HomeElementViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeElementViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeElementViewholder holder, int position) {
        Room room = rooms.get(position);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RoomActivity.class);
            intent.putExtra("room", rooms.get(position));
            context.startActivity(intent);
        });
        holder.roomName.setText(room.getRoomName());
        holder.status.setCompoundDrawablesWithIntrinsicBounds(0, 0, room.isArmed() ? R.drawable.baseline_circle_24 : R.drawable.status_red, 0);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
