package io.sfrei.tracksearch_web.controller;

import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch_web.entities.PersistentTrack;
import io.sfrei.tracksearch_web.entities.TrackContainer;
import io.sfrei.tracksearch_web.exceptions.EntityNotFoundException;
import io.sfrei.tracksearch_web.services.TrackSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrackSearchController {

    private final TrackSearchService trackSearchService;

    @GetMapping("/search")
    public TrackContainer getTracksForSearch(@RequestParam(name = "keyWords") String search)
            throws TrackSearchException {

        return trackSearchService.getTracksForSearch(search);
    }

    @GetMapping("/tracklist")
    public TrackContainer getTracklistById(@RequestParam(name = "id") long trackContainerId)
            throws EntityNotFoundException {

        return trackSearchService.getTrackContainerById(trackContainerId);
    }

    @GetMapping("/tracklist/next")
    public TrackContainer getNextTracksForTracklistId(@RequestParam(name = "id") long trackContainerId)
            throws EntityNotFoundException, TrackSearchException {

        return trackSearchService.getNext(trackContainerId);
    }

    @GetMapping("/track")
    public PersistentTrack getTrackById(@RequestParam(name = "id") long persistentTrackId)
            throws EntityNotFoundException {

        return trackSearchService.getPersistentTrackById(persistentTrackId);
    }

    @GetMapping("/track/stream")
    public String getStreamUrlForTrackId(@RequestParam(name = "id") long persistentTrackId)
            throws TrackSearchException, EntityNotFoundException {

        return trackSearchService.getStreamUrl(persistentTrackId);
    }

}
