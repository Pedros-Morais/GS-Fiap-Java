package com.fiap.blackoutservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.fiap.blackoutservice.dto.BlackoutDTO;
import com.fiap.blackoutservice.exception.ResourceNotFoundException;
import com.fiap.blackoutservice.model.Blackout;
import com.fiap.blackoutservice.model.User;
import com.fiap.blackoutservice.repository.BlackoutRepository;
import com.fiap.blackoutservice.repository.UserRepository;
import com.fiap.blackoutservice.service.BlackoutService;

@Service
public class BlackoutServiceImpl implements BlackoutService {

    @Autowired
    private BlackoutRepository blackoutRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Cacheable(value = "blackoutsCache", key = "'all'")
    public List<BlackoutDTO> getAllBlackouts() {
        return blackoutRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "blackoutsCache", key = "#id")
    public BlackoutDTO getBlackoutById(Long id) {
        Blackout blackout = blackoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blackout not found with id: " + id));
        return convertToDTO(blackout);
    }

    @Override
    @CacheEvict(value = "blackoutsCache", key = "'all'")
    public BlackoutDTO createBlackout(BlackoutDTO blackoutDTO) {
        Blackout blackout = convertToEntity(blackoutDTO);
        Blackout savedBlackout = blackoutRepository.save(blackout);
        
        // If the blackout was reported by a user, award them some points
        if (blackout.getReportedBy() != null) {
            User user = blackout.getReportedBy();
            user.setRewardPoints(user.getRewardPoints() + 10); // Award 10 points for reporting
            userRepository.save(user);
        }
        
        return convertToDTO(savedBlackout);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "blackoutsCache", key = "#id"),
        @CacheEvict(value = "blackoutsCache", key = "'all'")
    })
    public BlackoutDTO updateBlackout(Long id, BlackoutDTO blackoutDTO) {
        Blackout existingBlackout = blackoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blackout not found with id: " + id));
        
        // Update fields
        existingBlackout.setLocation(blackoutDTO.getLocation());
        existingBlackout.setStartTime(blackoutDTO.getStartTime());
        existingBlackout.setEndTime(blackoutDTO.getEndTime());
        existingBlackout.setStatus(blackoutDTO.getStatus());
        existingBlackout.setDescription(blackoutDTO.getDescription());
        existingBlackout.setLatitude(blackoutDTO.getLatitude());
        existingBlackout.setLongitude(blackoutDTO.getLongitude());
        existingBlackout.setVerified(blackoutDTO.getVerified());
        
        // If reported by user is changed, update it
        if (blackoutDTO.getReportedById() != null) {
            User user = userRepository.findById(blackoutDTO.getReportedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + blackoutDTO.getReportedById()));
            existingBlackout.setReportedBy(user);
        }
        
        Blackout updatedBlackout = blackoutRepository.save(existingBlackout);
        return convertToDTO(updatedBlackout);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "blackoutsCache", key = "#id"),
        @CacheEvict(value = "blackoutsCache", key = "'all'")
    })
    public void deleteBlackout(Long id) {
        if (!blackoutRepository.existsById(id)) {
            throw new ResourceNotFoundException("Blackout not found with id: " + id);
        }
        blackoutRepository.deleteById(id);
    }
    
    // Helper methods for conversion between DTO and Entity
    private BlackoutDTO convertToDTO(Blackout blackout) {
        BlackoutDTO dto = new BlackoutDTO();
        dto.setId(blackout.getId());
        dto.setLocation(blackout.getLocation());
        dto.setStartTime(blackout.getStartTime());
        dto.setEndTime(blackout.getEndTime());
        dto.setStatus(blackout.getStatus());
        dto.setDescription(blackout.getDescription());
        dto.setLatitude(blackout.getLatitude());
        dto.setLongitude(blackout.getLongitude());
        dto.setVerified(blackout.getVerified());
        
        if (blackout.getReportedBy() != null) {
            dto.setReportedById(blackout.getReportedBy().getId());
        }
        
        return dto;
    }
    
    private Blackout convertToEntity(BlackoutDTO dto) {
        Blackout blackout = new Blackout();
        blackout.setId(dto.getId());
        blackout.setLocation(dto.getLocation());
        blackout.setStartTime(dto.getStartTime());
        blackout.setEndTime(dto.getEndTime());
        blackout.setStatus(dto.getStatus());
        blackout.setDescription(dto.getDescription());
        blackout.setLatitude(dto.getLatitude());
        blackout.setLongitude(dto.getLongitude());
        blackout.setVerified(dto.getVerified());
        
        if (dto.getReportedById() != null) {
            User user = userRepository.findById(dto.getReportedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getReportedById()));
            blackout.setReportedBy(user);
        }
        
        return blackout;
    }
}
