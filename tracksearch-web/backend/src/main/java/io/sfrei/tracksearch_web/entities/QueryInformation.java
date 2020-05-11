package io.sfrei.tracksearch_web.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(length = 1024)
    private String value;

    @JsonIgnore
    @ManyToMany(mappedBy = "queryInformation", fetch = FetchType.LAZY)
    private Set<TrackContainer> trackContainers;

    public QueryInformation(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
