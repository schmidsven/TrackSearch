package io.sfrei.tracksearch_web.repositories;

import io.sfrei.tracksearch_web.entities.QueryInformation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QueryInformationRepo extends CrudRepository<QueryInformation, Long> {

    Optional<QueryInformation> findByKeyAndValue(String key, String value);

}
