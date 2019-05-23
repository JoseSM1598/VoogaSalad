package combatSystem.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import combatSystem.internal.PlayerManager;

import messenger.external.CombatActionEvent;
import messenger.external.EventBusFactory;

public class CombatSystem {

    EventBus eventBus;
    PlayerManager playerManager;

    public CombatSystem(int numOfPlayers){
        eventBus = EventBusFactory.getEventBus();
        playerManager = new PlayerManager(numOfPlayers);
    }

    @Subscribe
    public void onCombatEvent(CombatActionEvent event){
        int id = event.getInitiatorID();
        playerManager.changePlayerStateByIDOnEvent(id, event);
    }

}
