package io.sfrei.tracksearch_web.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sfrei.tracksearch.clients.MultiTrackSearchClient;
import io.sfrei.tracksearch.clients.setup.QueryType;
import io.sfrei.tracksearch.clients.setup.TrackSource;
import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch.tracks.BaseTrackList;
import io.sfrei.tracksearch.tracks.Track;
import io.sfrei.tracksearch.tracks.TrackList;
import io.sfrei.tracksearch_web.exceptions.RequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackSearchService {

    private final MultiTrackSearchClient trackSearchClient;

    public TrackList<Track> getTracksForSearch(String search) throws TrackSearchException {
        return trackSearchClient.getTracksForSearch(search);
    }

    public TrackList<Track> getTracksForSearch(String search, Set<String> sources) throws TrackSearchException, RequestException {
        Set<TrackSource> trackSources = Arrays.stream(TrackSource.values())
                .filter(trackSource -> {
                    for (String source : sources) {
                        if (source.equalsIgnoreCase(trackSource.name()))
                            return true;
                    }
                    return false;
                })
                .collect(Collectors.toSet());

        if (trackSources.isEmpty())
            throw new RequestException("Track sources are unknown");

        return trackSearchClient.getTracksForSearch(search, trackSources);
    }

    public TrackList<Track> getNext(TrackList<Track> trackList) throws TrackSearchException {
        return trackSearchClient.getNext(trackList);
    }

    public String getStreamUrl(Track track) throws TrackSearchException {
        return trackSearchClient.getStreamUrl(track);
    }

    public static class TrackSearchCookie {
        public static final String COOKIE_PREFIX = "tracksearch-cookie-";
        public static final String QUERY_TYPE = COOKIE_PREFIX + "qt";
        public static final String QUERY_INFORMATION = COOKIE_PREFIX + "qi";
        private static final ObjectMapper OBJECT_MAPPER;

        static {
            OBJECT_MAPPER = new ObjectMapper();
            OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        public static void set(HttpServletResponse response, TrackList<Track> trackList) {
            getCookies(trackList).forEach(response::addCookie);
        }

        @SneakyThrows
        public static Collection<Cookie> getCookies(TrackList<Track> trackList) {
            QueryType queryType = trackList.getQueryType();
            Map<String, String> queryInformation = trackList.getQueryInformation();

            Set<Cookie> tsCookies = new HashSet<>();
            tsCookies.add(new Cookie(QUERY_TYPE, queryType.name()));
            tsCookies.add(new Cookie(QUERY_INFORMATION, OBJECT_MAPPER.writeValueAsString(queryInformation)));
            return tsCookies;
        }

        public static TrackList<Track> get(HttpServletRequest request) {
            return getTrackList(request.getCookies());
        }

        @SneakyThrows
        public static TrackList<Track> getTrackList(Cookie... cookies) {
            QueryType queryType = null;
            Map<String, String> queryInformation = null;
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case QUERY_TYPE: {
                        queryType = QueryType.valueOf(cookie.getValue());
                        break;
                    }
                    case QUERY_INFORMATION: {
                        queryInformation = OBJECT_MAPPER.readValue(cookie.getValue(), Map.class);
                        break;
                    }
                }
            }
            return new BaseTrackList<>(null, queryType, queryInformation);
        }


    }

}
