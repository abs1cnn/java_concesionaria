package ar.edu.utnfrc.backend.spring_service_web.services;

import ar.edu.utnfrc.backend.spring_service_web.entities.Client;
import ar.edu.utnfrc.backend.spring_service_web.repositories.ClientRepository;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends ServiceImpl<Client, Integer> implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client create(Client entity) {
        clientRepository.save(entity);
        return entity;
    }

    @Override
    public Client update(Integer id, Client entity) {
        clientRepository.save(entity);
        return entity;
    }

    @Override
    public void delete(Integer id) {
        Client entity = findById(id);
        clientRepository.delete(entity);
    }

    @Override
    public Client findById(Integer id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}
