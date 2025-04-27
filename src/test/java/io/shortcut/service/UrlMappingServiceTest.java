package io.shortcut.service;

import io.shortcut.domain.UrlMapping;
import io.shortcut.repository.UrlMappingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlMappingServiceTest {

    @Mock
    private UrlMappingRepository urlMappingRepository;
    @Mock
    private HashingService hashingService;
    @InjectMocks
    private UrlMappingService mappingService;

    @Test
    public void test_whenCallCreate_NotExists_ThenShouldCreateNew() {
        //given
        String hashOfNewlyCreatedEntity = "createdNewEntityMockHash";
        var urlMappingMock = Mockito.mock(UrlMapping.class);

        when(urlMappingMock.getShortenedUrl()).thenReturn(hashOfNewlyCreatedEntity);
        when(urlMappingRepository.getUrlMappingByUserEmailAndUrl(anyString(), anyString()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(urlMappingMock)); //second time simulate created

        when(hashingService.getShortenedUrlHash()).thenReturn(hashOfNewlyCreatedEntity);

        //when
        String result = mappingService.createIfNotExists("someEmail", "someUrl");

        //then
        verify(hashingService, times(1)).getShortenedUrlHash();
        verify(urlMappingRepository, times(1)).save(any(UrlMapping.class));
        assertEquals(hashOfNewlyCreatedEntity, result);
    }

    @Test
    public void test_whenCallCreate_LinkAlreadyExists_ShouldReturnExistingHash() {
        //given
        var urlMappingMock = Mockito.mock(UrlMapping.class);
        String existingHash = "existingShortenedUrlHash";
        when(urlMappingMock.getShortenedUrl()).thenReturn(existingHash);
        when(urlMappingRepository.getUrlMappingByUserEmailAndUrl(anyString(), anyString())).thenReturn(Optional.of(urlMappingMock));

        //when
        String result = mappingService.createIfNotExists("someEmail", "someUrl");

        //then
        assertEquals(existingHash, result);
        verify(hashingService, times(0)).getShortenedUrlHash();
    }
}