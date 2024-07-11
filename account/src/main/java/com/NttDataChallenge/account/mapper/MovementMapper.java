package com.NttDataChallenge.account.mapper;

import com.NttDataChallenge.account.dto.MovementDTO;
import com.NttDataChallenge.account.dto.MovementRequestDTO;
import com.NttDataChallenge.account.dto.MovementResponseDTO;
import com.NttDataChallenge.account.models.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface MovementMapper {
    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);
    MovementRequestDTO movementDTOToMovementRequest(MovementDTO movementDTO);
    MovementEntity movementRequestToMovementCreate(MovementRequestDTO movementRequestDTO);
    MovementResponseDTO movementToMovementResponse(MovementEntity movement);
    List<MovementResponseDTO> listMovementToListMovementResponse(List<MovementEntity> movement);
}
