package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.models.Setting;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder>
{

    private List<Setting> settingsList;
    private Context context;

    public SettingAdapter(Context context, List<Setting> settings)
    {
        this.context  = context;
        this.settingsList = settings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Setting setting = settingsList.get(position);
        holder.bind(setting);
    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iconView;
        private TextView settingTitleTextView;
        private TextView settingDescriptionTextView;



        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            iconView    = itemView.findViewById(R.id.iconView);
            settingTitleTextView  = itemView.findViewById(R.id.settingstitleView);
            settingDescriptionTextView= itemView.findViewById(R.id.settingsdescriptionView);
        }

        public void bind (Setting setting)
        {
            iconView.setImageResource(setting.getIconResourceId());
            settingTitleTextView.setText(setting.getTitleName());
            settingDescriptionTextView.setText(String.valueOf(setting.getDescription()));
            int tintColor = ContextCompat.getColor(context, setting.getIconTint());
            iconView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        }
    }
}

