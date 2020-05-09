package io.sfrei.tracksearch_web.entities;

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

    private Long length;
    
    private String url;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "tracklist_tracks",
            joinColumns = @JoinColumn(name = "tracklist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    private Set<TracksContainer> trackContainers;

    public PersistentTrack(TrackSource source, String title, Long length, String url) {
        this.source = source;
        this.title = title;
        this.length = length;
        this.url = url;
    }

}
