package io.sfrei.tracksearch_web.pojos;

import io.sfrei.tracksearch.clients.setup.TrackSource;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TrackPOJO {

    private final String title;

    private final String cleanTitle;

    @NotNull(
            message = "TrackSource is required"
    )
    private final TrackSource trackSource;

    private final Long length;

    @NotEmpty(
            message = "URL is required"
    )
    private final String url;

}
