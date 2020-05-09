package io.sfrei.tracksearch_web.presistence;

import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import io.sfrei.tracksearch_web.entities.PersistentTrack;
import io.sfrei.tracksearch_web.entities.QueryInformation;
import io.sfrei.tracksearch_web.entities.TracksContainer;
import io.sfrei.tracksearch_web.exceptions.EntityNotFoundException;
import io.sfrei.tracksearch_web.repositories.TrackContainerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TracksContainerService {

    private final TrackContainerRepo trackContainerRepo;
    private final QueryInformationService queryInformationService;
    private final TrackService trackService;

    public TracksContainer save(TrackList<? extends Track> trackList) {
        Set<QueryInformation> queryInformation = Collections.emptySet();
//        serializeQueryInformation(trackList.getQueryInformation());
        Set<PersistentTrack> tracks = serializeTracks(trackList.getTracks());
        return trackContainerRepo.save(new TracksContainer(queryInformation, tracks));
    }

    public TracksContainer get(Long id) throws EntityNotFoundException {
        return trackContainerRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("TrackContainer with id: " + id + " does not exist"));
    }

    private Set<QueryInformation> serializeQueryInformation(Map<String, String> queryInformation) {
        return queryInformation.entrySet().stream()
                .map(entry -> queryInformationService.getOrCreate(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    private Set<PersistentTrack> serializeTracks(List<? extends Track> tracks) {
        return tracks.stream()
                .map(trackService::getOrCreate)
                .collect(Collectors.toSet());
    }
}
