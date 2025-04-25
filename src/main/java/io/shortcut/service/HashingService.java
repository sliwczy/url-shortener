package io.shortcut.service;

import org.hashids.Hashids;
import org.springframework.stereotype.Service;

// todo: currently reusing hashids library with nano time seed; could be tweaked for a shorter yet unique hash or can be a custom made
// todo: current setup is flexible for changes in implementation and allows easy unit testing (in the UrlService)
@Service
public class HashingService {

    private static final String SALT = "someSaltValue";
    private final Hashids hashids;

    HashingService() {
        this.hashids = new Hashids(SALT, 7);
    }

    public String getUuid() {
        return hashids.encode(System.nanoTime());
    }
}
