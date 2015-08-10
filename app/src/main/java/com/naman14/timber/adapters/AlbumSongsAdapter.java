package com.naman14.timber.adapters;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naman14.timber.MusicPlayer;
import com.naman14.timber.R;
import com.naman14.timber.models.Song;
import com.naman14.timber.utils.NavigationUtils;
import com.naman14.timber.utils.TimberUtils;

import java.util.List;

/**
 * Created by naman on 23/07/15.
 */
public class AlbumSongsAdapter extends RecyclerView.Adapter<AlbumSongsAdapter.ItemHolder> {

    private List<Song> arraylist;
    private Activity mContext;
    private long albumID;
    private long[] songIDs;

    public AlbumSongsAdapter(Activity context, List<Song> arraylist,long albumID) {
        this.arraylist = arraylist;
        this.mContext = context;
        this.songIDs=getSongIds();
        this.albumID=albumID;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_album_song, null);
        ItemHolder ml = new ItemHolder(v);
            return ml;


    }

    @Override
    public void onBindViewHolder(ItemHolder itemHolder, int i) {

            Song localItem = arraylist.get(i);

            itemHolder.title.setText(localItem.title);
            itemHolder.duration.setText(TimberUtils.makeShortTimeString(mContext, (localItem.duration) / 1000));
            int tracknumber = localItem.trackNumber;
            if (tracknumber == 0) {
                itemHolder.trackNumber.setText("-");
            } else if (tracknumber > 9) {
                String number = String.valueOf(tracknumber);
                itemHolder.trackNumber.setText(number.substring(number.length() - 1, number.length()));
            } else itemHolder.trackNumber.setText(String.valueOf(tracknumber));


    }

    @Override
    public int getItemCount() {
        return (null != arraylist ? arraylist.size() : 0);
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView title,duration,trackNumber;

        public ItemHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.song_title);
            this.duration = (TextView) view.findViewById(R.id.song_duration);
            this.trackNumber=(TextView) view.findViewById(R.id.trackNumber);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MusicPlayer.playAll(mContext, songIDs, getAdapterPosition(),albumID , TimberUtils.IdType.Album, false);
                    NavigationUtils.navigateToNowplaying(mContext, true);
                }
            },100);

        }

    }

    public long[] getSongIds() {
        long[] ret = new long[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            ret[i] = arraylist.get(i).id;
        }

        return ret;
    }

}



