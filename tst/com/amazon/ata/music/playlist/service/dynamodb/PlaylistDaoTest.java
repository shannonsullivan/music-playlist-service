package com.amazon.ata.music.playlist.service.dynamodb;

import com.amazon.ata.aws.dynamodb.DynamoDbClientProvider;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlaylistDaoTest {

    private PlaylistDao playlistDao;
    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    public void setUp() {
        AmazonDynamoDB dynamoDBClient = DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_2);
        dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
        playlistDao = new PlaylistDao(dynamoDBMapper);
    }

    @Test
    public void getPlaylist_existingPlaylist_returnsExpectedPlaylist() {
        // GIVEN
        String id = "PPT03";
        String name = "PPT03 playlist";
        String customerId = "3";
        Integer songCount = 3;
        Set<String> tags = new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag"));
        List<String> songList = new ArrayList<>(Arrays.asList("whereImFrom", "wahoo", "All Mirrors"));

        // WHEN
        Playlist actual = playlistDao.getPlaylist(id);

        // THEN
        assertEquals(name, actual.getName(), "Correct name of playlist was not returned");
        assertEquals(customerId, actual.getCustomerId(), "Correct customer id for playlist was not returned");
        assertEquals(songCount, actual.getSongCount(), "Correct song count for playlist was not returned");
        assertEquals(tags, actual.getTags(), "Correct tags for playlist were not returned");
        assertEquals(songList, actual.getSongList(), "Correct song list for playlist was not returned");
    }

    @Test
    public void getPlaylist_nullPlaylist_returnsPlaylistNotFoundException() {
        // GIVEN
        String id = "PPT00";

        // WHEN & THEN
        assertThrows(PlaylistNotFoundException.class, () -> {
            playlistDao.getPlaylist(id);
        }, "When playlist is null, throw PlaylistNotFoundException");
    }

    @Test
    public void savePlaylist_additionalTagForExistingPlaylist_returnUpdatedPlaylist() {
        // GIVEN
        Playlist expected = new Playlist();
        String playlistId = "PPT03";
        expected.setId(playlistId);
        expected.setName("PPT03 playlist");
        expected.setCustomerId("3");
        expected.setSongCount(3);
        Set<String> tags = new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag"));
        expected.setTags(tags);

        String additionalTag = "additionalTag";
        //List<String> songList = new ArrayList<>(Arrays.asList("whereImFrom", "wahoo", "All Mirrors"));
        //expected.setSongList(songList);

        // WHEN
        Playlist update = playlistDao.getPlaylist(playlistId);
        update.setTags(new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag", additionalTag)));
        playlistDao.savePlaylist(update);

        // THEN
        Playlist playlist = playlistDao.getPlaylist(playlistId);
        assertEquals(
                update,
                playlist.getId(),
                String.format("Expected updating playlist, '%s', with additional tag, '%', " +
                        "but playlist was: %s",
                        playlistId,
                        update,
                        playlist.toString())
        );
    }

    @Test
    public void savePlaylist_newPlaylist_returnsPlaylistWithCorrectAttributes() {
        // GIVEN
        Playlist newPlaylist = new Playlist();
        String playlistId = "PPT04";
        newPlaylist.setId(playlistId);
        newPlaylist.setName("PPT04 playlist");
        newPlaylist.setCustomerId("4");
        newPlaylist.setSongCount(4);
        Set<String> tags = new HashSet<>(Arrays.asList("PPT04 tags"));
        newPlaylist.setTags(tags);
        //List<String> songList = new ArrayList<>(Arrays.asList("whereImFrom", "wahoo", "All Mirrors"));
        //expected.setSongList(songList);

        // WHEN
        playlistDao.savePlaylist(newPlaylist);

        // THEN
        Playlist actual = playlistDao.getPlaylist(playlistId);
        assertEquals(newPlaylist, actual, "Expected to return a new playlist after saving to playlist");
    }
}
