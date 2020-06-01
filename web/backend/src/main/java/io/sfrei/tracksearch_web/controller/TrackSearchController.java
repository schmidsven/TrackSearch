package io.sfrei.tracksearch_web.controller;

import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch.tracks.SoundCloudTrack;
import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import io.sfrei.tracksearch.tracks.YouTubeTrack;
import io.sfrei.tracksearch_web.exceptions.RequestException;
import io.sfrei.tracksearch_web.pojos.TrackPOJO;
import io.sfrei.tracksearch_web.services.TrackSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/ts")
@RequiredArgsConstructor
public class TrackSearchController {

    private final TrackSearchService trackSearchService;

    @GetMapping("/search")
    public List<TrackPOJO> searchTracks(@RequestParam(name = "query") String search,
                                        @RequestParam(name = "source", required = false) Set<String> sources,
                                        HttpServletResponse response)
            throws TrackSearchException, RequestException {

        TrackList<Track> tracksForSearch;
        if (sources != null) {
            tracksForSearch = trackSearchService.getTracksForSearch(search, sources);
        } else {
            tracksForSearch = trackSearchService.getTracksForSearch(search);
        }
        TrackSearchService.TrackSearchCookie.set(response, tracksForSearch);
        return toPOJOs(tracksForSearch.getTracks());
    }

    @GetMapping("/next")
    public List<TrackPOJO> nextTracks(HttpServletRequest request, HttpServletResponse response)
            throws TrackSearchException {

        TrackList<Track> lastQuery = TrackSearchService.TrackSearchCookie.get(request);
        TrackList<Track> nextTracks = trackSearchService.getNext(lastQuery);
        TrackSearchService.TrackSearchCookie.set(response, nextTracks);
        return toPOJOs(nextTracks.getTracks());
    }

    @PostMapping("/stream")
    public String getStreamUrl(@Valid @RequestBody TrackPOJO pojo)
            throws TrackSearchException, RequestException {

        Track track = fromPOJO(pojo);
        return trackSearchService.getStreamUrl(track);
    }

    private List<TrackPOJO> toPOJOs(List<Track> tracks) {
        return tracks.stream().map(track ->
                new TrackPOJO(track.getTitle(), track.getCleanTitle(), track.getSource(), track.getLength(), track.getUrl()))
                .collect(Collectors.toList());
    }

    private Track fromPOJO(TrackPOJO track) throws RequestException {
        switch (track.getTrackSource()) {
            case Youtube:
                return new YouTubeTrack(track.getTitle(), track.getLength(), track.getUrl());
            case Soundcloud:
                return new SoundCloudTrack(track.getTitle(), track.getLength(), track.getUrl());
        }
        throw new RequestException("Track source is required");
    }
}
