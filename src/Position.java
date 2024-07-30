class Position {
    private char disk;

    public Position() {
        disk = '*';  // Mark unplayable positions with '*'
    }

    public char getDisk() {
        return disk;
    }

    public void setDisk(char disk) {
        this.disk = disk;
    }

    public boolean canPlay() {
        return disk == '_';  // Only playable if the disk is a space
    }
}


