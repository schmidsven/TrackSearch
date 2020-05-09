package io.sfrei.tracksearch_web.clients;

import io.sfrei.tracksearch.clients.MultiTrackSearchClient;
import io.sfrei.tracksearch.clients.setup.TrackSource;
import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TrackSearchClient implements MultiTrackSearchClient{
    
    private final MultiTrackSearchClient trackSearchClient;

    @Override
    public TrackList<Track> getTracksForSearch(String search) throws TrackSearchException {
        return trackSearchClient.getTracksForSearch(search);
    }

    @Override
    public TrackList<Track> getTracksForSearch(String search, Set<TrackSource> sources) throws TrackSearchException {
        return trackSearchClient.getTracksForSearch(search, sources);
    }

    @Override
    public TrackList<Track> getNext(TrackList<? extends Track> trackList) throws TrackSearchException {
        return trackSearchClient.getNext(trackList);
    }

    @Override
    public String getStreamUrl(Track track) throws TrackSearchException {
        return trackSearchClient.getStreamUrl(track);
    }

    @Override
    public boolean hasPagingValues(TrackList<? extends Track> trackList) {
        return false;
    }

}
