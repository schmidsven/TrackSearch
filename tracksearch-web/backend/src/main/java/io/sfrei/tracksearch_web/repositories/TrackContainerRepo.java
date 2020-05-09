package io.sfrei.tracksearch_web.repositories;

import io.sfrei.tracksearch_web.entities.TracksContainer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrackContainerRepo extends CrudRepository<TracksContainer, Long> {

    Optional<TracksContainer> findById(Long id);

}
