class PlayablePosition extends Position {
    public PlayablePosition() {
        super();
        setDisk('_');// playable positions are "_" and unplayable are "*"
    }

    @Override
    public boolean canPlay() {
        return getDisk() == '_';
    }
}