package io.sfrei.tracksearch_web.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class QueryInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    private String value;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "tracklist_infos",
            joinColumns = @JoinColumn(name = "tracklist_id"),
            inverseJoinColumns = @JoinColumn(name = "info_id"))
    private Set<TracksContainer> trackContainers;

    public QueryInformation(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
