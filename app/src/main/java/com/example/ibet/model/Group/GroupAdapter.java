package com.example.ibet.model.Group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ibet.R;
import com.squareup.picasso.Picasso;

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

    public void setGroupsData(List<Group> groups) {
        this.groupsData = groups;
        notifyDataSetChanged();
    }

    // Create GroupHolder for the adapter.
    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_container, parent, false);
        GroupHolder holder = new GroupHolder(view);
        return holder;
    }

    // Bind data to the adapter.
    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        Group currentGroup = groupsData.get(position);
        holder.bindData(currentGroup,position);
        holder.itemView.setTag(currentGroup);
    }

    @Override
    public int getItemCount() {
        return groupsData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Group group, View view);
    }

    //---------------GroupHolder----------------//

    public static class GroupHolder extends RecyclerView.ViewHolder{
        TextView groupText;
        TextView groupSubText;
        ImageView groupImage;
        int position;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            groupImage = itemView.findViewById(R.id.container_group_image);
            groupText = itemView.findViewById(R.id.container_group_text);
            groupSubText = itemView.findViewById(R.id.container_group_sub_text);

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
            groupText.setText(group.getName());
            groupSubText.setText(group.getCurrentUser().getScore());
            groupImage.setImageResource(R.drawable.nbalogo);
           /* if (group.getGroupLogo() != null) {
                Picasso.get().load(group.getGroupLogo()).placeholder(R.drawable.brplayer).into(groupImage);
            }*/

            this.position = position;
        }
    }

}
