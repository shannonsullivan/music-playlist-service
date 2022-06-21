package com.amazon.ata.music.playlist.service.dynamodb;

import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaylistDaoTest {
    private PlaylistDao playlistDao;

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    public void setUp() {
        initMocks(this);
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

        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setId(id);
        existingPlaylist.setName(name);
        existingPlaylist.setCustomerId(customerId);
        existingPlaylist.setSongCount(songCount);
        existingPlaylist.setTags(tags);

        when(dynamoDBMapper.load(Playlist.class, id)).thenReturn(existingPlaylist);

        // WHEN
        Playlist actual = playlistDao.getPlaylist(id);

        // THEN
        assertEquals(name, actual.getName(), "Expected playlist name is not correct");
        assertEquals(customerId, actual.getCustomerId(), "Expected customer Id is not correct");
        assertEquals(songCount, actual.getSongCount(), "Expected song count is not correct");
        assertEquals(tags, actual.getTags(), "Expected tags for playlist are not correct");
    }

    @Test
    public void getPlaylist_nullPlaylist_throwsPlaylistNotFoundException() {
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
        String id = "PPT04";
        String name = "PPT04 playlist";
        String customerId = "4";
        Integer songCount = 4;
        Set<String> tags = new HashSet<>(Arrays.asList("PPT04 tags"));

        Playlist newPlaylist = new Playlist();
        newPlaylist.setId(id);
        newPlaylist.setName(name);
        newPlaylist.setCustomerId(customerId);
        newPlaylist.setSongCount(songCount);
        newPlaylist.setTags(tags);

        when(dynamoDBMapper.load(Playlist.class, id)).thenReturn(newPlaylist);

        // WHEN
        playlistDao.savePlaylist(newPlaylist);
        Playlist actual = playlistDao.getPlaylist(id);

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
        String id = "PPT04";
        String name = "PPT04 playlist";
        String customerId = "4";
        Integer songCount = 4;
        Set<String> tags = new HashSet<>(Arrays.asList("PPT04 tags"));

        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setId(id);
        existingPlaylist.setName(name);
        existingPlaylist.setCustomerId(customerId);
        existingPlaylist.setSongCount(songCount);
        existingPlaylist.setTags(tags);

//        Playlist update = existingPlaylist.setTags(new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag", "additionalTag")));
//        Playlist update = existingPlaylist.dynamoDBMapper.load(Playlist.class, playlistId);
//        when(dynamoDBMapper.load(Playlist.class, id)).thenReturn(existingPlaylist);
//        update.setTags(new HashSet<>(Arrays.asList("1st tag", "2nd tag", "3rd tag", "additionalTag")));
//

        // WHEN
//        playlistDao.savePlaylist(update);

        // THEN
//        assertEquals(id, update.getId(), "Expected playlist Id is not correct after saving a new playlist");
//        assertEquals(name, update.getName(), "Expected playlist name is not correct after saving a new playlist");
//        assertEquals(customerId, update.getCustomerId(), "Expected playlist customerId is not correct after saving a new playlist");
//        assertEquals(songCount,update.getSongCount(), "Expected playlist song count is not correct after saving a new playlist");
//        assertEquals(tags, update.getTags(), "Expected playlist song count is not correct after saving a new playlist");
    }
}
