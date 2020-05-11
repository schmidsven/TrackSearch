package io.sfrei.tracksearch_web.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sfrei.tracksearch.clients.setup.QueryType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class TrackContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private QueryType queryType;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "tracklist_queryinformation",
            joinColumns = {
                    @JoinColumn(name = "tracklist_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "queryinformation_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<QueryInformation> queryInformation;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "tracklist_track",
            joinColumns = {
                    @JoinColumn(name = "tracklist_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "track_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<PersistentTrack> tracks;

    public TrackContainer(Set<PersistentTrack> tracks, QueryType queryType, Set<QueryInformation> queryInformation) {
        this.tracks = tracks;
        this.queryType = queryType;
        this.queryInformation = queryInformation;
    }

}
