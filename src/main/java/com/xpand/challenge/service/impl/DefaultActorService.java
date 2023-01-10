package com.xpand.challenge.service.impl;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.dto.ActorDTOMapper;
import com.xpand.challenge.dto.IdentifiableActorDTO;
import com.xpand.challenge.dto.MovieDTOMapper;
import com.xpand.challenge.model.Actor;
import com.xpand.challenge.repository.ActorRepository;
import com.xpand.challenge.service.ActorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultActorService implements ActorService {
    private final ActorRepository actorRepository;

    public DefaultActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public IdentifiableActorDTO createActor(ActorDTO actorDTO) {
        Actor actor = ActorDTOMapper.fromActorDTO(actorDTO);
        return ActorDTOMapper.toActorDTO(actorRepository.save(actor));
    }

    @Override
    public IdentifiableActorDTO getActor(Long id) {
        return actorRepository.findById(id)
                .map(ActorDTOMapper::toActorDTO)
                .orElseThrow();
    }

    @Override
    public List<IdentifiableActorDTO> getActors() {
        return actorRepository.findAll().stream()
                .map(ActorDTOMapper::toActorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateActor(Long id, ActorDTO actorDTO) {
        actorRepository.findById(id).orElseThrow();
        Actor actor = ActorDTOMapper.fromActorDTO(actorDTO);
        actor.setId(id);
        actorRepository.save(actor);
    }

    @Override
    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}