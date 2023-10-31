package com.example.giaodienchinh_doan.AdapterView;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodienchinh_doan.Model.ChatMessage;
import com.example.giaodienchinh_doan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ChatMessage> chatMessages;
    private final String senderId;
    private final String ReceiverId;
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(List<ChatMessage> chatMessages, String senderId,String Receiver) {
        this.chatMessages = chatMessages;
        this.senderId = senderId;
        this.ReceiverId=Receiver;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_receive_mess, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            SentMessageViewHolder sentViewHolder = (SentMessageViewHolder) holder;
            sentViewHolder.textMessage.setText(chatMessage.message);
            sentViewHolder.textDateTime.setText(getReadableDateTime(chatMessage.dateTime));
        } else {
            ReceivedMessageViewHolder receivedViewHolder = (ReceivedMessageViewHolder) holder;
            receivedViewHolder.textMessage.setText(chatMessage.message);
            receivedViewHolder.textDateTime.setText(getReadableDateTime(chatMessage.dateTime));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).senderId.equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage, textDateTime;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessageSent);
            textDateTime = itemView.findViewById(R.id.textDateTimeSent);
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage, textDateTime;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessageReceive);
            textDateTime = itemView.findViewById(R.id.textDateTimeReceive);
        }
    }

    public String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMM dd, yyy - hh:mm a", Locale.getDefault()).format(date);
    }
}