package com.example.ibet.model.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ibet.R;
import java.util.LinkedList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    public static List<User> usersData = new LinkedList<User>();
    private static UserAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(UserAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public User getUsers(int position)
    {
        return usersData.get(position);
    }

    public void setUsersData(List<User> users) {
        this.usersData = users;
        notifyDataSetChanged();
    }

    // Create UserHolder for the adapter.
    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_details_list_row, parent, false);
        UserAdapter.UserHolder holder = new UserAdapter.UserHolder(view);
        return holder;
    }

    // Bind data to the adapter.
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {
        User currentUser = usersData.get(position);
        holder.bindData(currentUser,position);
        holder.itemView.setTag(currentUser);

    }

    @Override
    public int getItemCount() {
        return usersData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(User user, View view);
    }

    //---------------UserHolder----------------//

    public class UserHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView userRank;
        TextView userScore;

        int position;

        public UserHolder(@NonNull View itemView) {

            super(itemView);
            userName = itemView.findViewById(R.id.group_listrow_username);
            userRank = itemView.findViewById(R.id.group_listrow_rank);
            userScore = itemView.findViewById(R.id.group_listrow_score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(usersData.get(position), v);
                    }

                }
            });
        }

        public void bindData(User user, int position){
            userName.setText(user.getId());
            userRank.setText(user.getRank());
            userScore.setText(user.getScore());

            this.position = position;
        }
    }
}
