package com.amazon.ata.music.playlist.service.dependency;


import com.amazon.ata.music.playlist.service.activity.*;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Dagger Component interface providing root objects for Activity classes.
 */
@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {

    /**
     * Provides a new CreatePlaylistActivity with injected dependencies from module.
     *
     * @return createPlaylistActivity a new CreatePlaylistActivity with injected dependencies.
     */
    CreatePlaylistActivity provideCreatePlaylistActivity();

    /**
     * Provides a new GetPlaylistActivity with injected dependencies from module.
     *
     * @return getPlaylistActivity a new GetPlaylistActivity with injected dependencies.
     */
    GetPlaylistActivity provideGetPlaylistActivity();

    /**
     * Provides a new UpdatePlaylistActivity with injected dependencies from module.
     *
     * @return updatePlaylistActivity a new UpdatePlaylistActivity with injected dependencies.
     */
    UpdatePlaylistActivity provideUpdatePlaylistActivity();

    /**
     * Provides a new AddSongToPlaylistActivity with injected dependencies from module.
     *
     * @return addSongToPlaylistActivity a new AddSongToPlaylistActivity with injected dependencies.
     */
    AddSongToPlaylistActivity provideAddSongToPlaylistActivity();

    /**
     * Provides a new GetPlaylistSongsActivity with injected dependencies from module.
     *
     * @return getPlaylistSongsActivity a new GetPlaylistSongsActivity with injected dependencies.
     */
    GetPlaylistSongsActivity provideGetPlaylistSongsActivity();
}
