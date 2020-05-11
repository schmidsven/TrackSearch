package io.sfrei.tracksearch_web.repositories;

import io.sfrei.tracksearch_web.entities.TrackContainer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrackContainerRepo extends CrudRepository<TrackContainer, Long> {

    Optional<TrackContainer> findById(Long id);

}
