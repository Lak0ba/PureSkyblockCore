package me.lakoba.pureSkyblockCore.managers;

import java.util.HashMap;
import java.util.UUID;

public class InvitationManager {
    private final HashMap<UUID, UUID> invitations = new HashMap<>();

    public void sendInvite(UUID sender, UUID receiver) {
        invitations.put(receiver, sender);
    }

    public boolean hasInvite(UUID receiver) {
        return invitations.containsKey(receiver);
    }

    public UUID getInviter(UUID receiver) {
        return invitations.get(receiver);
    }

    public void acceptInvite(UUID receiver) {
        invitations.remove(receiver);
    }
}
