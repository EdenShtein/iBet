package com.example.ibet.model.Group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ibet.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder>  {

    public static List<Group> groupsData = new LinkedList<Group>();
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Group getGroups(int position)
    {
        return groupsData.get(position);
    }

    public void setGamesData(List<Group> groups) {
        this.groupsData = groups;
        notifyDataSetChanged();
    }

    // Create GameHolder for the adapter.
    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        GroupHolder holder = new GroupHolder(view);
        return holder;
    }

    // Bind data to the adapter.
    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        Group currentGame = groupsData.get(position);
        holder.bindData(currentGame,position);
        holder.itemView.setTag(currentGame);
    }

    @Override
    public int getItemCount() {
        return groupsData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Group game, View view);
    }

    //---------------GameHolder----------------//

    public static class GroupHolder extends RecyclerView.ViewHolder{
        TextView groupText;
        TextView groupSubText;
        ImageView groupImage;
        int position;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            groupImage = itemView.findViewById(R.id.listrow_group_image);
            groupText = itemView.findViewById(R.id.listrow_group_text);
            groupSubText = itemView.findViewById(R.id.listrow_group_sub_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(groupsData.get(position), v);
                    }
                }
            });
        }

        public void bindData(Group group, int position){
//            gameText.setText(group.getName());
//            gameSubText.setText(game.getPrice());
//            gameImage.setImageResource(R.drawable.gamechangersimple);
//            if (game.getImageURL() != null) {
//                Picasso.get().load(game.getImageURL()).placeholder(R.drawable.gamechangersimple).into(gameImage);
//            }

            this.position = position;
        }
    }

}
