package io.sfrei.tracksearch_web.services;

import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import io.sfrei.tracksearch_web.entities.PersistentTrack;
import io.sfrei.tracksearch_web.entities.QueryInformation;
import io.sfrei.tracksearch_web.entities.TrackContainer;
import io.sfrei.tracksearch_web.exceptions.EntityNotFoundException;
import io.sfrei.tracksearch_web.repositories.QueryInformationRepo;
import io.sfrei.tracksearch_web.repositories.TrackContainerRepo;
import io.sfrei.tracksearch_web.repositories.TrackRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersistenceService {

    private final TrackContainerRepo trackContainerRepo;
    private final QueryInformationRepo queryInformationRepo;
    private final TrackRepo trackRepo;

    public TrackContainer saveTrackContainer(TrackList<? extends Track> trackList) {
        Set<QueryInformation> queryInformation = serializeQueryInformation(trackList.getQueryInformation());
        Set<PersistentTrack> tracks = serializeTracks(trackList.getTracks());
        return trackContainerRepo.save(new TrackContainer(queryInformation, tracks));
    }

    public TrackContainer getTrackContainerById(long id) throws EntityNotFoundException {
        return trackContainerRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("TrackContainer with id: " + id + " does not exist"));
    }

    private Set<QueryInformation> serializeQueryInformation(Map<String, String> queryInformation) {
        return queryInformation.entrySet().stream()
                .map(entry -> getOrCreateQueryInformation(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    private Set<PersistentTrack> serializeTracks(List<? extends Track> tracks) {
        return tracks.stream()
                .map(this::getOrCreatePersistentTrack)
                .collect(Collectors.toSet());
    }

    private QueryInformation getOrCreateQueryInformation(String key, String value) {
        return queryInformationRepo.findByKeyAndValue(key,value)
                .orElseGet(() -> queryInformationRepo.save(
                        new QueryInformation(key, value)
                ));
    }

    private PersistentTrack getOrCreatePersistentTrack(Track track) {
        return trackRepo.findByUrl(track.getUrl())
                .orElseGet(() -> trackRepo.save(new PersistentTrack(
                        track.getSource(), track.getTitle(), track.getCleanTitle(), track.getLength(), track.getUrl())
                ));
    }

}
