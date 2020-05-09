package io.sfrei.tracksearch_web.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class TracksContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(
            mappedBy = "trackContainers",
            cascade = CascadeType.ALL)
    private Set<QueryInformation> queryInformation;

    @ManyToMany(
            mappedBy = "trackContainers",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<PersistentTrack> tracks;

    public TracksContainer(Set<QueryInformation> queryInformation, Set<PersistentTrack> tracks) {
        this.queryInformation = queryInformation;
        this.tracks = tracks;
    }

}
