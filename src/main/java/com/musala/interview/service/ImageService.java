package com.musala.interview.service;

import com.musala.interview.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final PhotoRepository photoRepository;
    public void addImage(String medicationId, byte[] image) {
    }
    public byte[] getImage(String medicationId) {
        return new byte[0];
    }
}
