package io.sfrei.tracksearch_web.presistence;

import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch_web.entities.PersistentTrack;
import io.sfrei.tracksearch_web.repositories.TrackRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepo trackRepo;

    public PersistentTrack getOrCreate(Track track) {
        return trackRepo.findByUrl(track.getUrl())
                .orElseGet(() -> trackRepo.save(new PersistentTrack(
                        track.getSource(), track.getTitle(), track.getLength(), track.getUrl())
                ));
    }

}
