package com.example.alonesns;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alonesns.Model.MainModel;
import com.example.alonesns.Room.RoomDB;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<MainModel> items;
    private Activity activityContext;
    private RoomDB roomDB;

    Context context;

    public ItemAdapter(Activity activityContext, List<MainModel> items) {
        this.activityContext = activityContext;
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = (LayoutInflater.from(viewGroup.getContext())).inflate(R.layout.card_item, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        MainModel item = items.get(position);
        roomDB = RoomDB.getInstance(activityContext);

        holder.dateTv.setText(item.getDate());

        String picturePath = item.getPicture();
        holder.imageView.setImageURI(Uri.parse("file://" + picturePath));

        holder.contentTv.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView dateTv, contentTv;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.dateTv);
            imageView = itemView.findViewById(R.id.imageView);
            contentTv = itemView.findViewById(R.id.contentTv);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제하기");
                    builder.setMessage("선택한 게시물을 정말로 삭제하시겠습니까 ?");
                    builder.setIcon(R.drawable.delete);
                    builder.setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteItem(position);
                            Toast.makeText(context, "삭제되었습니다. ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }

    public void deleteItem(int position) {
        MainModel item = items.get(position);
        roomDB.mainDao().delete(item);

        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }
}
