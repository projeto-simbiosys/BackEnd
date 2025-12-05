package school.sptech.Simbiosys.infrastructure.gateway;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository repository;

    public UsuarioGatewayImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @CachePut(cacheNames = "usuarioPorId", key = "#produto.id")
    @Override
    public UsuarioEntity save(UsuarioEntity usuario) {
        return repository.save(usuario);
    }

    @Override
    public Optional<UsuarioEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Cacheable(cacheNames = "usuarioId", key = "#id")
    @Override
    public Optional<UsuarioEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @CacheEvict(cacheNames = "usuarioId", key = "#id")
    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Page<UsuarioEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public boolean existsByEmailIgnoreCaseContaining(String email) {
        return repository.existsByEmailIgnoreCaseContaining(email);
    }

    @Override
    public List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }
}
