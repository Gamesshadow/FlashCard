package com.gameshadow.flashcard;

public interface RecyclerClickListener {
    void onItemRemoveClick(int position);
    void onItemClick(int position);
    void onItemLongClick(int position);
}