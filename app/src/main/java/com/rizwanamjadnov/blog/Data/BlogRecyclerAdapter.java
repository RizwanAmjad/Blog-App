package com.rizwanamjadnov.blog.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizwanamjadnov.blog.Model.Blog;
import com.rizwanamjadnov.blog.R;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter(Context context, List<Blog> blogList){
        this.context = context;
        this.blogList = blogList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        String imageURL;
        holder.title.setText(blog.getTitle());
        holder.description.setText(blog.getDescription());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        holder.time.setText(dateFormat.format(new Date(Long.parseLong(blog.getTimeStamp())).getTime()));
        imageURL = blog.getImage();
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView title;
        public TextView time;
        public TextView description;
        public String userId;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.postImageView);
            title = (TextView) itemView.findViewById(R.id.textViewPostTitle);
            time = (TextView) itemView.findViewById(R.id.textViewPostTime);
            title = (TextView) itemView.findViewById(R.id.textViewPostTitle);
            description = (TextView) itemView.findViewById(R.id.textViewPostDescription);
            userId = null;
        }
    }
}
