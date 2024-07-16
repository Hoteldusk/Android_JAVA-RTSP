package com.gcsmagma.rtsp_java;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class CCTVActivity extends Activity {
    private ExoPlayer player;
    private StyledPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        playerView = findViewById(R.id.player_view);
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        String streamUrl = getIntent().getStringExtra("STREAM_URL");
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(streamUrl));

        RtspMediaSource.Factory mediaSourceFactory = new RtspMediaSource.Factory();
        RtspMediaSource mediaSource = mediaSourceFactory.createMediaSource(mediaItem);

        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);

        // 버튼
        Button homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
