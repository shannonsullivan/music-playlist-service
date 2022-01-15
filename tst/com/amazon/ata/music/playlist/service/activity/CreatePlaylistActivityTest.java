package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreatePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;

    private CreatePlaylistActivity createPlaylistActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        createPlaylistActivity = new CreatePlaylistActivity(playlistDao);
    }

    @Test
    public void handleRequest_validCustomerIdAndName_createsPlaylist() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        List<String> expectedTags = Lists.newArrayList("tag");

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .withTags(expectedTags)
                .build();

        // WHEN
        CreatePlaylistResult actual = createPlaylistActivity.handleRequest(request, null);

        // THEN
        assertEquals(expectedName, actual.getPlaylist().getName(), "Name provided is invalid");
        assertEquals(expectedCustomerId, actual.getPlaylist().getCustomerId(), "CustomerId provided is invalid");
        assertEquals(expectedTags, actual.getPlaylist().getTags(), "List of tags is invalid");
    }

    @Test
    public void handleRequest_inValidCustomerId_throwInvalidAttributeValueException() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedCustomerId = "customerId'sInvalid";
        List<String> expectedTags = Lists.newArrayList("tag");

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .withTags(expectedTags)
                .build();

        // WHEN & THEN
        assertThrows(InvalidAttributeValueException.class, () -> {
            createPlaylistActivity.handleRequest(request, null);
        }, "When customer Id is invalid, throw PlaylistNotFoundException");
    }

    @Test
    public void handleRequest_inValidName_throwInvalidAttributeValueException() {
        // GIVEN
        String expectedName = "name'sInvalid";
        String expectedCustomerId = "expectedCustomerId";
        List<String> expectedTags = Lists.newArrayList("tag");

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .withTags(expectedTags)
                .build();

        // WHEN & THEN
        assertThrows(InvalidAttributeValueException.class, () -> {
            createPlaylistActivity.handleRequest(request, null);
        }, "When playlist name is invalid, throw PlaylistNotFoundException");
    }

    @Test
    public void handleRequest_nullTCustomerIdAndName_throwInvalidAttributeValueException() {
        // GIVEN
        String expectedName = null;
        String expectedCustomerId = null;
        List<String> expectedTags = Lists.newArrayList("tag");

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .withTags(expectedTags)
                .build();

        // WHEN & THEN
        assertThrows(InvalidAttributeValueException.class, () -> {
            createPlaylistActivity.handleRequest(request, null);
        }, "When playlist name or customer Id is null, throw PlaylistNotFoundException");
    }
}
