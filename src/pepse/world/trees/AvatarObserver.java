package pepse.world.trees;
/**
 * The AvatarObserver interface defines the contract for objects that observe the avatar's actions.
 * Classes implementing this interface can react to specific actions performed by the avatar.
 */
public interface AvatarObserver {
    /**
     * Reacts to the avatar's jump action.
     * This method is called when the avatar performs a jump action.
     */
    public void jumpReaction();
}
