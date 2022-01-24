package com.amazon.ata.music.playlist.service.converters;

import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelConverterTest {

    private PlaylistModel expected;
    private List<String> tags = new ArrayList<>(Arrays.asList("1 tag", "2 tag ", "3 tag"));

    @BeforeEach
    void setUp() {
        expected = PlaylistModel.builder()
                .withId("expectedId")
                .withName("expectedName")
                .withCustomerId("customerId")
                .withSongCount(1)
                .withTags(tags)
                .build();
    }

    @Test
    public void toPlaylistModel_PlaylistToPlaylistModel_returnsConvertedPlaylistModel() {

    }
}
