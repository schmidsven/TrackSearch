package io.sfrei.tracksearch_web.controller;

import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch_web.entities.TracksContainer;
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
    public TracksContainer getTracksForSearch(@RequestParam(name = "keyWords") String search) throws TrackSearchException {
        return trackSearchService.getTracksForSearch(search);
    }

    @GetMapping("/tracklist")
    public TracksContainer getTracklistById(Long id) throws EntityNotFoundException {
        //TODO: Fix empty collections
        return trackSearchService.getById(id);
    }

}
