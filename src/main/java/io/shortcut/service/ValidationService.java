package io.shortcut.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;

import java.util.regex.Pattern;

@Service
public class ValidationService {

    public static final Pattern URL_MATCHER = Pattern.compile("^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$");

    public void validateUrl(String url) {
        if (!URL_MATCHER.matcher(url).matches()) {
           throw new InvalidUrlException("url is malformed " + url);
        }
    }
    //todo no need to sanitize input because it's done out of the box by Spring

    //todo another type of validation could try to hit the URL and see if it gets OK 200 and some content - to prevent adding dead links
    //todo above could be also enforced by a hard per-account limit, transferring responsibility to user
}
