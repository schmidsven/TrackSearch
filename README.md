# TrackSearch

## What is it ?

TrackSearch is to search for track metadata on different sources, like Youtube and SoundCloud for now and to expose the
URL of the underlying audio stream in the highest resolution. That offers the possibility to hand them over to other
programs which are able to process them, like [VLC](https://www.videolan.org/vlc/), or Firefox which can display the 
audio directly for example.

**Note:** TrackSearch isn't using any API-Key, it uses the public Rest-API.

## Supported sources

Since TrackSearch focuses on just exposing the audio streams and to search for music (I know YouTube offers more than 
music) I decided to add following providers first for now:

- YouTube
- SoundCloud

There could be more added if there is interesting content offered to go for.

#### Current features:

- Search for keywords
- Paging of results
- Expose audio stream url
- Interact with multiple clients asynchronous

## How to use it

```java
//Client to search on all available sources asynchronous
MultiTrackSearchClient searchClient = new MultiSearchClient();

//Client for explicit source
TrackSearchClient<SoundCloudTrack> explicitClient = new SoundCloudClient();

//Do the searching
TrackList<Track> tracksForSearch = searchClient.getTracksForSearch("your keywords")

//Get the audio stream
List<Track> tracks = tracksForSearch.getTracks();
String streamUrl = searchClient.getStreamUrl(tracks.get(any))
```


## Why is this done ?

I haven't found anything which is capable of doing this kind of stuff, except it offered something similar and could
be abused for this, or it wasn't written in Java.


## Develop

Run following commands in the root directory.

#### Build

```shell script
mvnw clean package
```