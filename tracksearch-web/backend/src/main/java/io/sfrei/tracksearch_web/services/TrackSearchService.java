package io.sfrei.tracksearch_web.services;

import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import io.sfrei.tracksearch_web.clients.TrackSearchClient;
import io.sfrei.tracksearch_web.entities.TracksContainer;
import io.sfrei.tracksearch_web.exceptions.EntityNotFoundException;
import io.sfrei.tracksearch_web.presistence.TracksContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackSearchService {

    private final TrackSearchClient trackSearchClient;

    private final TracksContainerService tracksContainerService;

    public TracksContainer getTracksForSearch(String search) throws TrackSearchException {
        TrackList<Track> trackList = trackSearchClient.getTracksForSearch(search);
         return tracksContainerService.save(trackList);
    }

    public TracksContainer getById(Long id) throws EntityNotFoundException {
        return tracksContainerService.get(id);
    }

}
