package io.sfrei.tracksearch_web.presistence;

import io.sfrei.tracksearch_web.entities.QueryInformation;
import io.sfrei.tracksearch_web.repositories.QueryInformationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryInformationService {

    private final QueryInformationRepo queryInformationRepo;

    public QueryInformation getOrCreate(String key, String value) {
        return queryInformationRepo.findByKeyAndValue(key,value)
                .orElseGet(() -> queryInformationRepo.save(
                        new QueryInformation(key, value)
                ));
    }

}
