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
        Set<String> tags = new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag", "additionalTag"));
        //List<String> songList = new ArrayList<>(Arrays.asList("whereImFrom", "wahoo", "All Mirrors"));

        // WHEN
        Playlist actual = playlistDao.getPlaylist(id);

        // THEN
        assertEquals(name, actual.getName(), "Correct name of playlist was not returned");
        assertEquals(customerId, actual.getCustomerId(), "Correct customer id for playlist was not returned");
        assertEquals(songCount, actual.getSongCount(), "Correct song count for playlist was not returned");
        assertEquals(tags, actual.getTags(), "Correct tags for playlist were not returned");
        //assertEquals(songList, actual.getSongList(), "Correct song list for playlist was not returned");
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
    public void savePlaylist_newPlaylist_returnsPlaylistWithCorrectAttributes() {
        // GIVEN
        String playlistId = "PPT04";
        String name = "PPT04 playlist";
        String customerId = "4";
        Integer songCount = 4;
        Set<String> tags = new HashSet<>(Arrays.asList("PPT04 tags"));
        //List<String> songList = new ArrayList<>(Arrays.asList("whereImFrom", "wahoo", "All Mirrors"));

        Playlist newPlaylist = new Playlist();
        newPlaylist.setId(playlistId);
        newPlaylist.setName(name);
        newPlaylist.setCustomerId(customerId);
        newPlaylist.setSongCount(songCount);
        newPlaylist.setTags(tags);
        //expected.setSongList(songList);

        // WHEN
        playlistDao.savePlaylist(newPlaylist);
        Playlist actual = playlistDao.getPlaylist(playlistId);

        // THEN
        assertEquals(newPlaylist.getId(), actual.getId(), "Expected playlist Id is not correct after saving a new playlist");
        assertEquals(newPlaylist.getName(), actual.getName(), "Expected playlist name is not correct after saving a new playlist");
        assertEquals(newPlaylist.getCustomerId(), actual.getCustomerId(), "Expected playlist customerId is not correct after saving a new playlist");
        assertEquals(newPlaylist.getSongCount(), actual.getSongCount(), "Expected playlist song count is not correct after saving a new playlist");
        assertEquals(newPlaylist.getTags(), actual.getTags(), "Expected playlist song count is not correct after saving a new playlist");
    }

    @Test
    public void savePlaylist_additionalTagForExistingPlaylist_returnUpdatedPlaylist() {
        // GIVEN
        String playlistId = "PPT03";
        String name = "PPT03 playlist";
        String customerId = "3";
        Integer songCount = 3;
        Set<String> tags = new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag", "additionalTag"));
        //List<String> songList = new ArrayList<>(Arrays.asList("whereImFrom", "wahoo", "All Mirrors"));

        Playlist update = dynamoDBMapper.load(Playlist.class, playlistId);
        //Playlist update = playlistDao.getPlaylist(playlistId);
        //update.setId(playlistId);
        //update.setName(name);
        //update.setCustomerId(customerId);
        //update.setSongCount(songCount);
        update.setTags(new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag", "additionalTag")));
        //update.setSongList(songList);




        // WHEN
        playlistDao.savePlaylist(update);

        // THEN
        assertEquals(playlistId, update.getId(), "Expected playlist Id is not correct after saving a new playlist");
        assertEquals(name, update.getName(), "Expected playlist name is not correct after saving a new playlist");
        assertEquals(customerId, update.getCustomerId(), "Expected playlist customerId is not correct after saving a new playlist");
        assertEquals(songCount,update.getSongCount(), "Expected playlist song count is not correct after saving a new playlist");
        assertEquals(tags, update.getTags(), "Expected playlist song count is not correct after saving a new playlist");
    }
}
