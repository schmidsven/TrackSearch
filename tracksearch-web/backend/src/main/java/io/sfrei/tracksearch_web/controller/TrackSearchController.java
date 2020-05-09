package io.sfrei.tracksearch_web.controller;

import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import io.sfrei.tracksearch_web.clients.TrackSearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrackSearchController {

    private final TrackSearchClient trackSearchClient;

    @GetMapping("/search")
    public TrackList<Track> getTracksForSearch(@RequestParam(name = "keyWords") String search) throws TrackSearchException {
        return trackSearchClient.getTracksForSearch(search);
    }

}
