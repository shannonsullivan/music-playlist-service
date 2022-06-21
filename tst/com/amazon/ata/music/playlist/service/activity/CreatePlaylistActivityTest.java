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
        assertEquals(expectedName, actual.getPlaylist().getName(), "Expected playlist name is not correct");
        assertEquals(expectedCustomerId, actual.getPlaylist().getCustomerId(), "Expected customerId is not correct");
        assertEquals(expectedTags, actual.getPlaylist().getTags(), "Expected list of tags is not correct");
    }

    @Test
    public void handleRequest_invalidCustomerId_throwInvalidAttributeValueException() {
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
        }, "When customer Id is invalid, throw InvalidAttributeValueException");
    }

    @Test
    public void handleRequest_invalidName_throwInvalidAttributeValueException() {
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
        }, "When playlist name is invalid, throw InvalidAttributeValueException");
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
        }, "When playlist name or customer Id is null, throw InvalidAttributeValueException");
    }
}
