package com.fiap.blackoutservice.service;

import java.util.List;

import com.fiap.blackoutservice.dto.BlackoutDTO;

public interface BlackoutService {
    List<BlackoutDTO> getAllBlackouts();
    BlackoutDTO getBlackoutById(Long id);
    BlackoutDTO createBlackout(BlackoutDTO blackoutDTO);
    BlackoutDTO updateBlackout(Long id, BlackoutDTO blackoutDTO);
    void deleteBlackout(Long id);
}
