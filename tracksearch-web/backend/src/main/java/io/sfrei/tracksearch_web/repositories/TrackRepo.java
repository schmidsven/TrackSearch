package io.sfrei.tracksearch_web.repositories;

import io.sfrei.tracksearch_web.entities.PersistentTrack;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrackRepo extends CrudRepository<PersistentTrack, Long> {

    Optional<PersistentTrack> findById(Long id);

    Optional<PersistentTrack> findByUrl(String url);

}
