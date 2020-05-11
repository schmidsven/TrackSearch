package io.sfrei.tracksearch_web.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sfrei.tracksearch.clients.setup.TrackSource;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class PersistentTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TrackSource source;

    private String title;

    private String cleanTitle;

    private Long length;
    
    private String url;

    @JsonIgnore
    @ManyToMany(mappedBy = "tracks", fetch = FetchType.LAZY)
    private Set<TrackContainer> trackContainers;

    public PersistentTrack(TrackSource source, String title, String cleanTitle, Long length, String url) {
        this.source = source;
        this.title = title;
        this.cleanTitle = cleanTitle;
        this.length = length;
        this.url = url;
    }

}
