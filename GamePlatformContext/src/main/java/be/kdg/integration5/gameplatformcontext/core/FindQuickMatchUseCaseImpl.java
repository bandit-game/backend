package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.port.in.FindQuickMatchCommand;
import be.kdg.integration5.gameplatformcontext.port.in.FindQuickMatchUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindQuickMatchUseCaseImpl implements FindQuickMatchUseCase {
    @Override
    @Transactional(readOnly = true)
    public Lobby findQuickMatch(FindQuickMatchCommand findQuickMatchCommand) {
        //TODO Implement
        return null;
    }
}
