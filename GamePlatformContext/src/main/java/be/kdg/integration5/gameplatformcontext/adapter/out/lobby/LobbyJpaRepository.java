package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import org.apache.qpid.proton.codec.UUIDType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {

}
