package io.sfrei.tracksearch_web.services;

import io.sfrei.tracksearch.clients.MultiTrackSearchClient;
import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import io.sfrei.tracksearch_web.entities.PersistentTrack;
import io.sfrei.tracksearch_web.entities.TrackContainer;
import io.sfrei.tracksearch_web.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackSearchService {

    private final MultiTrackSearchClient trackSearchClient;

    private final PersistenceService persistenceService;

    public TrackContainer getTracksForSearch(String search) throws TrackSearchException {
        TrackList<Track> trackList = trackSearchClient.getTracksForSearch(search);
        return persistenceService.saveTrackContainer(trackList);
    }

    public TrackContainer getTrackContainerById(long trackContainerId) throws EntityNotFoundException {
        return persistenceService.getTrackContainerById(trackContainerId);
    }

    public PersistentTrack getPersistentTrackById(long persistentTrackId) throws EntityNotFoundException {
        return persistenceService.getPersistentTrackById(persistentTrackId);
    }

    public String getStreamUrl(long persistentTrackId) throws EntityNotFoundException, TrackSearchException {
        PersistentTrack persistentTrack = persistenceService.getPersistentTrackById(persistentTrackId);
        Track track = persistenceService.deserializeTrack(persistentTrack);
        return trackSearchClient.getStreamUrl(track);
    }

}
