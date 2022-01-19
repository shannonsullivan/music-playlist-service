package com.amazon.ata.music.playlist.service.dynamodb;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.exceptions.AlbumTrackNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AlbumTrackDaoTest {
    private AlbumTrackDao albumTrackDao;

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        albumTrackDao = new AlbumTrackDao(dynamoDBMapper);
    }

    @Test
    public void getAlbumTrack_existingAlbumTrack_returnsExpectedAlbumTrack() {
        // GIVEN
        String asin = "B07NJ3H27X";
        int trackNumber = 7;
        String albumName = "Cuz I Love You";
        String songTitle = "Tempo";

        AlbumTrack existingAlbumTrack = new AlbumTrack();
        existingAlbumTrack.setAsin(asin);
        existingAlbumTrack.setTrackNumber(trackNumber);
        existingAlbumTrack.setAlbumName(albumName);
        existingAlbumTrack.setSongTitle(songTitle);

        when(dynamoDBMapper.load(AlbumTrack.class, asin)).thenReturn(existingAlbumTrack);

        // WHEN
        AlbumTrack actual = albumTrackDao.getAlbumTrack(asin);

        // THEN
        assertEquals(asin, actual.getAsin(), "Expected asin for the album track is not correct");
        assertEquals(trackNumber, actual.getTrackNumber(), "Expected track number for the album track is not correct");
        assertEquals(albumName, actual.getAlbumName(), "Expected album name for the album track is not correct");
        assertEquals(songTitle, actual.getSongTitle(), "Expected song title for the album track is not correct");
    }

    @Test
    public void getAlbumTrack_nullAlbumTrack_throwsAlbumTrackNotFoundException() {
        // GIVEN
        String asin = "00000";

        // WHEN & THEN
        assertThrows(AlbumTrackNotFoundException.class, () -> {
            albumTrackDao.getAlbumTrack(asin);
        }, "When album track is null, throw AlbumTrackNotFoundException");
    }
}
