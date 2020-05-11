package io.sfrei.tracksearch_web.services;

import io.sfrei.tracksearch.clients.setup.TrackSource;
import io.sfrei.tracksearch.tracks.*;
import io.sfrei.tracksearch_web.entities.PersistentTrack;
import io.sfrei.tracksearch_web.entities.QueryInformation;
import io.sfrei.tracksearch_web.entities.TrackContainer;
import io.sfrei.tracksearch_web.exceptions.EntityNotFoundException;
import io.sfrei.tracksearch_web.repositories.QueryInformationRepo;
import io.sfrei.tracksearch_web.repositories.TrackContainerRepo;
import io.sfrei.tracksearch_web.repositories.TrackRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        return trackContainerRepo.save(serializeTrackList(trackList));
    }

    public TrackContainer getTrackContainerById(long id) throws EntityNotFoundException {
        return trackContainerRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("TrackContainer with id: " + id + " does not exist"));
    }

    public PersistentTrack getPersistentTrackById(long id) throws EntityNotFoundException {
        return trackRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Track with id: " + id + " does not exist"));
    }

    private TrackContainer serializeTrackList(TrackList<? extends Track> trackList) {
        Set<PersistentTrack> tracks = serializeTracks(trackList.getTracks());
        Set<QueryInformation> queryInformation = serializeQueryInformation(trackList.getQueryInformation());
        return new TrackContainer(tracks, trackList.getQueryType(), queryInformation);
    }

    public TrackList<? extends Track> deserializeTrackListInformation(TrackContainer trackContainer) {
        Map<String, String> queryInformation = deserializeQueryInformation(trackContainer.getQueryInformation());
        return new BaseTrackList<>(new ArrayList<>(), trackContainer.getQueryType(), queryInformation);
    }

    @SuppressWarnings("unused")
    private TrackList<? extends Track> deserializeTrackList(TrackContainer trackContainer) {
        List<Track> tracks =deserializeTracks(trackContainer.getTracks());
        Map<String, String> queryInformation = deserializeQueryInformation(trackContainer.getQueryInformation());

        return new BaseTrackList<>(tracks, trackContainer.getQueryType(), queryInformation);
    }

    private Set<QueryInformation> serializeQueryInformation(Map<String, String> queryInformation) {
        return queryInformation.entrySet().stream()
                .map(entry -> getOrCreateQueryInformation(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    private Map<String, String> deserializeQueryInformation(Set<QueryInformation> queryInformation) {
        return queryInformation.stream().collect(Collectors.toMap(QueryInformation::getKey, QueryInformation::getValue));
    }

    private Set<PersistentTrack> serializeTracks(List<? extends Track> tracks) {
        return tracks.stream()
                .map(this::getOrCreatePersistentTrack)
                .collect(Collectors.toSet());
    }

    private List<Track> deserializeTracks(Set<PersistentTrack> tracks) {
        return tracks.stream()
                .map(this::deserializeTrack)
                .collect(Collectors.toList());
    }

    public Track deserializeTrack(PersistentTrack persistentTrack) {
        TrackSource source = persistentTrack.getSource();

        Track track;
        if (source.equals(TrackSource.Youtube)) {
            track = new YouTubeTrack(persistentTrack.getTitle(), persistentTrack.getLength(), persistentTrack.getUrl());
        } else  {
            track = new SoundCloudTrack(persistentTrack.getTitle(), persistentTrack.getLength(), persistentTrack.getUrl());
        }

        return track;
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
